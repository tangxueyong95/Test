package com.day8;

import java.util.Scanner;

public class _04 {
    public static void main(String[] args) {
        String str = new Scanner(System.in).next();
        System.out.println(bD(str));
    }

    public static boolean bD(String str) {
        return str.indexOf("bad") == 0 || str.indexOf("bad") == 1;
    }
}
