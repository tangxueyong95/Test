package com.day_08.扩展题;

import java.util.Scanner;

public class Work_02 {

    public static void main(String[] args) {

        while (true) try {
            int s = new Scanner(System.in).nextInt();
            int small = 1, big = 0;
            System.out.println(multiplication(small, big, s));
            break;
        } catch (Exception e) {
            System.out.println("请输入整数");
        }
    }

    public static String multiplication(int small, int big, int monthCount) {
        if (monthCount == 0)
            return "大兔子数量："+big+" 小兔子数量："+small+" 兔子总数："+(big + small);
        else
            return multiplication(big, small + big, monthCount - 1);
    }
}
