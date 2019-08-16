package com.day_01;

public class Work_08 {
    public static void main(String[] args) {
        char[] c= {'i','t','c','a','s','a'};
        System.arraycopy(c,1,c,c.length-1,1);
        System.out.println(String.valueOf(c));
    }
}
