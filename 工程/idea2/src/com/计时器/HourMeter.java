package com.计时器;

import java.awt.*;

public class HourMeter {
    public static void main(String[] args) {
        hourMeterFrame();
        hourMeter();
    }

    //计时
    public static void hourMeter() {
        int i = 0;
        while (i < 10) {
            System.out.println(10 - i);
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //窗体
    public static void hourMeterFrame() {
        Frame frame = new Frame();
        frame.addNotify();
    }
}
