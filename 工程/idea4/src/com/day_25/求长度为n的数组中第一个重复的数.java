package com.day_25;

public class 求长度为n的数组中第一个重复的数 {
    public static void main(String[] args) {
        int[] a = {2, 3, 1, 0, 2, 5};
        int[] b = new int[1];
        boolean b1 = duplicate(a, a.length, b);
        System.out.println(b[0]);
    }

    public static boolean duplicate(int numbers[], int length, int[] duplication) {
        for (int i = 0; i < length; i++) {
            if (numbers[i] != i) {
                if (numbers[i] == numbers[numbers[i]]) {
                    duplication[0] = numbers[i];
                    return true;
                }
                swap(numbers, i, numbers[i]);
            }
        }
        return false;
    }

    private static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
