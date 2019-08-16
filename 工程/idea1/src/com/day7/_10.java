package com.day7;

import java.util.ArrayList;
import java.util.Scanner;

public class _10 {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        SL(a, b);
        System.out.println("偶数集合元素为：");
        dY(a);
        System.out.println("奇数集合元素为：");
        dY(b);
    }

    //添加5个100~200的整数
    public static void SL(ArrayList<Integer> a, ArrayList<Integer> b) {
        for (int i = 1; i <= 5; i++) {
            System.out.println("第" + i + "个数：");
            int m = new Scanner(System.in).nextInt();   //输入一个整数
            if (m < 100 || m > 200) {   //判断是否超出范围
                System.out.println("数字超出范围，请重新输入");
                i--;    //超出范围的数不计入个数中
                continue;   //终止当前循环，开始下一个循环
            }
            if (m % 2 == 0) {
                a.add(m);
            } else {
                b.add(m);
            }
        }
    }

    //打印出集合的元素
    public static void dY(ArrayList<Integer> n) {
        for (int i = 0; i < n.size(); i++) {
            System.out.print(n.get(i) + " ");
        }
        System.out.println();
    }
}
