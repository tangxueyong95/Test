package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderSettingDao {

    void add(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> findMonthListByMonthBetween(Map<String, Object> params);

    void delete(OrderSetting orderSetting);

    OrderSetting findOrderSettingByOrderDate(Date orderDate);

    void update(OrderSetting orderSetting);

    void deleteOrderSetting(String dateString);
}
