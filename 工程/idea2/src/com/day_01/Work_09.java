package com.day_01;

public class Work_09 {
    public static void main(String[] args) {
        String a = "";
        long a1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            a += "java1_7关流";
        }
        long a2 = System.currentTimeMillis();
        System.out.println(a2 - a1 + "毫秒");
        StringBuilder str = new StringBuilder();
        long b1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            str.append("java1_7关流");
        }
        long b2 = System.currentTimeMillis();
        System.out.println(b2 - b1 + "毫秒");
    }
}
