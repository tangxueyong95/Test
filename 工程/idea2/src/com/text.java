package com;

public class text {
    public static void main(String[] args) {
        int[] a = {2, 1, 1, 3, 2, 3, 1};
        aVoid(a);
    }

    public static void aVoid(int[] a) {
        if (a == null || a.length < 0) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] < 0 || a[i] > a.length - 1) {
                return;
            }
        }
        for (int i = 0; i < a.length; i++) {
            while (a[i] != i) {
                if (a[i] == a[a[i]]) {
                    System.out.println(a[i]);
                    break;
                }
                int temp = a[i];
                a[i] = a[temp];
                a[temp] = temp;
            }
        }
    }
}
