package com.习题;

public class SuShu {
    static int i, j, n;

    public static void main(String[] args) {
        SSF();
        System.out.println();
        SSW();
   }

   //用for打印100以内的素数
   public static void SSF() {
        n = 0;
        for (i = 2; i < 100; i++) {
            for (j = 2; j <= i; j++) {
                if (i % j == 0 && i != j) {   //有约数,但不是本身
                    break;
                }
                if (i % j == 0 && i == j) {   //有约数,是本身
                    System.out.print(i + "\t");
                    n++;
                    if (n % 5 == 0) {
                        System.out.println();
                    }
                }
            }

        }
    }

    //用while打印100以内的素数
    public static void SSW() {
        n = 0;
        i = 2;
        while (i < 100) {
            j = 2;
            while (j <= i) {
                if (i % j == 0 && i != j) {
                    break;
                } else if (i % j == 0 && i == j) {
                    System.out.print(i + "\t");
                    n++;
                    if (n % 5 == 0) {
                        System.out.println();
                    }
                }
                j++;
            }
            i++;
        }
    }
}
