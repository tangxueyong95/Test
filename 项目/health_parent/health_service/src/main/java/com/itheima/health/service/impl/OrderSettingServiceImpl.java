package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:44
 * @Version V1.0
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    // 从excel中读取数据， 导入到数据库中
    @Override
    public void uploadAdd(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                // 1：使用当前预约时间，查询，判断当前预约时间下是否有数据
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                // 有数据，更新数据
                if (count > 0) {
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }
                // 没有数据，保存数据
                else {
                    // 保存数据
                    orderSettingDao.add(orderSetting);
                }

            }
        }

    }

    @Override
    public List<Map<String, Object>> findMonthListByMonthBetween(String date) {
        String beginDate = date + "-1";
        String endDate = date + "-31";
        // 传递的条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);
        List<OrderSetting> list = orderSettingDao.findMonthListByMonthBetween(params);
        // 组织返回响应的数据List<Map<String,Object>>
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("date", orderSetting.getOrderDate().getDate());
                map.put("number", orderSetting.getNumber());
                map.put("reservations", orderSetting.getReservations());
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public void edit(OrderSetting orderSetting) {
        if (orderSetting.getNumber() == 0) {
            orderSettingDao.delete(orderSetting);
        } else {
            // 1：使用当前预约时间，查询，判断当前预约时间下是否有数据
            long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            // 有数据，更新数据
            if (count > 0) {
                orderSettingDao.editNumberByOrderDate(orderSetting);
            }
            // 没有数据，保存数据
            else {
                // 保存数据
                orderSettingDao.add(orderSetting);
            }
        }
    }
}
