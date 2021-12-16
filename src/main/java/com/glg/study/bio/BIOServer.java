package com.glg.study.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author glg
 * @version 1.0
 * @date 2021/9/29 14:47
 */
public class BIOServer {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(8088));
            // 主线程死循环等待新连接到来
            while (!Thread.currentThread().isInterrupted()) {
                Socket accept = serverSocket.accept();
                executor.submit(new ConnectIOnHandler(accept));
            }
            executor.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ConnectIOnHandler extends Thread {

    private Socket socket;

    public ConnectIOnHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted() && !socket.isClosed()) {
        }
    }
}