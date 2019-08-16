package com.day4;

public class _3 {
    public static void main(String[] args) {
        System.out.println(A(1, 9));
    }

    public static int A(int a, int b) {
        if (a < 2 || b < 2) {
            return 0;
        } else if (a > 8 || b > 8) {
            return 2;
        } else {
            return 0;
        }
    }
}
