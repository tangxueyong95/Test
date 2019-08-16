package com.day8;

import java.util.Scanner;

public class _05{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        int n =sc.nextInt();
        method(str,n);
    }

    public static void method(String str,int n) {
        if(str.length()<=n){
            System.out.println(str+str);
        }else{
            System.out.println(str.substring(0,n)+str.substring(str.length()-n));
        }
    }
}
