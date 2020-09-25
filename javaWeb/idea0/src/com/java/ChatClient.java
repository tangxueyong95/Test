package com.java;

import java.io.DataInputStream;//导入DataInputStream类
import java.io.DataOutputStream;//导入DataOutputStream
import java.io.IOException;//导入IOException类
import java.net.Socket;//导入Socket类
import java.util.Scanner;//导入Scanner类

/**
 * 注意用到的输入输出流DataInputStream和DataOutputStream，成对出现，最好用字节流
 */
// 客户端类
public class ChatClient {//创建公共类
    private String host = "localhost";// 默认连接到本机
    private int port = 8189;// 默认连接到端口8189

    public ChatClient() {

    }

    // 连接到指定的主机和端口
    public ChatClient(String host, int port) {//构造方法
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


                Scanner scanner = new Scanner(System.in);// 装饰标准输入流，用于从控制台输入

                while (true) {
                    String send = scanner.nextLine();//读取控制台输入的内容
                    System.out.println("客户端：" + send);//输出键盘输出内容提示 ，也就是客户端向服务器端发送的消息
                    // 把从控制台得到的信息传送给服务器
                    out.writeUTF("客户端：" + send);//将客户端的信息传递给服务器
                }

            } finally {
                socket.close();//关闭Socket监听
            }
        } catch (IOException e) {//捕获异常
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {//主程序方法
        new ChatClient().chat();//调用chat方法
    }
}
