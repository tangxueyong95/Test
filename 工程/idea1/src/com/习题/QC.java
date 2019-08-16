package com.习题;

import javax.management.DynamicMBean;

public class QC {
    static int a[] = {6, 2, 9, 6, 1, 2, 6, 7, 8};
    static int i, j, k, n;
    static int[] b;

    public static void main(String[] args) {
        DY(QC1(a));
        DY(QC2(a));
    }

    //数组去重①
    public static int QC1(int[] a) {
        n = 0;
        b = new int[a.length];
        for (i = 0; i < a.length; i++) {
            for (j = 0; j <= i; j++) {
                if (a[i] == a[j] && i != j) {
                    break;
                }
                if (a[i] == a[j] && i == j) {
                    b[n] = a[i];
                    n++;
                }
            }
        }
        return n;
    }

    //数组去重②
    public static int QC2(int[] a) {
        n = 0;
        b = new int[a.length];
        boolean f = false;
        for (i = 0; i < a.length; i++) {
            int m = a[i];
            for (j = 0; j < b.length; j++) {
                if (m == b[j] && m != 0) {
                    f = true;
                    break;
                } else
                    f = false;
            }
            if (f == false && m != 0) {
                b[n] = m;
                n++;
            }
        }
        return n;
    }

    //打印数组
    public static void DY(int n) {
        int c[] = new int[n];
        System.out.print("{");
        for (k = 0; k < n; k++) {
            c[k] = b[k];
            System.out.print(c[k]);
            if (k != n - 1) {
                System.out.print(",");
            }
        }
        System.out.println("}");
    }
}