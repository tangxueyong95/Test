package com.day4;

public class _1 {
    static int i;

    public static void main(String[] args) {
        int a, b, c;
        float m, n;
        a = 1;
        b = 2;
        c = 3;
        m = 1.2f;
        n = 1.4f;
        System.out.println("两整数和：" + QH(a, b));
        System.out.println("两小数和：" + QH(m, n));
        XD(a, b);
        System.out.println("两数中较大的为：" + BJ(a, b));
        JZ(b, c);
        System.out.println("三个数中较大的为：" + BJ(a, b, c));
        QH1();
        QH2(10);
        QS();
    }

    //两整数求和
    public static int QH(int a, int b) {
        int m = a + b;
        return m;
    }

    //两小数求和
    public static float QH(float a, float b) {
        float m = a + b;
        return m;
    }

    //1~100之间所有能被3整除数字的个数
    public static void QS() {
        int m = 0;
        for (i = 1; i < 100; i++) {
            if (i % 3 == 0) {
                m++;
            }
        }
        System.out.println("1~100之间所有能被3整除数字的个数为：" + m);
    }

    //1~100之间的奇数和
    public static void QH1() {
        int m = 0;
        for (i = 1; i < 100; i += 2) {
            m += i;
        }
        System.out.println("1~100之间的奇数和为：" + m);
    }

    //1~n之间的偶数和
    public static void QH2(int n) {
        int m = 0;
        for (i = 2; i < n; i += 2) {
            m += i;
        }
        System.out.println("1~" + n + "之间的偶数和为：" + m);
    }

    //判断两整数是否相等
    public static void XD(int a, int b) {
        if (a == b) {
            System.out.println("相等");
        } else {
            System.out.println("不相等");
        }
    }

    //比较两整数的大小，获取较大的数
    public static int BJ(int a, int b) {
        /*if (a >= b) {
            return a;
        } else {
            return b;
        }*/
        return a > b ? a : b;
    }

    //比较三个整数的大小，获取较大的数
    public static int BJ(int a, int b, int c) {
        /*if (c >= BJ(a, b)) {
            return c;
        } else {
            return BJ(a, b);
        }*/
        return c > BJ(a, b) ? c : BJ(a, b);
    }

    //m行n列星型矩阵
    public static void JZ(int m, int n) {
        for (i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
