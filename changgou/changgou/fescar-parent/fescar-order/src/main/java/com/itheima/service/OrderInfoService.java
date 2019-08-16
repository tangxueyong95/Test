package com.itheima.service;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima.service
 ****/
public interface OrderInfoService {

    /***
     * 添加订单
     * @param username
     * @param id
     * @param count
     */
    void add(String username, int id, int count);
}
