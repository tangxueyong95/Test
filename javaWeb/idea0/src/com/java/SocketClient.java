package com.java;

import java.io.DataInputStream;//导入DataInputStream类
import java.io.DataOutputStream;//导入DataOutputStream
import java.io.IOException;//导入IOException类
import java.net.Socket;//导入Socket类
import java.util.Date;
import java.util.Scanner;//导入Scanner类

/**
 * 注意用到的输入输出流DataInputStream和DataOutputStream，成对出现，最好用字节流
 */
// 客户端类
public class SocketClient {//创建公共类
    private String host = "localhost";// 默认连接到本机
    private int port = 6000;// 默认连接到端口8189

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

            try {

                DataInputStream in = new DataInputStream(socket
                        .getInputStream());// 读取服务器端传过来信息的DataInputStream

                DataOutputStream out = new DataOutputStream(socket
                        .getOutputStream());// 向服务器端发送信息的DataOutputStream
                out.write("@@".getBytes());
                out.write(new byte[]{00,00});
                out.write(new byte[]{00,00});
                out.write(new byte[]{20,4,3,9,21,16});
                out.write(new byte[]{00,00,00,00,00,00});
                out.write(new byte[]{00,00,00,00,00,00});
                out.write(new byte[]{9,00});
                out.write(new byte[]{02});
                out.write(new byte[]{21,01,01,20,04,03,9,25,33});
                out.write(new byte[]{00});
                out.write("##".getBytes());
                socket.shutdownOutput();
//                String s = in.readUTF();
//                System.out.println(s);
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
    }
}