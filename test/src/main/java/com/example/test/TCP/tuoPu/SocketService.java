package com.example.test.TCP.tuoPu;

import com.example.test.TCP.InformationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//@Service("tSocketService")
public class SocketService {

    @Autowired
    InformationService informationService;
    //监听端口
    private static final int PORT = 8085;
    //开始运行
//    @PostConstruct
    public void startServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void start() throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            //建立服务器的Socket，并设定一个监听的端口PORT
            serverSocket = new ServerSocket(PORT);
            //由于需要进行循环监听，因此获取消息的操作应放在一个while大循环中
            while(true){
                try {
                    //建立跟客户端的连接
                    socket = serverSocket.accept();
                    System.out.println(socket);
                } catch (Exception e) {
                    System.out.println("建立与客户端的连接出现异常");
                    e.printStackTrace();
                }
//                ServerThread thread = new ServerThread(socket);
                ServerThread thread = new ServerThread(socket,informationService);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println("端口被占用");
            e.printStackTrace();
        }
        finally {
            System.out.println("服务端关闭");
            serverSocket.close();
        }
    }

    public static void main(String[] args) {
        try {
            new SocketService().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}