package com.day_25.排序算法.插入排序;

import java.util.Arrays;

public class 直接插入排序 {
    public static void main(String[] args) {
        int[] arr = {4, 2, 1, 5, 3};
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    //将要排序的数插入到之前以排好的数中
    private static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0 && arr[j] < arr[j - 1]; j--) {
                int t = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = t;
            }
        }
    }
}
