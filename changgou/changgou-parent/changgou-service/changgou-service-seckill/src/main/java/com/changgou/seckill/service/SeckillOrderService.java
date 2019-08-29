package com.changgou.seckill.service;

import com.changgou.seckill.pojo.SeckillOrder;
import com.github.pagehelper.PageInfo;
import entity.SeckillStatus;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:SeckillOrder业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface SeckillOrderService {

    /***
     * 删除订单信息
     * @param username
     */
    void delete(String username);

    /****
     * 修改订单状态
     * @param username:获取订单
     */
    void updateStatus(String username,String transactionid);

    /****
     * 查询排队状态
     */
    SeckillStatus queryStatus(String username);

    /****
     * 下订单
     * 1)找到商品->time,id
     * 2)创建订单->一个用户只允许有一个未支付秒杀订单,可以将订单存入到Redis，将用户的名字作为key
     * 3)订单存入到Redis
     * 4)库存递减->库存递减
     */
    Boolean add(Long id,String time,String username);

}
