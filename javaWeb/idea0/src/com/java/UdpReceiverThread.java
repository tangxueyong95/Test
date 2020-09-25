package com.java;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpReceiverThread {

    public static void main(String[] args) throws Exception {
// 定义一个接收端，并且指定了接收的端口号
        DatagramSocket socket = new DatagramSocket(6070);

        while (true) {
            byte[] buf = new byte[1024*5];
            // 解析数据包
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            socket.receive(packet);

            String ip = packet.getAddress().getHostAddress();

            buf = packet.getData();

            //将字节数组转换为16进制字符串
            String hexString = HexConvert.BinaryToHexString( buf );//含有空格，如：2A 30 30 30 30 37 56 45 52 53 49 4F 4E 5C 6E 31 24

            hexString =  hexString.replace( " ","" );//去除空格

            String asc = HexConvert.convertHexToString( hexString );//转为ASCII,如：*00007VERSION\n1$

            System.out.println("收到 " + ip + " 发来的消息：" + asc);

        }

    }
}
