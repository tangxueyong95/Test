package com.day7;

import java.util.ArrayList;
import java.util.Random;

public class _01 {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        Random1(a);
        prin(a);
    }


    public static void Random1(ArrayList a) {
        for (int i = 0; i < 10; i++) {
            Integer b = new Random().nextInt(100) + 1;//生成10个1~100的随机整数
            a.add(b);//将生成的数添加到ArrayList
        }
    }

    //打印出ArrayList
    public static void prin(ArrayList a) {
        for (int i = 0; i < a.size(); i++) {
            System.out.print(a.get(i) + " ");
        }
    }
}
