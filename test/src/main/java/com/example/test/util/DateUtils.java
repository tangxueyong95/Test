package com.example.test.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Title: DateUtils
 * @Description:
 * @author: sunny.y
 * @version: 1.0
 */
public class DateUtils {

    /**
     * 功能描述:
     *
     * @param: 获取当前系统时间 yyyy-MM-dd HH:mm:ss
     * @return:
     */
    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(System.currentTimeMillis());
        return date;
    }


    /**
     * 功能描述:
     *
     * @param: date类 获取当前系统时间 yyyy-MM-dd HH:mm:ss
     * @return:
     */
    public static Date getCurrentDateToDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(System.currentTimeMillis());
        Date d = null;
        try {
            d = df.parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 增加时间单位：天
     *
     * @param day
     * @return
     */
    public static String getCurrentAddDay(int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, day);
        return sdf.format(cal.getTime());
    }

    /**
     * 增加时间单位：分钟
     *
     * @param minute
     * @return
     */
    public static String getCurrentAddMin(int minute) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, minute);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowDateString() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    /**
     * 把Date转为String
     *
     * @param date
     * @param format
     * @return
     */
    public static String getFormatTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 增加时间单位：天
     *
     * @param day
     * @return
     */
    public static Date addDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 增加时间单位：天
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 减去多少天
     *
     * @param date
     * @param day
     * @return
     */
    public static Date minusDay(Date date, int day) {
        return addDay(date, -day);
    }

    //获取当前年和月
    public static List<Integer> getYearAndMonth() {
        List<Integer> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        list.add(year);
        int month = cal.get(Calendar.MONTH) + 1;
        list.add(month);
        return list;
    }

    //设置年和月并返回
    public static String setYearAndMonth(int year, int month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 0);
        return sdf.format(cal.getTime());
    }

    public static String setDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf1.format(date);
    }

    public static String set6ByteDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf1.format(date);
    }

    public static int byte2ToUnsignedShort(byte[] bytes, int off) {
        int i = (bytes[off + 1] << 8 & 0xFFFF) | (bytes[off] & 0xFF);
        String s = String.valueOf(i);
        return Integer.parseUnsignedInt(s,16);
    }

    public static String getDate(Long time) {
        Date date = new Date(time*1000L);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf1.format(date);
        return format;
    }

    public static String setDate1(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf1.format(date);
    }

    public static String[] get6ByteDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
        Date date=new Date();
        String format = sdf.format(date);
        return format.split("-");
    }

    public static void main(String[] args) {
        String s="130613192629";
        String s1 = set6ByteDate(s);
        System.out.println(s1);
    }
}
