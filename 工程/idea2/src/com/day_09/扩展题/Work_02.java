package com.day_09.扩展题;

import java.io.FileOutputStream;
import java.util.Scanner;

public class Work_02 {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("F:\\我的代码\\idea2\\src\\stu.txt");
        Scanner sc =new Scanner(System.in);
        System.out.println("输入end退出");
        String str = "";
        while (true){
            System.out.println("输入学号:");
            int a = sc.nextInt();
            System.out.println("输入姓名:");
            String s = sc.next();
            str =a+"-"+s;
            break;
        }
        fos.write(str.getBytes());
        fos.close();
    }
}
