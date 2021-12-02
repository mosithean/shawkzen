package ru.geekbrains.chat.server;

import ru.geekbrains.chat.network.TcpConnection;
import ru.geekbrains.chat.network.TcpConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements TcpConnectionListener {

    private final List<TcpConnection> connections = new ArrayList<>();

    private ChatServer() {
        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            while (true) {
                try {
                    new TcpConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    System.out.println("TCP Connection exception: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new ChatServer();
    }

    @Override
    public void onConnectionReady(TcpConnection tcpConnection) {
    }

    @Override
    public void onReceiveString(TcpConnection tcpConnection, String value) {
    }

    @Override
    public void onDisconnect(TcpConnection tcpConnection) {
    }

    @Override
    public void onException(TcpConnection tcpConnection, Exception e) {
    }
}
