package com.day4;

public class _2 {
    static int n;

    public static void main(String[] args) {
        SSF();
    }

    //用for打印100以内的素数
    public static void SSF() {
        for (int i = 2; i < 100; i++) {
            PD(i);
        }
    }

    //判断i是否为素数，并打印出来
    public static void PD(int i) {
        for (int j = 2; j <= i; j++) {
            if (i % j == 0 && i != j) {   //有约数,但不是本身
                break;
            }
            if (i == j) {   //有约数,是本身
                System.out.print(i + "\t");
                n++;
                if (n % 5 == 0) {
                    System.out.println();
                }
            }
        }
    }
}
