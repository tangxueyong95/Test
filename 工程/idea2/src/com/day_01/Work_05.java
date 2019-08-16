package com.day_01;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Work_05 {
    public static void main(String[] args) {
        Date date = new Date();
        String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        System.out.println(s);
    }
}
