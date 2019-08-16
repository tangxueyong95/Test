package com.day5;

public class _09 {
    //9
    public static void main(String[] args) {
        int[] a = {6, 2, 9, 15, 1, 5, 20, 7, 18};
        jH(a);
        dY(a);
    }

    public static void jH(int[] a) {
        int m = a[0];
        int n = m;
        int j = 0, k = 0;
        for (int i = 1; i < a.length; i++) {
            if (m < a[i]) {
                m = a[i];
                j = i;
            }
            if (n > a[i]) {
                n = a[i];
                k = i;
            }
        }
        a[j] = a[a.length - 1];
        a[a.length - 1] = m;
        a[k] = a[0];
        a[0] = n;
    }

    public static void dY(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
