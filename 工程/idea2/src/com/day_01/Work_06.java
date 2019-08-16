package com.day_01;

import java.text.*;
import java.util.Date;

public class Work_06 {
    public static void main(String[] args) {
        DateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String str = "2018-03-04";
        try {
            Date date = s.parse(str);
            DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            String format = dateFormat.format(date);
            System.out.println(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
