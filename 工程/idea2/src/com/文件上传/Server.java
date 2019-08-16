package com.文件上传;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9999);
        System.out.println("服务器等待连接中...");
        while (true) {
            Socket socket = ss.accept();
            new Thread(() -> {
                try {
                    System.out.println("服务器连接成功");
                    BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("com\\atxt"));
                    byte[] b = new byte[1024 * 8];
                    int len;
                    while ((len = bis.read(b)) != -1) {
                        bos.write(b,0,len);
                    }
                    //bos.flush();
                    //socket.shutdownInput();
//                    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//                    回写
                    BufferedOutputStream bos1 = new BufferedOutputStream(socket.getOutputStream());
                    bos1.write("上传成功!~".getBytes());//bos为输入文件,bos1才为传输给客户端

                    bos1.close();
                    bos.close();
                    bis.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}