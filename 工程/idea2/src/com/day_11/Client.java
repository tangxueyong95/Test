package com.day_11;


import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


/**
 * 聊天室客户端
 * @author edg
 *
 */

public class Client
{

    /**
     * java.net.Socket
     * 封装了TCP协议，使用它就可以基于TCP协议
     * 进行网络通讯
     * Socket是运行在客户端的
     * 别名;套接字。理解为电话（获取输入输出流）
     */
    private Socket socket;

    //构造方法,初始化客户端

    /**
     * 实例化Socket的时候需要两个参数：
     * 1：服务端地址：通过IP地址可以找到服务的那台计算机
     *    windows:  cmd-> ipconfig
     *    linux:sbin->ipconfig
     * 2：服务端口号：通过服务端口号，可以找到服务端计算机
     * 上的对应应用程序
     *
     * 实例化Socket的过程就是连接过程，若远端计算机没有响应
     * * 则会抛出异常。
     * @throws Exception
     */
    public Client ()throws Exception {
        System.out.println("正在与服务端建立连接....");
        socket = new Socket("192.168.38.65",8088);
        System.out.println("以与服务端建立连接！");
    }
    /**
     * 启动客户端的方法
     */

    public void start() {
        try {
            Scanner scanner=new Scanner(System.in);
            /**
             * 先要求用户输入一个昵称
             */
            String nickName=null;
            while (true) {
                System.out.println("请输入一个昵称;");
                nickName=scanner.nextLine();
                if (nickName.length()>0) {
                    break;
                }
                System.out.println("输入有误！");
            }
            System.out.println("欢迎"+nickName+"，开始聊天吧！");
            //返回输出流子类实现
            /**
             * OutputStream getOutputStream
             * 获取一个字节输出流，通过该流
             * 写出的数据会被发送至远端计算机
             */
            OutputStream out= socket.getOutputStream();
            //转换流，高级刘流，缓冲流
            OutputStreamWriter osw=new OutputStreamWriter(out ,"utf-8");
            //行显示流（高级流），缓冲流,ture代表自动行刷新（回车换行时刷新）
            PrintWriter pw=new PrintWriter(osw,true);
            //将昵称发给服务端
            pw.println(nickName);
            /**
             * 将字符串发送至服务端
             * 启动读取服务端发送过来消息的线程
             */
            ServerHandler handler=new ServerHandler();
            Thread t=new Thread(handler);
            t.start();
            while (true) {
                pw.println(scanner.nextLine()); //输出消息到服务器
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Client client =new Client();
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("启动客户端失败！");
        }
    }

    /**
     * 该线程用来读取服务端发送过来的消息
     * 并输出到客户端控制台显示
     * @author edg
     *
     */
    class ServerHandler implements Runnable{
        public void run() {
            try {
                InputStream in=socket.getInputStream();
                InputStreamReader isr=new InputStreamReader(in,"utf-8");
                BufferedReader br=new BufferedReader(isr);
                String message=null;
                while ((message=br.readLine())!=null) {
                    //执行readLine()方法接收消息时,当服务器无响应消息时会同步阻塞(一直等待服务器的消息)
                    //接收到消息时,消息又不为空,故才一直循环
                    System.out.println(message);
                }
                System.out.println("aaaaaaaaa2");
            } catch (Exception e) {
            }
        }

    }
}

