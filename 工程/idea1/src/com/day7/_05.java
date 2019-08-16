package com.day7;

import java.util.Random;
import java.util.Scanner;

public class _05 {
    public static void main(String[] args) {
        int a = new Random().nextInt(11) + 50;
        Cz(a);
    }

    public static void Cz(int a) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int i = sc.nextInt();
            if (i == a) {
                System.out.println("猜中了");
                break;
            } else if (i > a) {
                System.out.println("大了");
            } else {
                System.out.println("小了");
            }
        }
    }
}
