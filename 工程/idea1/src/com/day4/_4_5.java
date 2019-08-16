package com.day4;

public class _4_5 {
    public static void main(String[] args) {
        int a, b, c;
        a = 1;
        b = 2;
        c = 3;
        System.out.println(BJ1(a, b, c));
        System.out.println(BJ2(a, b, c));
    }

    public static int BJ1(int a, int b, int c) {
        if (a != b && b != c && a != c) {
            return 0;
        } else if (a == b && b == c) {
            return 20;
        } else {
            return 10;
        }
    }

    public static boolean BJ2(int a, int b, int c) {
        if (a % 10 != b % 10 && b % 10 != c % 10 && a % 10 != c % 10) {
            return false;
        } else {
            return true;
        }
    }
}
