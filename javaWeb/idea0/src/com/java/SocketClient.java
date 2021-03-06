package com.java;

import java.io.DataInputStream;//导入DataInputStream类
import java.io.DataOutputStream;//导入DataOutputStream
import java.io.IOException;//导入IOException类
import java.net.Socket;//导入Socket类
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 注意用到的输入输出流DataInputStream和DataOutputStream，成对出现，最好用字节流
 */
// 客户端类
public class SocketClient {//创建公共类
    private String host = "localhost";// 默认连接到本机 localhost 223.84.188.60
    private int port = 8082;// 默认连接到端口8189

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

                DataInputStream in = new DataInputStream(socket
                        .getInputStream());// 读取服务器端传过来信息的DataInputStream

                DataOutputStream out = new DataOutputStream(socket
                        .getOutputStream());// 向服务器端发送信息的DataOutputStream
                String str="40400800010135340d13011440e20100000000000000000009000215011535340d1301149f2323";
                System.out.println(str.length());
                byte[] bytes1 = HexConvert.hexStringToBytes(str);
                out.write(bytes1);
                socket.shutdownOutput();
                byte[] bytes = new byte[1024];
                int len = 0;
                String string =null;
                while((len = in.read(bytes)) != -1){
                    string = new String(bytes,0,len);
                    System.out.println(string);
                }
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
    }
}