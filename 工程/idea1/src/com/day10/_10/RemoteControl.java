package com.day10._10;

import java.util.Scanner;

public class RemoteControl {
    void controlTV(TV tv){
        System.out.println("遥控器控制电视");
        System.out.println("请输入节目名称：");
        String str;
        str = new Scanner(System.in).next();
        tv.paly(str);
    }
}
