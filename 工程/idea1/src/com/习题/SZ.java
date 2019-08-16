package com.习题;


import java.util.Scanner;

public class SZ {
    static Scanner sc = new Scanner(System.in);
    static int i,j,m;
    public static void main(String[] args) {
        int k,n;
        m=sc.nextInt();
        int a[]=new int[m];
        k=SQ(a,m);
        m=sc.nextInt();
        int b[]=new int[m];
        n=SQ(b,m);
        if(SB(k,n)){
            DY(a);
        }else{
            DY(b);
        }
    }

    //输入数组并求和
    public static int SQ(int[] a,int b) {
        int h=0;
        for (i = 0; i < b; i++) {
            m = sc.nextInt();
            a[i] = m;
            h+=m;
        }
        return h;
    }

    //打印数组
    public static void DY(int[] a) {
        for(i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
    }
    //两数组和比较
    static boolean SB(int a,int b) {
        if (a >= b) {
            return true;
        }
        else {
            return false;
        }
    }
}
