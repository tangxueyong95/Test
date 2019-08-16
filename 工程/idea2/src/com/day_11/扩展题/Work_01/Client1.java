package com.day_11.扩展题.Work_01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client1 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8002);
        OutputStream bos = socket.getOutputStream();
        bos.write("哈哈".getBytes());
        socket.shutdownOutput();
        InputStream dis = socket.getInputStream();
        byte[] b = new byte[1024 * 8];
        int c;
        while ((c=dis.read(b)) != -1) {
            System.out.println(new String(b, 0, c));
        }
        dis.close();
        socket.close();
    }
}
