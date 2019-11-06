package com.day_25.排序算法.交换排序;

import java.util.Arrays;

public class 冒泡排序 {
    public static void main(String[] args) {
        int[] arr = {4, 2, 1, 3, 5};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void bubbleSort(int[] arr) {
        //动态边界
        int n = arr.length;
        //记录数组最后进行排序的位置
        int m = 0;
        for (int i = 0; i < arr.length; i++) {
            boolean flag = true;
            for (int j = 1; j < n; j++) {
                if (arr[j - 1] > arr[j]) {
                    int t = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = t;
                    flag = false;
                    m = j;
                }
            }
            n = m;
            if (flag) {
                break;
            }
        }
    }
}
