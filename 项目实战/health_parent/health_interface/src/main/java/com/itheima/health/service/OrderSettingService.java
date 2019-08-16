package com.itheima.health.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    void uploadAdd(List<OrderSetting> orderSettingList);

    List<Map<String,Object>> findMonthListByMonthBetween(String date);

    void edit(OrderSetting orderSetting);

}
