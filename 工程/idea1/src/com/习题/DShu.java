package com.习题;

import java.util.Scanner;

public class DShu {
    static int i, n;

    public static void main(String[] args) {
        //SXF();
        SXW();
        System.out.println();
        System.out.println("1000以内的水仙花数有：" + n + "个。");
    }

    //用for写水仙花数
    public static void SXF() {
        for (i = 100; i < 1000; i++) {
            int j, k, l;
            j = i / 100;
            k = (i % 100) / 10;
            l = i % 10;
            if (i == Lf(j) + Lf(k) + Lf(l)) {
                System.out.print(i + " ");
                n++;
                if (n % 5 == 0) {
                    System.out.println();
                }
            }
        }
    }

    //用while写水仙花数
    public static void SXW() {
        i = 100;
        while (i < 1000) {
            int j, k, l;
            j = i / 100;
            k = (i % 100) / 10;
            l = i % 10;
            if (i == Lf(j) + Lf(k) + Lf(l)) {
                System.out.print(i + " ");
                n++;
                if (n % 5 == 0) {
                    System.out.println();
                }
            }
            i++;
        }
    }

    static int Lf(int i) {
        int m = i * i * i;
        return m;
    }
}
