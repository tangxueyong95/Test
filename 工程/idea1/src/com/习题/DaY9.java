package com.习题;

public class DaY9 {
    public static void main(String[] args) {
        //DF();
        DW();
    }
         //用for求100以内不包含9的数
    public static void DF() {
        int i = 1, n = 0;
        for (; i < 100; i++) {         //当 i 的初始化放在外面时，判断语句前要加 ;
            if (i % 10 == 9 || i / 10 == 9) {
                continue;
            } else {
                System.out.print(i + " ");
                n++;
                if (n % 5 == 0) {
                    System.out.println();
                }
            }
        }
    }

        //用while求100以内不包含9的数
    public static void DW() {
        int i = 1,n=0;
        while (i < 100) {
            if (i % 10 == 9 || i / 10 == 9) {
            } else {
                System.out.print(i + " ");
                n++;
                if (n % 5 == 0) {
                    System.out.println();
                }
            }
            i++;
        }
    }
}
