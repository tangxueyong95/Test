package com.day5;

public class _05 {
    public static void main(String[] args) {
        int[] a={1,2,3,4,2,5};
        int[] b={1,2,3,3,4,5};
        dY(qB(a,b));
        dY(dY1(a));
        System.out.println(pD1(a));
        System.out.println(pD2(b));
    }
    //5
    public static int[] qB(int[] a, int[] b) {
        int m = 0, n = 0;
        for (int i = 0; i < a.length; i++) {
            m += a[i];
        }
        for (int j = 0; j < b.length; j++) {
            n += b[j];
        }
        if (m >= n) {
            return a;
        }
        return b;
    }

    //6
    public static int[] dY1(int[] a) {
        int[] b = new int[2];
        if (a.length <= 2) {
            return a;
        }
        b[0] = a[0];
        b[1] = a[1];
        return b;
    }

    //7
    public static boolean pD1(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 2 || a[i] == 3) {
                return true;
            }
        }
        return false;
    }

    //8
    public static boolean pD2(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 2 || a[i] == 3) {
                return false;
            }
        }
        return true;
    }
    public static void dY(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
