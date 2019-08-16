package com.itheima.travel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 包名:com.itheima.travel.utils
 * 作者:Leevi
 * 日期2019-05-31  11:00
 */
public class DateUtil {
    /**
     * 获取当前的日期
     * @return
     */
    public static String getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
