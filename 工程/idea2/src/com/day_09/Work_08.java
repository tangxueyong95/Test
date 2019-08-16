package com.day_09;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Set;

public class Work_08 {
    public static void main(String[] args) throws Exception {
        Properties p = new Properties();
        FileInputStream fis = new FileInputStream("F:\\我的代码\\idea2\\src\\score.txt");
        p.load(fis);
        FileOutputStream fos = new FileOutputStream("F:\\我的代码\\idea2\\src\\score.txt",true);
        Set<String> set = p.stringPropertyNames();
        for (String s : set) {
            if ("lisi".equals(s)) {
                p.setProperty(s, "90");
            }
        }
        p.store(fos, "lisi,80->lisi<90");
        fos.close();
        fis.close();
    }
}
