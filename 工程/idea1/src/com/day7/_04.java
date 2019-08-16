package com.day7;

import java.util.Scanner;

public class _04 {
    static String zh = "admin";
    static String mm = "admin";

    public static void main(String[] args) {
        DL();
    }

    //登陆方法
    public static void DL() {
        Scanner sc = new Scanner(System.in);
        int i = 1;
        String m, n;
        while (i <= 3) {
            System.out.print("账号:");
            m = sc.next();
            System.out.print("密码:");
            n = sc.next();
            if (!m.equalsIgnoreCase(zh) && !n.equalsIgnoreCase(mm)) {//比较账号和密码是否为admin
                if (i == 3) {
                    System.out.println("你已经没机会了");
                    break;
                }
                System.out.println("你还有" + (3 - i) + "次机会！");
                i++;
            } else {
                break;
            }
        }
    }
}
