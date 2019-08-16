package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 包名:com.itheima.utils
 * 作者:Leevi
 * 日期2019-05-12  10:12
 */
public class DateUtil {
    public static String getCurrentTime(){
        Date date = new Date();
        //将date转换成字符串，使用SimpleDateFormat进行转换
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
        return  dateFormat.format(date);
    }
    public static String getCurrentDate(){
        Date date = new Date();
        //将date转换成字符串，使用SimpleDateFormat进行转换
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  dateFormat.format(date);
    }
}
