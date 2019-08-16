package com.day_02;

import java.util.ArrayList;
import java.util.Random;

public class Work_10 {
    public static void main(String[] args) {
        int[] a = new int[10];
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            a[i] = new Random().nextInt(100) + 1;
            if (a[i] > 10) {
                list.add(a[i]);
            }
        }
        System.out.println(list);
    }
}
