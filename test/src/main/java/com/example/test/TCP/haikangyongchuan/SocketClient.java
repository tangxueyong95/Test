package com.example.test.TCP.haikangyongchuan;

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
    private int port = 8082;// 默认连接到端口 8082

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
                String str="40400800010135340d13011440e20100000000000000000009000215011535340d1301149f2323";
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
        /*int[] ints = ServerThread.hexStringToByteArray("f1fb09000000");
        System.out.println(Arrays.toString(ints));
        String s = ServerThread.ByteToIp(ints);
        System.out.println(s);*/
        /*String s="40400800010135340d13011440e20100000000000000000009000215010335340d1301148D2323";
        String substring = ServerThread.Substring(s,2,25);
        System.out.println(substring);
        String checksum = ServerThread.checksum(substring);
        System.out.println(checksum);*/
//        int i = ServerThread.toInt("0201");
//        System.out.println(i);
//        String s1 = Long.toHexString(111111111111111L).toUpperCase();
//        System.out.println(s1);
//        Long i = Long.parseLong("FFFFFFFFFFFF",16);
//        System.out.println(i);
//        String s = ServerThread.deviceNumberToString(71339423563776L);
//        System.out.println(s);
//        String date = ServerThread.ByteToDate(ServerThread.hexStringToByteArray("35340D130114"));
//        System.out.println(date);
    }
}