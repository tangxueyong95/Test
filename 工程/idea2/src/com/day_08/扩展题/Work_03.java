package com.day_08.扩展题;

import java.util.Scanner;

public class Work_03 {

    public static void main(String[] args) {
        while (true){
            try {
                int s = new Scanner(System.in).nextInt();
                int i = 1;
                System.out.println(eatPeach(s,i));
                break;
            } catch (Exception e) {
                System.out.println("请输入整数!");
            }
        }
    }

    public static String eatPeach(int a,int i) {
        if(a==0){
            return "猴子共摘了"+i+"个桃子";
        }else {
            return eatPeach(a-1,2*(i+1));
        }
    }
}
