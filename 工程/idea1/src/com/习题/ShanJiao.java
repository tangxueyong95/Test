package com.习题;

public class ShanJiao {
    static int i, j,n;

    public static void main(String[] args) {
        SJF();
        System.out.println();
        SJW();
    }

    //用for打印三角形
    public static void SJF() {
        for(i=0;i<5;i++){
            for(j=0;j<5-i;j++){
                System.out.print("*");
            }
            System.out.println();
        }
        for(i=0;i<4;i++){
            for(j=0;j<3-i;j++){
                System.out.print(" ");
            }
            for(n=0;n<=i;n++){
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    //用while打印三角形
    public static void SJW() {
        i = 0;
        while (i < 5) {
            j = 0;
            while (j < 5 - i) {
                System.out.print("*");
                j++;
            }
            System.out.println();
            i++;
        }
        i = 0;
        while (i < 4) {
            j = 0;
            while (j < 3 - i) {
                System.out.print(" ");
                j++;
            }
            n = 0;
            while (n <= i) {
                System.out.print("* ");
                n++;
            }
            System.out.println();
            i++;
        }
    }
}
