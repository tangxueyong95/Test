package com.day_01;

public class Work_10 {
    public static void main(String[] args) {
        String[] a = {"010", "3223", "666", "7890987", "123123"};
        int n = 0;
        for (String str : a) {
            StringBuilder s = new StringBuilder(str);
            String s1= s.toString();
            if (s1.equalsIgnoreCase(s.reverse().toString())) {
                System.out.println(s1);
                n++;
            }
        }
        System.out.println(n);
    }
}
