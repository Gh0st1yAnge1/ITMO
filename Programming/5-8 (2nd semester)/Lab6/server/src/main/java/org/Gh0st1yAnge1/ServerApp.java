package org.Gh0st1yAnge1;

import org.Gh0st1yAnge1.audit.AuditProducer;
import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.manager.FileManager;
import org.Gh0st1yAnge1.manager.InputManager;
import org.Gh0st1yAnge1.manager.ServerCommandExecutor;
import org.Gh0st1yAnge1.model.Route;
import org.Gh0st1yAnge1.request_and_response.CommandType;
import org.Gh0st1yAnge1.request_and_response.Request;
import org.Gh0st1yAnge1.request_and_response.Response;
import org.Gh0st1yAnge1.utils.LoggerUtil;
import org.Gh0st1yAnge1.utils.TcpUtil;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerApp {

    private static final int PORT = 12345;
    private static final Logger logger = LoggerUtil.getLogger();

    private static CollectionManager collectionManager;
    private static FileManager fileManager;
    private static ServerCommandExecutor serverCommandExecutor;
    private static InputManager inputManager;
    private static AuditProducer auditProducer;
    private static volatile boolean running = true;

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");
        logger.setLevel(Level.INFO);
        logger.info("=== LOADING SERVER ===");

        String filePath = System.getenv("FILE_PATH");
        if (filePath == null || filePath.trim().isEmpty()) {
            logger.severe("ERROR: FILE_PATH environmental variable is not set.");
            return;
        }
        logger.info("File path: " + filePath);

        fileManager = new FileManager(filePath);
        collectionManager = new CollectionManager();

        String kafkaBootstrap = System.getenv("KAFKA_BOOTSTRAP_SERVERS");
        if (kafkaBootstrap != null && !kafkaBootstrap.trim().isEmpty()) {
            try {
                auditProducer = new AuditProducer(kafkaBootstrap);
                logger.info("Kafka audit enabled. Bootstrap: " + kafkaBootstrap);
            } catch (Exception e) {
                logger.warning("Failed to init Kafka: " + e.getMessage()
                        + ". Running without audit.");
                auditProducer = null;
            }
        } else {
            logger.info("KAFKA_BOOTSTRAP_SERVERS not set. Running without audit.");
        }

        serverCommandExecutor = new ServerCommandExecutor(collectionManager, fileManager, auditProducer);

        logger.info("Try to load collection file.");
        LinkedHashMap<Integer, Route> loadedData = fileManager.loadCollection();
        collectionManager.load(loadedData);
        logger.info("Collection loaded. Elements count: " + loadedData.size());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Response saveResult = serverCommandExecutor.execute(
                    new Request(CommandType.SAVE_SERVER, null, null)
            );
            logger.info("Shutdown save status: " + saveResult.message());

            if (auditProducer != null) {
                auditProducer.close();
                logger.info("AuditProducer closed.");
            }
        }));

        logger.info("Starting server loop...");
        runServer();
    }

    private static void runServer() {
        Thread networkThread = new Thread(() -> {
            try (Selector selector = Selector.open();
                 ServerSocketChannel serverChannel = ServerSocketChannel.open()) {

                serverChannel.configureBlocking(false);
                serverChannel.bind(new InetSocketAddress(PORT));
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);
                logger.info("Server started on port: " + PORT);


                while (running) {
                    int readyCount = selector.select(200);
                    if (readyCount == 0) continue;

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        if (!key.isValid()) continue;

                        try {
                            if (key.isAcceptable()) handleAccept(key, selector);
                            if (key.isValid() && key.isReadable()) handleRead(key);
                        } catch (IOException | ClassNotFoundException e) {
                            logger.severe("Error handling client: " + e.getMessage());
                            closeClient(key);
                        }
                    }
                }

            } catch (IOException e) {
                logger.severe("Server startup error: " + e.getMessage());
            }
        });

        networkThread.setDaemon(true);
        networkThread.start();

        inputManager = new InputManager();

        while (running) {
            System.out.print("> ");
            String input = inputManager.readline();
            if (input == null || input.trim().isEmpty()) continue;
            String answer = serverCommandExecutor.execute(input);
            if (answer == null) continue;
            if (answer.equals("Session terminated.")) {
                Response saveResult = serverCommandExecutor.execute(
                        new Request(CommandType.SAVE_SERVER, null, null)
                );
                if (auditProducer != null) {
                    auditProducer.close();
                    logger.info("AuditProducer closed.");
                }
                logger.info("Save on exit: " + saveResult.message());
                System.out.println(answer);
                running = false;
                break;
            }
            System.out.println(answer);
        }
    }

    private static void handleAccept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        if (clientChannel == null) return;
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ, new ClientSession());
        logger.info("New connection from: " + clientChannel.getRemoteAddress());
    }

    private static void handleRead(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ClientSession session = (ClientSession) key.attachment();

        while (true) {
            if (session.remainingChunkBytes == null) {
                int bytesRead = clientChannel.read(session.chunkLengthBuffer);
                if (bytesRead == -1) { closeClient(key); return; }
                if (session.chunkLengthBuffer.hasRemaining()) return;

                session.chunkLengthBuffer.flip();
                int chunkLength = session.chunkLengthBuffer.getInt();
                session.chunkLengthBuffer.clear();

                if (chunkLength < 0) { closeClient(key); return; }

                if (chunkLength == 0) {
                    session.payloadOutputStream.flush();
                    Request request = (Request) TcpUtil.desirealizeFromFile(session.payloadFile);
                    Response response = serverCommandExecutor.execute(request);
                    logger.info("Request: " + request.commandType()
                            + " from " + clientChannel.getRemoteAddress());
                    sendResponse(clientChannel, response);
                    session.reset();
                    return;
                }

                session.remainingChunkBytes = chunkLength;
            }

            session.payloadBuffer.clear();
            int portion = Math.min(session.remainingChunkBytes, session.payloadBuffer.capacity());
            session.payloadBuffer.limit(portion);
            int bytesRead = clientChannel.read(session.payloadBuffer);
            if (bytesRead == -1) { closeClient(key); return; }
            if (bytesRead == 0) return;
            session.payloadOutputStream.write(session.payloadBuffer.array(), 0, bytesRead);
            session.remainingChunkBytes -= bytesRead;
            if (session.remainingChunkBytes == 0) session.remainingChunkBytes = null;
        }
    }

    private static void sendResponse(SocketChannel channel, Response response) throws IOException {
        Path payloadFile = TcpUtil.serializeToTempFile(response);
        try {
            TcpUtil.writeChunkedFromFile(channel, payloadFile);
        } finally {
            Files.deleteIfExists(payloadFile);
        }
    }

    private static void closeClient(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try { logger.info("Client disconnected: " + channel.getRemoteAddress()); }
        catch (IOException ignored) {}
        ClientSession session = (ClientSession) key.attachment();
        if (session != null) { try { session.cleanUp(); } catch (IOException ignored) {} }
        key.cancel();
        try { channel.close(); } catch (IOException ignored) {}
    }

    private static class ClientSession {
        ByteBuffer chunkLengthBuffer = ByteBuffer.allocate(4);
        ByteBuffer payloadBuffer     = ByteBuffer.allocate(TcpUtil.CHUNK_SIZE);
        Integer remainingChunkBytes  = null;
        Path payloadFile;
        OutputStream payloadOutputStream;

        ClientSession() throws IOException { init(); }

        void init() throws IOException {
            chunkLengthBuffer.clear();
            remainingChunkBytes = null;
            payloadBuffer.clear();
            payloadFile = Files.createTempFile("tcp-request", ".bin");
            payloadOutputStream = Files.newOutputStream(payloadFile);
        }

        void cleanUp() throws IOException {
            if (payloadOutputStream != null) payloadOutputStream.close();
            if (payloadFile != null) Files.deleteIfExists(payloadFile);
        }

        void reset() throws IOException { cleanUp(); init(); }
    }
}
