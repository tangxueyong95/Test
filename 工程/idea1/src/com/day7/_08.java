package com.day7;

import java.util.Scanner;

public class _08 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一串字符串(空格不计)");
        String n = sc.next();
        Cx(n);
    }

    public static void Cx(String n) {
        int j = 0, k = 0, m = 0;
        for (int i = 0; i < n.length(); i++) {
           /* if (Character.isLetter(n.charAt(i))) {
                j++;
            }else if (Character.isDigit(n.charAt(i))) {
                k++;
            }else {
                m++;
            }*/
            if ((n.charAt(i) >= 'a' && n.charAt(i) <= 'z') || (n.charAt(i) >= 'A' && n.charAt(i) <= 'Z')) {
                j++;
            } else if (n.charAt(i) >= '0' && n.charAt(i) <= '9') {
                k++;
            } else {
                m++;
            }
        }
        System.out.println("英文字母个数：" + j);
        System.out.println("数字个数：" + k);
        System.out.println("其它字符个数：" + m);
    }
}
