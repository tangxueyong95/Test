package com.day_25.排序算法.交换排序;

import java.util.Arrays;

public class 快速排序 {
    public static void main(String[] args) {
        int[] arr = new int[]{4, 2, 1, 3, 5};
        qSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void qSort(int[] arr, int head, int tail) {
        if (head >= tail || arr == null || arr.length <= 1) {
            return;
        }
        int i = head, j = tail, pivot = arr[(head + tail) / 2];
        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
                j--;
            }
            if (i < j) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
                i++;
                j--;
            } else if (i == j) {
                i++;
            }
        }
        qSort(arr, head, j);
        qSort(arr, i, tail);
    }
}
