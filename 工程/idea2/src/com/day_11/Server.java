package com.day_11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

/**
 * 聊天室服务端
 * 运行时先启动服务端，再启动客户端
 * 永远是客户端先发出请求（连接）
 * @author edg
 *
 */
public class Server {
    /**
     * 运行在服务端的ServerSocket主要负责：
     * 1：向系统申请服务端口（）
     * 	客户端就是通过这个端口与之连接的
     * 2：监听申请的服务端口，当一个客户通过
     * 该端口尝试建立连接时，ServerSocket
     * 会在服务端创建一个Socket与客户端建立连接。
     */
    private ServerSocket server;

    /**
     * 集合allOut保存所有客户端的输出流
     * 共享集合中的数据，注意保护线程安全
     */
    private List<PrintWriter> allOut;
    /**
     * 初始化服务端
     */
    public Server()throws Exception{
        /**
         * 初始化的同时申请服务端口
         */
        server=new ServerSocket(8088);
        allOut=new ArrayList<PrintWriter>();
    }

    //将给定的输出流存入共享集合

    private synchronized void addOut(PrintWriter out){
        allOut.add(out);
    }
    //将给定的输出流从共享集合中删除
    private  synchronized void  removeOut(PrintWriter out) {
        allOut.remove(out);
    }
    //将给定的消息发给所有客户端,遍历输出流集合
    private synchronized void sendMessage(String message) {
        for (PrintWriter out : allOut) {
            out.println(message);
        }
    }
    /**
     * 服务端开始工作方法
     */
    public void start() {
        try {
            /**
             * ServerSocket 的accpet方法是一个阻塞方法，在此等待
             * 作用是监听服务端口，直到一个客户端
             * 连接并建立一个Socket即可与刚连接的客户端
             * 进行交互
             */
            while (true) {
                System.out.println("等待一个客户端连接...");
                Socket socket=server.accept();   //等待连接成功则返回服务端的socket
                //否则一直阻塞等待
                System.out.println("一个客户端连接了！");
                /**
                 * 启动一个线程，来完成与客户端的交互
                 */
                ClientHandler handler=new ClientHandler(socket);
                Thread t=new Thread(handler);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            Server server=new Server();
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务端启动失败！");
        }

    }
    /**
     * 该线程负责处理一个客户端的交互
     * @author edg
     *
     */
    /**
     * 内部类ClientHandler，可以访问Server类的属性
     * @author edg
     *
     */
    class ClientHandler implements Runnable{
        /**
         * 该线程处理客户端的Socket
         */
        private Socket socket;

        //客户端地址信息
        private String host;

        //该用户昵称
        private String nickName;
        public ClientHandler(Socket socket)
        {
            this.socket=socket;
            /**
             * 通过Socket可以获取远端计算机地址信息
             */
            InetAddress address=socket.getInetAddress();
            //获取ip地址
            host=address.getHostAddress();
        }
        public void run() {
            PrintWriter pw=null;
            try {
                System.out.println(host+"上线了！");
                /**
                 * Socket提供的方法
                 * InputStream()  getInputStream()
                 * 该方法获取一个输入字节流，从该流读取的数据
                 * 就是远程客户端计算机发送过来的
                 */
                InputStream in=socket.getInputStream();
                //将字节流转换为字符流，字符流（操作字符专用）存在编码方式
                InputStreamReader isr=new InputStreamReader(in,"utf-8");
                BufferedReader br=new BufferedReader(isr);

                //首先读取一行字符串为昵称
                nickName=br.readLine();
                sendMessage(nickName+"上线了！");
                /**
                 * 通过Socket创建输出流，用于将消息
                 * 发送给客户端
                 */
                OutputStream out=socket.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(out,"utf-8");
                pw=new PrintWriter(osw,true);

                //将客户端的输出流存入共享集合中
                //调用外部类中的方法
                addOut(pw);

                String message=null;

                /**
                 * br.readLine,由于客户掉线，其操作系统的不同，
                 * 读取结果不同
                 * window返回异常，linux返回null
                 */
                while ((message=br.readLine())!=null)
                {
                    //一直监听等待
                    //发给自己
//				    System.out.println(host+"server说："+message);
//				    //往外写，往客户端发
//				    pw.println(host+"说："+message);
                    sendMessage(nickName+"说："+message);

                }
            } catch (Exception e) {

            }finally{
                /**
                 * 处理当前客户端断开
                 */
                //将该客户端的输出流从共享集合中删除
                removeOut(pw);
                System.out.println(host+"下线了！");
                try {
                    socket.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }

    }
}

