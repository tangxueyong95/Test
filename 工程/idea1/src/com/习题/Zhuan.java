package com.习题;

import java.util.Scanner;

public class Zhuan {
    public static void main(String[] args) {
        int i, j, k;
        while (true) {
            Scanner sc = new Scanner(System.in);
            i = sc.nextInt();
            j = sc.nextInt();
            k = sc.nextInt();
            System.out.println(makeBricks(i, j, k));
        }
    }

    public static boolean makeBricks(int i, int j, int k) {
        if (k == i + j * 5) {
            return true;
        } else
            return false;
    }
}
