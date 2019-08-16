package com.day7;

import java.util.Scanner;

public class _06 {
    public static void main(String[] args) {
        DY(SL());
    }

    public static String SL() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入带字母的字符串：");
        boolean i = true;
        String st = null;
        while (i) {
            st = sc.next();
            i = BJ(st);//当i=false;是终止while循环
            if (i) {
                System.out.println("输入错误，请重新输入带字母的字符串：");
            }
        }
        return st;
    }

    public static boolean BJ(String st) {
        for (int i = 0; i < st.length(); i++) {
            /*if (Character.isLetter(st.charAt(i))) {
                i=st.length();
                return false;
            }*/
            if (st.charAt(i) >= 'a' && st.charAt(i) <= 'z') {
                return false;//终止循环，并返回
            }
            if (st.charAt(i) >= 'A' && st.charAt(i) <= 'Z') {
                return false;
            }
        }
        return true;
    }

    public static void DY(String a) {
        int m = 0;//计数
        for (int i = 0; i < a.length(); i++) {
            /*if (Character.isUpperCase(a.charAt(i))) {
                m++;
            }*/
            if (a.charAt(i) >= 'A' && a.charAt(i) <= 'Z') {
                m++;
            }
        }
        System.out.println("输入的字符串中大写字母的个数：" + m);
    }
}
