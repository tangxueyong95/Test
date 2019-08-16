package com.day_09;

import java.io.*;
import java.net.URL;

public class 从网上下载图片 {
    public static void main(String[] args) {

        InputStream inputStream =null;
        BufferedOutputStream fos = null;
        try {
            URL url = new URL("网上地址");
            inputStream =url.openStream();
            fos = new BufferedOutputStream(new FileOutputStream("存储地址"));
            byte[] bytes = new byte[1024 * 8];
            int b;
            while ((b = inputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
