package com.itheima.job;

import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

public class JobImg2 {

    @Autowired
    OrderSettingDao orderSettingDao;

    //定时清理预约设置数据
    public void deleteOrderSetting(){

        try {
            //获取当前时间的之前两个月的时间,将前两个月之前的所有数据删除,只保留近两个月的数据
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,-2);
            String dateString = DateUtils.parseDate2String(calendar.getTime()); //2019-5-11
            orderSettingDao.deleteOrderSetting(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
