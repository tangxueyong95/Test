package com.itheima.health.dao;

import com.itheima.health.pojo.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderDao {

    List<Order> findOrderListByCondition(Order order);

    void add(Order order);

    Map<String, Object> findById(Integer id);

    List<Map<String, Object>> getSetmealReport();

    // 根据当前时间，查询当前时间预约的订单数量
    Integer findOrderCountByDate(String today);

    Integer findOrderCountBetweenDate(Map<String, Object> weekMap);

    // 根据当前时间，查询当前时间预约已经到诊的订单数量
    Integer findVisitsCountByDate(String today);

    Integer findVisitsCountAfterDate(String thisWeekMonday);

    List<Map<String, Object>> findHotSetmeal();
}
