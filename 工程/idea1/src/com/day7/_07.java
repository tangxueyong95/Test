package com.day7;

import java.util.ArrayList;
import java.util.Scanner;

public class _07 {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        Integer[] b = {1, 8, 26, 45, 60, 78, 99};
        TJ(a, b);
        //Collections.addAll(a, b);
        CL(a, tJ());
        System.out.println(a);//打印集合
    }

    //添加数组中的元素到集合
    public static void TJ(ArrayList<Integer> a, Integer[] b) {
        for (int i = 0; i < b.length; i++) {
            a.add(b[i]);
        }
    }

    //添加一个0~100之间的数
    public static int tJ() {
        Scanner sc = new Scanner(System.in);
        boolean i = true;
        int j = 0;
        while (i) {
            System.out.println("请输入0~100之间的数：");
            j = sc.nextInt();
            if (j > 0 && j < 100) {
                i = false;//终止循环
            }
        }
        return j;
    }

    //添加一个数
    public static void CL(ArrayList<Integer> a, int b) {
        for (int i = 0; i < a.size(); i++) {
            if (b <= a.get(i)) {
                a.add(i, b);//添加一个数到比这数大的后面
                break;//终止循环
            }
        }
    }
}
