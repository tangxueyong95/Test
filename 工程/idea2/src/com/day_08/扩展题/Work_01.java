package com.day_08.扩展题;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Work_01 {
    public static void main(String[] args) {
        String s = new Scanner(System.in).next();
        File f = new File(s);
        if (s.contains(".")) {
            try {
                if (!f.exists()) {
                    f.mkdirs();
                    if (f.isDirectory()) {
                        f.delete();
                    }
                    f.createNewFile();
                }
                System.out.println(f.length());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (f.exists()) {
                f.mkdirs();
            }
            System.out.println(f.length());
        }
        if (f.isFile()) {
            System.out.println("是文件");
        }
    }
}
