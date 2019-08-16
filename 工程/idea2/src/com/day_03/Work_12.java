package com.day_03;

import java.util.HashSet;
import java.util.Random;

public class Work_12 {
    public static void main(String[] args) {
        HashSet<Integer> red = new HashSet<>();
        while (red.size() < 6) {
            Integer a = new Random().nextInt(33) + 1;
            red.add(a);
        }
        for (Integer integer : red) {
            System.out.print(integer + " ");
        }
        System.out.println(new Random().nextInt(16) + 1);
    }
}
