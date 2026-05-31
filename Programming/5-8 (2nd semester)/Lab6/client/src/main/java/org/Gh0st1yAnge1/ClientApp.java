package org.Gh0st1yAnge1;

import org.Gh0st1yAnge1.client_commands.Insert;
import org.Gh0st1yAnge1.exceptions.InputCancelledException;
import org.Gh0st1yAnge1.manager.*;
import org.Gh0st1yAnge1.request_and_response.CommandType;
import org.Gh0st1yAnge1.request_and_response.Request;
import org.Gh0st1yAnge1.request_and_response.Response;
import org.Gh0st1yAnge1.utils.RouteBuilder;
import org.Gh0st1yAnge1.utils.TcpUtil;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class ClientApp {

    private static final String HOST = "localhost";
    private static final int PORT = 12345;
    private static final long RECONNECT_TIMEOUT_MS = 3000;

    private enum State {
        DISCONNECTED,
        CONNECTING,
        CONNECTED
    }

    private static State state = State.DISCONNECTED;
    private static Selector selector;
    private static SocketChannel socketChannel;

    private static InputManager inputManager;
    private static RouteBuilder routeBuilder;
    private static ClientCommandManager commandManager;

    private static Request pendingRequest = null;

    private static boolean awaitingInsertConfirm = false;
    private static String  pendingInsertArg      = null;
    private static Response lastResponse = null;

    private static ByteBuffer chunkLengthBuffer = ByteBuffer.allocate(4);
    private static Integer    remainingChunkBytes = null;
    private static Path       responseFile        = null;
    private static OutputStream responseOut       = null;
    private static final ByteBuffer readBuffer    = ByteBuffer.allocate(TcpUtil.CHUNK_SIZE);

    private static Path         requestFile    = null;
    private static InputStream  requestStream  = null;
    private static final byte[] writeChunk     = new byte[TcpUtil.CHUNK_SIZE];
    private static ByteBuffer   writeHeaderBuf = ByteBuffer.allocate(4);
    private static ByteBuffer   writePayloadBuf = null;
    private static boolean      sendingEof      = false;

    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            cleanup();
        }));

        selector = Selector.open();
        inputManager = new InputManager();
        routeBuilder = new RouteBuilder(inputManager);
        commandManager = new ClientCommandManager(inputManager, routeBuilder);

        initiateConnect();
        System.out.print("> ");

        while (true) {
            selector.select(RECONNECT_TIMEOUT_MS);

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (!key.isValid()) continue;

                try {
                    if (key.isConnectable()) handleConnect(key);
                    if (key.isValid() && key.isWritable()) handleWrite(key);
                    if (key.isValid() && key.isReadable()) handleRead(key);
                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    System.out.println("\nConnection error: " + e.getMessage());
                    handleDisconnect();
                }
            }

            if (state == State.DISCONNECTED) {
                System.out.println("Reconnecting to server...");
                initiateConnect();
                continue;
            }

            if (state == State.CONNECTED && pendingRequest == null) {
                if (System.in.available() > 0) {
                    String input = inputManager.readline();
                    if (input == null || input.trim().isEmpty()) {
                        System.out.print("> ");
                        continue;
                    }

                    String commandName = input.trim().split("\\s+", 2)[0];
                    if (commandName.equals("execute_script")) {
                        String arg = input.trim().contains(" ")
                                ? input.trim().substring(input.trim().indexOf(' ') + 1)
                                : null;
                        commandManager.executeScript(arg, ClientApp::sendAndWait);
                        System.out.print("> ");
                        continue;
                    }

                    Request request = commandManager.execute(input);
                    if (request == null) {
                        System.out.print("> ");
                        continue;
                    }
                    if (request.commandType() == CommandType.EXIT) break;

                    if (request.commandType() == CommandType.CHECK_KEY
                            && commandName.equals("insert")) {
                        awaitingInsertConfirm = true;
                        pendingInsertArg = request.argument();
                    }

                    pendingRequest = request;
                    socketChannel.keyFor(selector).interestOps(SelectionKey.OP_WRITE);
                }
            }
        }
        cleanup();
    }

    private static void initiateConnect() throws IOException {
        if (socketChannel != null) {
            try { socketChannel.close(); } catch (IOException ignored) {}
        }
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress(HOST, PORT));
        state = State.CONNECTING;
    }

    private static void handleConnect(SelectionKey key) throws IOException, InterruptedException {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            if (channel.finishConnect()) {
                key.interestOps(SelectionKey.OP_READ);
                state = State.CONNECTED;
                pendingRequest = null;
                resetReadState();
                System.out.println("Connected to server.");
                System.out.print("> ");
            }
        } catch (IOException e) {
            System.out.println("Server unavailable: " + e.getMessage()
                    + ". Retrying in " + RECONNECT_TIMEOUT_MS + "ms...");
            Thread.sleep(3000);
            state = State.DISCONNECTED;
            key.cancel();
            channel.close();
        }
    }

    private static void handleWrite(SelectionKey key) throws IOException {
        if (requestFile == null) {
            requestFile = TcpUtil.serializeToTempFile(pendingRequest);
            requestStream = Files.newInputStream(requestFile);
            prepareNextWriteChunk();
        }

        SocketChannel channel = (SocketChannel) key.channel();

        if (writeHeaderBuf.hasRemaining()) {
            channel.write(writeHeaderBuf);
            return;
        }

        if (sendingEof) {
            finishWrite(key);
            return;
        }

        if (writePayloadBuf != null && writePayloadBuf.hasRemaining()) {
            channel.write(writePayloadBuf);
            if (!writePayloadBuf.hasRemaining()) {
                prepareNextWriteChunk();
            }
        }
    }

    private static void prepareNextWriteChunk() throws IOException {
        int read = requestStream.read(writeChunk);
        writeHeaderBuf.clear();
        if (read <= 0) {
            writeHeaderBuf.putInt(0);
            sendingEof = true;
        } else {
            writeHeaderBuf.putInt(read);
            writePayloadBuf = ByteBuffer.wrap(writeChunk, 0, read);
        }
        writeHeaderBuf.flip();
    }

    private static void finishWrite(SelectionKey key) throws IOException {
        if (requestStream != null) { requestStream.close(); requestStream = null; }
        if (requestFile != null) { Files.deleteIfExists(requestFile); requestFile = null; }
        writePayloadBuf = null;
        sendingEof = false;

        resetReadState();
        responseFile = Files.createTempFile("tcp-response", ".bin");
        responseOut  = Files.newOutputStream(responseFile);

        key.interestOps(SelectionKey.OP_READ);
    }

    private static void handleRead(SelectionKey key) throws IOException, ClassNotFoundException {
        if (pendingRequest == null || responseFile == null) return;

        SocketChannel channel = (SocketChannel) key.channel();

        while (true) {
            if (remainingChunkBytes == null) {
                int read = channel.read(chunkLengthBuffer);
                if (read == -1) throw new EOFException("Server closed connection");
                if (chunkLengthBuffer.hasRemaining()) return;

                chunkLengthBuffer.flip();
                int chunkLen = chunkLengthBuffer.getInt();
                chunkLengthBuffer.clear();

                if (chunkLen < 0) throw new IOException("Invalid chunk size: " + chunkLen);

                if (chunkLen == 0) {
                    responseOut.close();
                    Response response = (Response) TcpUtil.desirealizeFromFile(responseFile);
                    Files.deleteIfExists(responseFile);
                    responseFile = null;
                    responseOut  = null;
                    pendingRequest = null;
                    lastResponse = response;

                    handleResponse(key, response);
                    return;
                }
                remainingChunkBytes = chunkLen;
            }

            readBuffer.clear();
            readBuffer.limit(Math.min(remainingChunkBytes, readBuffer.capacity()));
            int read = channel.read(readBuffer);
            if (read == -1) throw new EOFException("Server closed connection");
            if (read == 0) return;

            responseOut.write(readBuffer.array(), 0, read);
            remainingChunkBytes -= read;
            if (remainingChunkBytes == 0) remainingChunkBytes = null;
        }
    }

    private static void handleResponse(SelectionKey key, Response response)
            throws IOException {

        if (awaitingInsertConfirm) {
            awaitingInsertConfirm = false;
            if (response.success()) {
                System.out.println(response.message());
                try {
                    Request insertRequest = new Insert(routeBuilder).execute(pendingInsertArg);
                    pendingInsertArg = null;
                    pendingRequest = insertRequest;
                    key.interestOps(SelectionKey.OP_WRITE);
                } catch (InputCancelledException e) {
                    System.out.println("Route building cancelled.");
                    pendingInsertArg = null;
                    System.out.print("> ");
                }
            } else {
                System.out.println(response.message());
                pendingInsertArg = null;
                System.out.print("> ");
            }
            return;
        }

        if (response.message() != null) System.out.println(response.message());
        if (response.collection() != null) System.out.println(response.collection());
        System.out.print("> ");

        key.interestOps(SelectionKey.OP_READ);
    }

    private static Response sendAndWait(Request request) throws IOException, ClassNotFoundException {
        if (state != State.CONNECTED) {
            throw new IOException("Not connected to server.");
        }

        lastResponse = null;
        pendingRequest = request;
        socketChannel.keyFor(selector).interestOps(SelectionKey.OP_WRITE);

        while (pendingRequest != null && state == State.CONNECTED) {
            selector.select(RECONNECT_TIMEOUT_MS);
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                if (!key.isValid()) continue;
                if (key.isWritable()) handleWrite(key);
                if (key.isValid() && key.isReadable()) handleRead(key);
            }
        }

        if (state != State.CONNECTED) {
            throw new IOException("Lost connection during script execution.");
        }

        return lastResponse;
    }

    private static void handleDisconnect() {
        System.out.println("Disconnected. Will reconnect...");
        if (socketChannel != null) {
            SelectionKey key = socketChannel.keyFor(selector);
            if (key != null) key.cancel();
            try { socketChannel.close(); } catch (IOException ignored) {}
        }
        try {
            if (requestStream != null) { requestStream.close(); requestStream = null; }
            if (requestFile != null) { Files.deleteIfExists(requestFile); requestFile = null; }
            if (responseOut != null) { responseOut.close(); responseOut = null; }
            if (responseFile != null) { Files.deleteIfExists(responseFile); responseFile = null; }
        } catch (IOException ignored) {}
        writePayloadBuf = null;
        sendingEof = false;
        pendingRequest = null;
        awaitingInsertConfirm = false;
        pendingInsertArg = null;
        resetReadState();
        state = State.DISCONNECTED;
    }

    private static void resetReadState() {
        chunkLengthBuffer.clear();
        remainingChunkBytes = null;
    }

    private static void cleanup() {
        try {
            if (socketChannel != null) socketChannel.close();
            if (selector != null) selector.close();
        } catch (IOException ignored) {}
    }
}