package com.day_11.扩展题.Work_01;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8002);

        while (true) {
            Socket socket = server.accept();
            new Thread(() -> {
                    try (DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                         OutputStream dos = socket.getOutputStream()
                    ) {
                        byte[] b = new byte[1024 * 8];
                        int c;
                        while ((c = dis.read(b)) != -1) {
                            System.out.println(new String(b, 0, c));
                        }
                        dos.write("上传成功".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }).start();
        }
    }
}
