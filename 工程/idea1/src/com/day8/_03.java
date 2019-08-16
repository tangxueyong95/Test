package com.day8;

import java.util.Scanner;

public class _03 {
    public static void main(String[] args) {
        System.out.println(bJ(jS()));
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

    public static boolean bJ(String str) {
        if (str.substring(0, 2).equals(str.substring(str.length() - 2))) {
            return true;
        }
        return false;
    }
}
