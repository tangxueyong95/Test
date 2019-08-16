package com.day8;

import java.util.Arrays;

public class _09 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println(toString(arr));
    }

    public static String toString(int[] arr) {
        if (arr.length == 0) {
            return "[]";
        }else {
            return Arrays.toString(arr);
        }
    }
}
