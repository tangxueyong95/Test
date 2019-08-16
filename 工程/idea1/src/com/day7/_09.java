package com.day7;

import java.util.Random;
import java.util.Scanner;

public class _09 {
    public static void main(String[] args) {
        int[] arr=new int[5];
        SL(arr);
        DY(arr);
    }

    //将5个1~99的数添加到数组并打印出来
    public static void SL(int[] arr) {
        System.out.println("随机生成的数组中的元素为：");
        for (int i = 0; i < arr.length; i++) {
            arr[i]=new Random().nextInt(99)+1;
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    //筛选数组中输入数的倍数的数组元素
    public static void DY(int[] arr) {
        System.out.println("请输入一个2-5之间的数：");
        int m=new Scanner(System.in).nextInt();
        System.out.println("符合条件的元素为：");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]%m==0) {
                System.out.print(arr[i]+" ");
            }
        }
        System.out.println();
    }
}
