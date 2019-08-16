package com.day_09;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class java1_7关流 {
    public static void main(String[] args) {

        try (FileInputStream fis = new FileInputStream("Day09\\aaa.txt");
             FileOutputStream fos = new FileOutputStream("Day09\\bbb.txt")) {
            int b;
            while ((b = fis.read()) != -1) {
                fos.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
