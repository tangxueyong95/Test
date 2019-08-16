package com.day_25;

import java.util.Arrays;

public class 冒泡排序优化 {
    public static void main(String[] args) {
        int[] array = {
                3, 6, 2, 7, 9, 5, 0, 1, 4, 8
        };
// 程序有效运行的次数
        int runCount = 0;
// 一共遍历的次数
        int allCount = 0;
        int max = 0;
// flag判断排序是否完成 true-完成；false-未完成
        boolean flag;
// 无序数组循环边界，默认为数组长度array.length - 1
        int sortBorder = array.length - 1;
//  记录数组最后进行排序的位置
        int lastChange = 0;

// 一次遍历，在倒序情况下最少遍历的次数
        for (int i = 0,len=array.length-1; i<len; i++) {
            // 每次循环重置flag为true
            flag = true;
            // 二次遍历，将相对最大的数放到数组底部
            for (int j = 0; j < sortBorder; j++) {
                if (array[j] > array[j + 1]) {
                    max = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = max;
                    runCount += 1;
                    // 进入循环表示数组未排序完成，需再次循环
                    flag = false;
                    // 记录数组最后进行排序的位置
                    lastChange = j;
                }
                allCount += 1;
            }
            // 动态设置无序数组循环边界
            sortBorder = lastChange;
            // 如果已经完成排序，则跳出循环
            if (flag) {
                break;
            }
        }
        System.out.println("runCount = " + runCount);
        System.out.println("allCount = " + allCount);
        System.out.println(Arrays.toString(array));

    }
}
