package com.day_25.面试题;

public class B {
    public static void main(String[] args) {
        System.out.println(b());
    }

    private static int b() {
        int i = 0;
        try {
            i = 2;
            return i;
        } finally {
            i = 1;
            return i;
        }
    }
}
