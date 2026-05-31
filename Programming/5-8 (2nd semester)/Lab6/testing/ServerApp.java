package org.Gh0st1yAnge1;

import org.Gh0st1yAnge1.manager.CollectionManager;
import org.Gh0st1yAnge1.manager.FileManager;
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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerApp
{
    private static final int PORT = 12345;
    private static final Logger logger = LoggerUtil.getLogger();

    private static CollectionManager collectionManager;
    private static FileManager fileManager;
    private static ServerCommandExecutor serverCommandExecutor;

    public static void main(String[] args ){
        logger.setLevel(Level.INFO);
        logger.info("=== LOADING SERVER ===");

        String filePath = System.getenv("FILE_PATH");
        if (filePath == null || filePath.trim().isEmpty()){
            logger.severe("ERROR: FILE_PATH environmental variable is not set.");
            return;
        }
        logger.info("File path: " + filePath);

        fileManager = new FileManager(filePath);
        collectionManager = new CollectionManager();
        serverCommandExecutor = new ServerCommandExecutor(collectionManager, fileManager);


        logger.info("Try to load collection file.");
        LinkedHashMap<Integer, Route> loadedData = fileManager.loadCollection();
        collectionManager.load(loadedData);
        logger.info("Collection loaded. Elements count: " + loadedData.size());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Response saveResult = serverCommandExecutor.execute(
                    new Request(CommandType.SAVE_SERVER, null,null)
            );
            logger.info("Shutdown save status: " + saveResult.message());
        }));

        logger.info("Starting server loop...");
        runServer();
    }

    private static void runServer(){
        try (Selector selector = Selector.open();
             ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()){

            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(PORT));

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            logger.info("Server started on port: " + PORT);

            while (true){
                int readyCount = selector.select();
                if (readyCount == 0) continue;


                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (!key.isValid()) continue;

                    try {
                        if (key.isAcceptable()) handleAccept(key, selector);
                        if (key.isValid() && key.isReadable()) handleRead(key);
                    } catch (IOException | ClassNotFoundException e){
                        logger.severe("Error  handling client: " + e.getMessage());
                        closeClient(key);
                    }
                }
            }

        } catch (IOException e) {
            logger.severe("Server startup error: " + e.getMessage());
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

        while (true){
            if (session.remainingChunkBytes == null){

                int bytesRead = clientChannel.read(session.chunkLengthBuffer);
                if (bytesRead == -1){ closeClient(key); return;}
                if (session.chunkLengthBuffer.hasRemaining()) return;

                session.chunkLengthBuffer.flip();
                int chunkLength = session.chunkLengthBuffer.getInt();
                session.chunkLengthBuffer.clear();

                if (chunkLength < 0){
                    logger.warning("Invalid chunk size from " + clientChannel.getRemoteAddress() + ": " + chunkLength);
                    closeClient(key);
                    return;
                }

                if (chunkLength == 0){
                    session.payloadOutputStream.flush();
                    Request request = (Request) TcpUtil.desirealizeFromFile(session.payloadFile);
                    Response response = serverCommandExecutor.execute(request);
                    logger.info("Got request: " + request.commandType() + " from " + clientChannel.getRemoteAddress());
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
            if (bytesRead == 0) return; //no available data right now

            session.payloadOutputStream.write(session.payloadBuffer.array(), 0, bytesRead);
            session.remainingChunkBytes -= bytesRead;

            if (session.remainingChunkBytes == 0) {
                session.remainingChunkBytes = null; // ready to read next chunk header
            }
        }
    }

    private static void sendResponse(SocketChannel channel, Response response) throws IOException {
        Path payloadFile = TcpUtil.serializeToTempFile(response);
        try {
            TcpUtil.writeChunckedFromFile(channel, payloadFile);
            logger.fine("Response is sent to client.");
        } finally {
            Files.deleteIfExists(payloadFile);
        }

    }

    private static void closeClient(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            logger.info("Client disconnected: " + channel.getRemoteAddress());
        } catch (IOException ignored){}

        ClientSession session = (ClientSession) key.attachment();
        if (session != null) try {session.cleanUp();} catch (IOException ignored) {}
        key.cancel();
        try {channel.close();} catch (IOException ignored){}
    }

    private static class ClientSession{
        private ByteBuffer chunkLengthBuffer = ByteBuffer.allocate(4);
        private ByteBuffer payloadBuffer = ByteBuffer.allocate(TcpUtil.CHUNK_SIZE);
        private Integer remainingChunkBytes = null;
        private Path payloadFile;
        private OutputStream payloadOutputStream;

        private ClientSession() throws IOException{
            init();
        }

        private void init() throws IOException {
            this.chunkLengthBuffer.clear();
            this.remainingChunkBytes = null;
            this.payloadBuffer.clear();
            this.payloadFile = Files.createTempFile("tcp-request", ".bin");
            this.payloadOutputStream = Files.newOutputStream(payloadFile);
        }

        private void cleanUp() throws IOException {
            if (payloadOutputStream != null) payloadOutputStream.close();
            if (this.payloadFile != null) Files.deleteIfExists(this.payloadFile);
        }

        private void reset() throws IOException{
            cleanUp();
            init();
        }
    }
}
