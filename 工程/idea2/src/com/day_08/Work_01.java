package com.day_08;

import java.io.File;
import java.io.IOException;

public class Work_01 {
    public static void main(String[] args) {
        File file1 = new File("D:\\aaa\\b.txt");
        File file2 = new File("D:\\aaa\\bbb");
        File file4 = new File("D:\\aaa\\bbb\\java1_7关流.txt");
        File file3 = new File("D:\\aaa");
        if (!file3.exists()) {
            file3.mkdirs();
        }
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }if (!file2.exists()) {
            file2.mkdirs();
        }
        if (!file4.exists()) {
            try {
                file4.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        System.out.println(file1.getName()+" "+file1.length()+" "+file1.getAbsolutePath()+" "+file3.getAbsolutePath());
//        file2.mkdir();

       /* if (file1.isFile()) {
            System.out.println(file1.getName() + "是一个文件");
        } else {
            System.out.println(file1.getName() + "不是一个文件");
        }
        if (file3.isDirectory()) {
            System.out.println(file3.getName() + "是一个文件夹");
        } else {
            System.out.println(file3.getName() + "不是一个文件夹");
        }*/
       String[] s = file3.list();
        for (String s1 : s) {
            System.out.println(s1);
        }
        File[] f = file3.listFiles();
        for (File file : f) {
            System.out.println(file);
        }
        System.out.println(file1);
    }
}
