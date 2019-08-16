package com.itheima.test;

import com.itheima.health.utils.DateUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @ClassName TestDate
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/7/7 18:10
 * @Version V1.0
 */
public class TestDate {

    @Test
    public void testDate(){
        Calendar calendar = Calendar.getInstance();
        // 获取当前时间之前的前12月
        calendar.add(Calendar.MONTH,-12);
        // 从2018-8   到 2019-7 每个月份
        List<String> datelist = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1); // +1  2018-8
            try {
                datelist.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(datelist);
    }

    @Test
    public void testDate2() throws Exception {
        // 当前时间
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        // 本周的第一天
        String weekFirstDay = DateUtils.parseDate2String(DateUtils.getThisWeekMonday(DateUtils.getToday()));
        // 本周的最后一天
        String weekLastDay =  DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
        // 本月的第一天
        String monthFirstDay = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        // 本月的最后一天
        String monthLastDay = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());


        System.out.println(today);
        System.out.println(weekFirstDay+"       "+weekLastDay);
        System.out.println(monthFirstDay+"        "+monthLastDay);
    }
}
