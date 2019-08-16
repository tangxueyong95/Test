package com.day8;

public class Str {
    public static void main(String[] args) {
        String a = "abc";
        String b = new String("abc");
        System.out.println(a.equals(b));//true
        System.out.println(a==b);//false    a的值和b的地址比较
        System.out.println(b);//b.toString()
    }
}
