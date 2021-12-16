package com.glg.study.bio.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

/**
 * @author glg
 * @version 1.0
 * @date 2021/12/16 15:27
 */
public class UDPServer {

    private DatagramSocket server;

    private int port = 8808;

    private DatagramPacket receivePacket;

    public UDPServer() {
        System.out.println("服务器启动监听在" + port + "端口...");
    }

    public void start() {
        try {
            server = new DatagramSocket(port);
            System.out.println("服务器创建成功，端口号：" + server.getLocalPort());
            while (true) {
                //服务器接收数据
                String msg = null;
                receivePacket = udpReceive();
                InetAddress ipR = receivePacket.getAddress();
                int portR = receivePacket.getPort();
                msg = new String(receivePacket.getData(), 0, receivePacket.getLength(), "utf-8");
                // System.out.println("收到："+receivePacket.getSocketAddress()+"内容："+msg);

                if (msg.equalsIgnoreCase("bye")) {
                    udpSend("来自服务器消息：服务器断开连接，结束服务！",ipR,portR);
                    System.out.println(receivePacket.getSocketAddress()+"的客户端离开。");
                    continue;
                }
                System.out.println("建立连接："+receivePacket.getSocketAddress());

                String now = new Date().toString();
                String hello = "From 服务器：&" + now + "&" + msg;
                udpSend(hello,ipR,portR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DatagramPacket udpReceive() throws IOException {
        DatagramPacket receive;
        byte[] buffer = new byte[65535];
        receive = new DatagramPacket(buffer, buffer.length);
        server.receive(receive);
        return receive;
    }

    private void udpSend(String msg, InetAddress ip, int port) throws IOException {
        DatagramPacket sendPacket;
        byte[] dataSend = msg.getBytes();
        sendPacket = new DatagramPacket(dataSend, dataSend.length, ip, port);
        server.send(sendPacket);
    }

    public static void main(String[] args) {
        new UDPServer().start();
    }
}
