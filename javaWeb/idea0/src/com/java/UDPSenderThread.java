package com.java;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSenderThread {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();

        String serial = "*00007VERSION\\n1$";//串口字符串

        String hex = HexConvert.convertStringToHex(serial);//转化为十六进制字符串：2a303030303756455253494f4e5c6e3124

        byte[] buf = HexConvert.hexStringToBytes( hex );//将十六进制字符串转为字节数组

//将数据打包
        DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("127.0.0.1"), 6070);

        socket.send(packet);

        socket.close();

    }
}
