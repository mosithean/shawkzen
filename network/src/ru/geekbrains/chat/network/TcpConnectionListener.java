package ru.geekbrains.chat.network;

public interface TcpConnectionListener {

    void onConnectionReady(TcpConnection tcpConnection);

    void onReceiveString(TcpConnection tcpConnection, String value);

    void onDisconnect(TcpConnection tcpConnection);

    void onException(TcpConnection tcpConnection, Exception e);
}
