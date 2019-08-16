package com.day4;

public class _6 {
    public static void main(String[] args) {
        int a, b;
        a = 20;
        b = 30;
        System.out.println(blackjack(a, b));
    }

    public static int blackjack(int a, int b) {
        //a,b都大于21
        if (a > 21 && b > 21) {
            return 0;
        }
        //a小，b大
        if (b > 21) {
            return a;
        }
        //a大，b小
        if (a > 21) {
            return b;
        }
        //a,b都小于21
        return a > b ? a : b;
    }
}
