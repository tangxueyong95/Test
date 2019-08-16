package com.day_08.扩展题;

import java.io.File;
import java.util.Scanner;

public class Work_06 {
    public static void main(String[] args) {
        String s = new Scanner(System.in).next();
        File file = new File(s);
        System.out.println(fileSize(file));
    }

    public static long fileSize(File file) {

        int n = 0;
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isFile()) {
                n += file1.length();
            }
            if (file1.isDirectory()) {
                n += fileSize(file1);
            }
        }
        return n;
    }
}
