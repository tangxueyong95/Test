package com.day4;

public class A {
    static int a, b;

    public static void main(String[] args) {
        a = 1;
        b = 2;
        System.out.println(a);
        System.out.println(b);
        B();
        System.out.println(a);
        System.out.println(b);
    }

    public static void B() {
        a = a + b;
        b = b + a;
    }

}
