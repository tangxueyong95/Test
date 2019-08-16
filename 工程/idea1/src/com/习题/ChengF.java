package com.习题;

public class ChengF {
    public static void main(String[] args) {
        //CFW();
        CFF();
        }
        //用while写九九乘法表
    public static void CFW() {
        int i = 1, j;
        while (i < 10) {
            j = 1;      //要写在循环内，初始化 j 的值
            while (j <= i) {
                System.out.print(j + "*" + i + "=" + j * i + "\t");
                j++;
            }
            i++;
            System.out.println();
        }
    }
        //用for写九九乘法表
    public static void CFF() {
        int i , j;
        for(i=1;i<10;i++){
            for (j = 1; j <= i; j++){
                System.out.print(j + "*" + i + "=" + j * i + "\t");
            }
            System.out.println();
        }
    }
}
