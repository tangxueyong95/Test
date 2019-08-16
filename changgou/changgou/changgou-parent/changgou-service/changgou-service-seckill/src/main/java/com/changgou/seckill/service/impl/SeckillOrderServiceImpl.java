package com.changgou.seckill.service.impl;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.dao.SeckillOrderMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillOrder;
import com.changgou.seckill.service.SeckillOrderService;
import com.changgou.seckill.task.MultiThreadingCreateOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.IdWorker;
import entity.SeckillStatus;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:SeckillOrder业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    //用于异步下单操作
    @Autowired
    private MultiThreadingCreateOrder multiThreadingCreateOrder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    /***
     * 删除订单信息
     * @param username
     */
    @Override
    public void delete(String username) {
        //3.库存回滚
        SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundHashOps("UserQueueStatus").get(username);
        //最后一个处理
        //->判断时间是否过期，如果过期，MySQL中的销量递减1
        //如果没有过期，定时任务会自动回滚

        //没有过期处理
        //获取Redis中的商品信息
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps("SeckillGoods_"+seckillStatus.getTime()).get(seckillStatus.getGoodsId());
        if(seckillGoods!=null){
            //修改库存对象
            //将商品的个数存入到队列中o o o o o o o
            redisTemplate.boundListOps("SeckillGoodsCountList_"+seckillGoods.getId()).leftPush(seckillGoods.getId());
            //商品个数计数器
            Long counts = redisTemplate.boundHashOps("SeckillGoodsCount").increment(seckillGoods.getId(), 1);
            //往Redis中添加一个数量队列
            seckillGoods.setStockCount(counts.intValue());
        }

        //1.删除Redis缓存订单
        redisTemplate.boundHashOps("SeckillOrder").delete(username);
        //2.用户抢单信息
        redisTemplate.boundHashOps("UserQueueCount").delete(username);
        redisTemplate.boundHashOps("UserQueueStatus").delete(username);
    }

    /****
     * 修改订单
     * @param username:获取订单
     */
    @Override
    public void updateStatus(String username,String transactionid) {
        //获取订单信息->修改状态 ->支付【交易流水号】
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.boundHashOps("SeckillOrder").get(username);
        if(seckillOrder!=null){
            seckillOrder.setPayTime(new Date());    //支付时间
            seckillOrder.setStatus("1");    //已支付
            seckillOrder.setTransactionId(transactionid); //交易流水号
            //Redis->MySQL
            seckillOrderMapper.insertSelective(seckillOrder);
            //清理缓存  1)订单缓存
            redisTemplate.boundHashOps("SeckillOrder").delete(username);

            //Redis缓存清理掉  2)用户抢单缓存（标识）  3)用户排队标示
            redisTemplate.boundHashOps("UserQueueCount").delete(username);
            redisTemplate.boundHashOps("UserQueueStatus").delete(username);
        }
    }

    /****
     * 查询用户排队装填
     * @param username
     * @return
     */
    @Override
    public SeckillStatus queryStatus(String username) {
        return (SeckillStatus) redisTemplate.boundHashOps("UserQueueStatus").get(username);
    }

    /****
     * 下订单
     * 1)找到商品->time,id
     * 2)创建订单->一个用户只允许有一个未支付秒杀订单,可以将订单存入到Redis，将用户的名字作为key
     * 3)订单存入到Redis
     * 4)库存递减->库存递减
     */
    @Override
    public Boolean add(Long id, String time, String username) {
        //给当前用户添加一个排队标识(抢单标识)
        Long count = redisTemplate.boundHashOps("UserQueueCount").increment(username, 1);
        if(count!=1){
            //100：表示有重复抢单
            throw new RuntimeException("100");
        }

        //排队->将信息封装到SeckillStatus中
        SeckillStatus seckillStatus = new SeckillStatus(username, new Date(), 1,id,time);
        redisTemplate.boundListOps("SeckillOrderQueue").leftPush(seckillStatus);

        //把用户排队状态存入到Redis-> Hash  key:username  value:seckillStatus
        redisTemplate.boundHashOps("UserQueueStatus").put(username,seckillStatus);

        //异步执行
        multiThreadingCreateOrder.createOrder();

        return true;
    }
}
