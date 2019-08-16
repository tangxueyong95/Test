package com.day_01;

public class Work_03 {
    public static void main(String[] args) {

        String s1 = new String("abc");
        String s2 = "abc";
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        String s3 = "abc";
        String s4 = "abc";
        System.out.println(s3 == s4);
        System.out.println(s3.equals(s4));
        String s5 = "java1_7关流" + "b" + "c";
        String s6 = "abc";
        System.out.println(s5 == s6);
        System.out.println(s5.equals(s6));
        String s7 = "ab";
        String s8 = "abc";
        String s9 = s7 + "c";
        System.out.println(s9 == s8);
        System.out.println(s9.equals(s8));
        System.out.println();
        System.out.println();
    }
}