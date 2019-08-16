package com.day8;

import java.util.Scanner;

public class _07 {
    public static void main(String[] args) {
        double a = new Scanner(System.in).nextDouble();
        //System.out.println(Math.round(a * 100) / 100.0);//取两位小数，四舍五入
        qS(a);
    }

    public static void qS(double a) {
        String str =String.valueOf(a);
        int i = str.indexOf(".");
        System.out.println(str.substring(0, i + 3));
    }
}
