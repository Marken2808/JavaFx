package com.school.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class NetworkConnection {

    private ConnectionThread connThread = new ConnectionThread();
    private Consumer<Serializable> onReceiveCallBack;
    protected abstract boolean isServer();
    protected abstract String getIP();
    protected abstract int getPort();

    public NetworkConnection( Consumer<Serializable> onReceiveCallBack){
        this.onReceiveCallBack = onReceiveCallBack;
        connThread.setDaemon(true);
    }

    public void startConnection() {
        connThread.start();
    }

    public void send(Serializable data) throws IOException {
        connThread.out.writeObject(data);
    }

    public void closeConnection() throws IOException {
        connThread.socket.close();
    }

    private class ConnectionThread extends Thread{

        private Socket socket;
        private ObjectOutputStream out;

        @Override
        public void run() {
            try (ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                 Socket socket = isServer() ? server.accept() : new Socket(getIP(), getPort());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream()))
            {

                this.socket = socket;
                this.out = out;
                socket.setTcpNoDelay(true);

                while (true){
                    Serializable data = (Serializable) in.readObject();
                    onReceiveCallBack.accept(data);
                }

            } catch (Exception e) {
                onReceiveCallBack.accept("Connection closed");
            }
        }
    }
}
