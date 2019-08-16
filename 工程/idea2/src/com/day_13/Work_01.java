package com.day_13;

import java.util.function.Predicate;

public class Work_01 {
    public static boolean math(Predicate<Integer> p, Integer a) {
        return p.test(a);
    }

    public static void main(String[] args) {
        Integer[] arr = {-12345, 9999, 520, 0, -38, -7758520, 941213};
        System.out.println(w1(arr));
        System.out.println(arr.length - w1(arr));
        w2(arr);
        w3(arr);
    }

    private static int w1(Integer[] arr) {
        int count = 0;
        for (Integer a : arr) {
            if (math(p1 -> p1 >= 0, a)) {
                count++;
            }
        }
        return count;
    }

    private static void w2(Integer[] arr) {
        int count = 0;
        for (Integer a : arr) {
            if (math(p2 -> Math.abs(p2) > 100, a)&&math(p3 -> p3 % 2 == 0, a)) {
                count++;
            }
        }
        System.out.println(count);
    }

    private static void w3(Integer[] arr) {
        int count = 0;
        for (Integer a : arr) {
            if (math(p3 -> p3 % 2 == 0, a) || math(p1 -> p1 < 0, a)) {
                count++;
            }
        }
        System.out.println(count);
    }
}
