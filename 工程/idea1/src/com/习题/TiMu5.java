package com.习题;

import java.util.Scanner;

public class TiMu5 {
    public static void main(String[] args) {
        int i, j;
        Scanner sc1 = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        i = sc1.nextInt();
        j = sc2.nextInt();
        System.out.println(QH(i, j));
        BJ(i, j);
        BJ1(i, j);
        JZ(i, j);
    }

    static int QH(int i, int j) {
        int M;
        M = i + j;
        return M;
    }

    static void BJ(int i, int j) {
        if (i != j) {
            System.out.println("两数不相等");
        } else
            System.out.println("两数相等");
    }

    static void BJ1(int i, int j) {
        if (i > j) {
            System.out.println(i + "较大");
        } else
            System.out.println(j + "较大");
    }

    static void JZ(int i, int j) {
        int m, n;
        for (m = 0; m < i; m++) {
            for (n = 0; n < j; n++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

}
