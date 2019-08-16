package com.day4;

public class _7 {
    public static void main(String[] args) {
        int a, b, c;
        a = 2;
        b = 3;
        c = 3;
        System.out.println(QH(a, b, c));
    }

    public static int QH(int A, int B, int C) {
        if (A != B && A != C && B != C) {
            return A + B + C;
        } else if (A == B && B != C) {
            return C;
        } else if (A == C && B != C) {
            return B;
        } else if (B == C && A != B) {
            return A;
        } else {
            return 0;
        }
    }
}
