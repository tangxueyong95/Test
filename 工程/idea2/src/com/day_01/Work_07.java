package com.day_01;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Work_07 {
    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar calendar = Calendar.getInstance();
        String str = "2018年2月14日";
        try {
            Date date = dateFormat.parse(str);
            calendar.setTime(date);
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    System.out.println("星期日");
                    break;
                case 2:
                    System.out.println("星期一");
                    break;
                case 3:
                    System.out.println("星期二");
                    break;
                case 4:
                    System.out.println("星期三");
                    break;
                case 5:
                    System.out.println("星期四");
                    break;
                case 6:
                    System.out.println("星期五");
                    break;
                case 7:
                    System.out.println("星期六");
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
