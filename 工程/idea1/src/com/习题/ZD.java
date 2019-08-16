package com.习题;

public class ZD {
    public static void main(String[] args) {
        int i, n;
        double j;
        i = 8848;
        j = 0.001;
        n = 0;
        while (j < i) {
            j = j * 2;
            n++;
        }
        System.out.println("对折后纸的厚度为：" + j);
        System.out.println("对折次数为：" + n);
    }
}
