package com.day_02;

public class Work_11 {
    public static void main(String[] args) {

    }

    public static <T> void swap(T[] a) {
        /*T b;
        b=java1_7关流[0];
        java1_7关流[0]=java1_7关流[1];
        java1_7关流[1]=b;*/
        T b;
        for (int i = 0; i < a.length / 2; i++) {
            b=a[i];
            a[i]=a[a.length-1-i];
            a[a.length-1-i]=b;
        }
    }
}
