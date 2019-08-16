package com.day_09.扩展题;

import java.io.FileInputStream;

public class Work_01 {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("F:\\我的代码\\idea2\\src\\test.txt");
        int b, n = 0;
        while ((b = fis.read()) != -1) {
            if ("java1_7关流".equals((char)b+"")) {
                n++;
            }
        }
        fis.close();
        System.out.println("a出现了"+n+"次");
    }
}
