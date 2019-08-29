package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;

import java.util.List;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.order.service
 ****/
public interface CartService {

    /***
     * 购物车集合
     * List<OrderItem>
     */
    List<OrderItem> list(String username);


    /****
     * 加入购物车
     * @param id :商品ID
     * @param num :购买数量
     * @param username :购买的用户的用户名
     */
    void add(Long id,Integer num,String username);
}
