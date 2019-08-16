package com.day8;

import java.util.Scanner;

public class _06 {
    public static void main(String[] args) {
        jH(jS());
    }
    public static String jS() {
        String str;
        while (true) {
            System.out.println("请输入一个长度大于2的字符串：");
            str = new Scanner(System.in).next();
            if (str.length() >= 2) {
                break;
            }
        }
        return str;
    }

    public static void jH(String a) {
       char b = a.charAt(a.length()-1);
       char c = a.charAt(a.length()-2);
        System.out.println(a.substring(0,a.length()-2)+b+c);
    }
}
