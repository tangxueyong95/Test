package com.example.test.TCP.tuoPu;

import com.example.test.util.HexConvert;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * 注意用到的输入输出流DataInputStream和DataOutputStream，成对出现，最好用字节流
 */
// 客户端类
public class SocketClient {//创建公共类
    private String host = "localhost";// 默认连接到本机 localhost 223.84.188.55
    private int port = 8085;// 默认连接到端口 8085

    public SocketClient() {

    }

    // 连接到指定的主机和端口
    public SocketClient(String host, int port) {//构造方法
        this.host = host;//将构造方法的参数host传递给类变量host
        this.port = port;//将构造方法的参数port传递给类变量port
    }

    public void chat() {//chat方法
        try {
            // 连接到服务器
            Socket socket = new Socket(host, port);//创建Socket类对象

            System.out.println(socket);

            try {

                DataInputStream inputStream = new DataInputStream(socket
                        .getInputStream());// 读取服务器端传过来信息的DataInputStream

                DataOutputStream out = new DataOutputStream(socket
                        .getOutputStream());// 向服务器端发送信息的DataOutputStream
                String str="7e7e7e7e333539373731303430323638383838303131313131313131313131313131313009000012130613192629029931000141225001412250";
//                String str="7e7e7e7e333539373731303430323638383838303131313131313131313131313131313001000002001f";
                byte[] bytes1 = HexConvert.hexStringToBytes(str);
                out.write(bytes1);
                socket.shutdownOutput();
                byte[] buffer = new byte[1024 * 2];
                int len = 0;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((len = inputStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.close();
                byte[] bytes = bos.toByteArray();
                String str1 = HexConvert.BinaryToHexString(bytes);
                System.out.println(str1);
            } finally {
                socket.close();//关闭Socket监听
            }
        } catch (IOException e) {//捕获异常
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {//主程序方法
        new SocketClient().chat();//调用chat方法
//        String str="7e 7e 7e 7e 33 35 39 37 37 31 30 34 30 32 36 38 38 38 38 30 31 31 31 31 31 31 31 31 31 31 31 31 31 31 31 30 09 00 00 12 13 06 13 19 26 29 02 99 31 00 01 41 22 50 01 41 22 50";
//        String s = str.replace(" ", "");
//        System.out.println(s);
    }
}