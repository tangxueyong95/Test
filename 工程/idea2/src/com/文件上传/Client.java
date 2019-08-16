package com.文件上传;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1",9999);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("com\\stu.txt"));
        BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());
        int len;
        while ((len = bis.read())!=-1){
            bos.write(len);
        }
        bos.flush();
        s.shutdownOutput();//仅仅是关流的操作,在后面关流的话,服务器就读写不到文件
//        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        接收回写
        BufferedInputStream bis1 = new BufferedInputStream(s.getInputStream());
        byte[] arr = new byte[1024*8];
        int len1;
        while((len1 = bis1.read(arr))!=-1) {
            System.out.println(new String(arr, 0, len1));
        }
        bis1.close();
       // bos.close();//此处就不用关流了,关流就会报异常
        bis.close();
        s.close();
    }
}
