package com.day5;

public class _01 {
    public static void main(String[] args) {
        int[] a={1,2,3,4,2,5};
        int[] b={1,2,3,3,4,5};
        System.out.println(bJ(a,b));
        System.out.println(pD(a));
        dY(gG(a));
        System.out.println(jC(a));
    }
    //1
    public static boolean bJ(int[] a, int[] b) {
        if (a[0] == b[0] || a[a.length-1] == b[b.length-1]) {
            return true;
        }
        return false;
    }

    //2
    public static boolean pD(int[] a) {
        if (a[0] == 6 || a[a.length-1] == 6) {
            return true;
        }
        return false;
    }

    //3
    public static int[] gG(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 2 && a[i + 1] == 3) {
                a[i + 1] = 0;
            }
        }
        return a;
    }

    //4
    public static boolean jC(int[] a) {
        int m = 0, n = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 2) {
                m++;
            }
            if (a[i] == 3) {
                n++;
            }
        }
        if (m == 2 || n == 2) {
            return true;
        }
        return false;
    }
    public static void dY(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
