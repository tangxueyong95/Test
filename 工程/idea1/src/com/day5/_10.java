package com.day5;

import java.util.Random;

public class _10 {
    public static void main(String[] args) {
        int[] a = new int[5];
        randomCount(a);
        printCount(getNewArr(a));

    }

    public static void randomCount(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = new Random().nextInt(46) + 5;
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static int[] getNewArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 10) {
                arr[i] = 0;
            }
        }
        return arr;
    }

    public static void printCount(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
