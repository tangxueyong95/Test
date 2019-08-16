package com.习题;

import java.util.Scanner;

public class FourSea {
    public static void main(String[] args) {
        SL();
    }

    //输入年份
    public static void SL() {
        Scanner sc = new Scanner(System.in);
        System.out.println("******欢迎使用四季月份程序******");
        System.out.println("******退出请输入-1 !!!******");
        while (true) {
            System.out.println("输入要查询的月份：");
            int i = sc.nextInt();
            if (i > 0 && i < 13) {
                Sw(i);
            } else if (i == -1) {
                break;
            } else
                System.out.println("请输入正确的月份：");
        }
    }

    //判段季节
    static void Sw(int i) {
        switch (i) {
            case 3:
            case 4:
            case 5:
                System.out.println("******春季******");
                break;
            case 6:
            case 7:
            case 8:
                System.out.println("******夏季******");
                break;
            case 9:
            case 10:
            case 11:
                System.out.println("******秋季******");
                break;
            case 12:
            case 1:
            case 2:
                System.out.println("******冬季******");
                break;
        }
    }
}
