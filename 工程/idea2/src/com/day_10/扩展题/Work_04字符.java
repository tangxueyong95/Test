package com.day_10.扩展题;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Work_04字符 {
    public static void main(String[] args) {
        try {
            BufferedReader bis = new BufferedReader(new FileReader("F:\\我的代码\\工程1\\idea2\\src\\text.txt"));
            BufferedWriter bos = new BufferedWriter(new FileWriter("F:\\我的代码\\工程1\\idea2\\src\\text1.txt"));
            ArrayList<String> list = new ArrayList<>();
            try (bis; bos) {
                String s;
                while ((s=bis.readLine()) != null) {
                    Collections.addAll(list, s);
                }
                Collections.reverse(list);
                for (String str : list) {
                    bos.write(str);
                    bos.newLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
