package com.day_08.扩展题;

import java.io.File;
import java.util.Scanner;

public class Work_05 {
    public static void main(String[] args) {
        String a = new Scanner(System.in).next();
        File file = new File(a);
        deleteFile(file);
    }

    public static void deleteFile(File file) {

        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isFile()) {
                file1.delete();
            }
            if (file1.isDirectory()) {
                deleteFile(file1);
            }
        }
        file.delete();
    }
}
