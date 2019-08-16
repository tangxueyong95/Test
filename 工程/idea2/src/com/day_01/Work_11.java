package com.day_01;

public class Work_11 {
    public static void main(String[] args) {
        StringBuilder s = new StringBuilder();
        for (int i = 7; i < 10; i++) {
            for (int i1 = 7; i1 < 10; i1++) {
                for (int i2 = 7; i2 < 10; i2++) {
                    if (i != i1 && i1 != i2 && i != i2) {
                        s.append(i).append(i1).append(i2).append(" ");
                    }
                }
            }
        }
        System.out.println(s);
    }
}
