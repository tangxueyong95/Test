package com.day_25.面试题;

public class A {
    static {
        System.out.println("1");
    }

    public A(){
        System.out.println("2");
    }
    public static void main(String[] args) {
        A a =new A();
        a=new A();
    }
}
