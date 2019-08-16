package com.day8;

import java.util.Scanner;

public class _08 {
    public static void main(String[] args) {
        System.out.println(pD(jS()));
    }
    public static String jS() {
        String str;
        while (true) {
            System.out.println("请输入一个长度大于3的字符串：");
            str = new Scanner(System.in).next();
            if (str.length() >= 3) {
                break;
            }
        }
        return str;
    }

    public static int pD(String a) {
        int n=0;
        for (int i = 0; i < a.length()-2; i++) {
            if(a.charAt(i)==a.charAt(i+1)&&a.charAt(i)==a.charAt(i+2)){
                n++;
            }
        }
        return n;
    }
}
