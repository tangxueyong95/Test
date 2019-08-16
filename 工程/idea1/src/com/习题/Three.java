package com.习题;

import java.util.Scanner;

public class Three {
    public static void main(String[] args) {
        int a, b, c, n;
        Scanner sc = new Scanner(System.in);
        a = sc.nextInt();
        b = sc.nextInt();
        c = sc.nextInt();
        n = (a > b ? a : b);
        System.out.println(a == b ? "前两个数相等" : "前两个数不相等");
        System.out.println("前两个数中最大的是" + n);
        System.out.println("三个数中最大的是" + (n > c ? n : c));
    }
}
