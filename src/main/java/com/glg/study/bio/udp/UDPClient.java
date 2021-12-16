package com.glg.study.bio.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author glg
 * @version 1.0
 * @date 2021/12/16 11:06
 */
public class UDPClient {

    private static final int MAX_PACKET_SIZE = 65535;

    private InetAddress remoteIP;

    private int remotePort;

    private static DatagramSocket socket;

    //实例化一个数据报套接字，用于与服务器端进行通信。
    //与TCP不同，UDP中只有DatagramSocket一种套接字，不区分服务端和客户端，创建的时候并不需要指定目的地址，
    //这也是TCP协议和UDP协议最大的不同点之一
    public UDPClient(String remoteIP,String remotePort) throws IOException {
        this.remoteIP = InetAddress.getByName(remoteIP);
        this.remotePort = Integer.parseInt(remotePort);
        //创建UDP套接字，系统随机选定一个未使用的UDP端口绑定
        socket = new DatagramSocket();
    }

    //定义一个数据的发送方法
    public void send(String msg){
        try {
            //将待发送的字符串转为字节数组
            byte[] outData = msg.getBytes("utf-8");
            //构建用于发送的数据报文，构造方法中传入远程通信方（服务器）的ip地址和端口
            DatagramPacket outPacket = new DatagramPacket(outData,outData.length,remoteIP,remotePort);
            //给UDP发送数据报
            socket.send(outPacket);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String receive(){
        String msg;
        //准备空的数据报文
        DatagramPacket inPacket=new DatagramPacket(new byte[MAX_PACKET_SIZE],MAX_PACKET_SIZE);
        try {
            //读取报文，阻塞语句，有数据就装包在inPacket报文中，以装完或装满为止
            socket.receive(inPacket);
            //将接收到的字节数组转为对应的字符串
            msg = new String(inPacket.getData(),0,inPacket.getLength(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            msg=null;
        }
        return msg;
    }

    public void close() {
        if (socket != null) {
            socket.close();
        }
    }
}
