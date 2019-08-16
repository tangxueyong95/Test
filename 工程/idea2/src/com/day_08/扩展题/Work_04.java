package com.day_08.扩展题;

import java.io.File;

public class Work_04 {
    public static void main(String[] args) {
        File f = new File("idea2\\src\\com\\day_04");
        File[] files = f.listFiles(a -> a.isFile() && a.getName().endsWith(".java"));
        for (File file : files) {
            System.out.println(file);
        }
    }
}
