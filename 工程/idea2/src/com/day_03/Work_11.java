package com.day_03;

import java.util.LinkedHashSet;
import java.util.Scanner;

public class Work_11 {
    public static void main(String[] args) {
        String s = new Scanner(System.in).next();
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        char[] c = s.toCharArray();
        for (Character c1 : c) {
            set.add(c1);
        }
        for (Character character : set) {
            System.out.print(character);
        }
    }
}
