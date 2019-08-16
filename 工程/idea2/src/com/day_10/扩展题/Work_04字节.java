package com.day_10.扩展题;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class Work_04字节 {

    public static void main(String[] args) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("F:\\我的代码\\idea2\\src\\text.txt"));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("F:\\我的代码\\idea2\\src\\text1.txt"));
        ArrayList<String> list = new ArrayList<>();
        try (bis; bos) {
            byte[] bytes = new byte[1024 * 8];
            int b;
            if ((b=bis.read(bytes)) != -1) {
                String[] split = new String(bytes,0,b).split("\r\n");
                Collections.addAll(list, split);
            }
            Collections.reverse(list);
            for (String s : list) {
                bos.write((s+"\r\n").getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
