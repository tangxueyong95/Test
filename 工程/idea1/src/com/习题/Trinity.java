package com.习题;

import java.util.*;

public class Trinity {
    public static void main(String[] args) {
        int a, i, j, k;
        a = new Random().nextInt(900) + 100;
        /*Random A = new Random();
        a = A.nextInt(900) + 100;*/
        i = a / 100;
        j = (a % 100) / 10;
        k = (a % 10);
        System.out.println("数字" + a + "的各位是" + k + ",十位是" + j + ",百位是" + i);
    }
}