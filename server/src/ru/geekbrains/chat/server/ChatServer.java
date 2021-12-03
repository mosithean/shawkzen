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
                    System.out.println("TCP Connection exception: " + e);
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
    public synchronized void onConnectionReady(TcpConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnections("Client connected: " + tcpConnection);
    }

    @Override
    public synchronized void onReceiveString(TcpConnection tcpConnection, String value) {
        sendToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TcpConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections("Client disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onException(TcpConnection tcpConnection, Exception e) {
        System.out.println("TCP Connection exception: " + e);
    }

    private void sendToAllConnections(String value) {
        System.out.println(value);
        connections.forEach(c -> c.sendString(value));
    }
}
