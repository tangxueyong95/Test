package com.changgou.seckill.task;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.dao.SeckillOrderMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillOrder;
import entity.IdWorker;
import entity.SeckillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.seckill.task
 * 异步(多线程)执行的类，主要用于创建订单
 ****/
@Component
public class MultiThreadingCreateOrder {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    /****
     * 异步执行的方法->run
     */
    @Async  //异步执行该方法
    public void createOrder(){
        try {
           //从队列中获取用户排队信息
            SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps("SeckillOrderQueue").rightPop();

            //获取到了排队信息，则下单
            if(seckillStatus!=null){
                //定义username、id、time  先固定写
                Long id = seckillStatus.getGoodsId();
                String username = seckillStatus.getUsername();
                String time = seckillStatus.getTime();

                //获取商品
                Object sgoods = redisTemplate.boundListOps("SeckillGoodsCountList_" + seckillStatus.getGoodsId()).rightPop();

                //商品没有了
                if(sgoods==null){
                    //更新状态，返回
                    //seckillStatus.setStatus(4);  //秒杀失败
                    redisTemplate.boundHashOps("UserQueueCount").delete(seckillStatus.getUsername());//排队标识
                    redisTemplate.boundHashOps("UserQueueStatus").delete(seckillStatus.getUsername());//抢单状态
                    return;
                }

                //1)找到商品->time,id
                SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps("SeckillGoods_" + time).get(id);

                if(seckillGoods!=null){
                    //2)创建订单->一个用户只允许有一个未支付秒杀订单,可以将订单存入到Redis，将用户的名字作为key
                    SeckillOrder seckillOrder = new SeckillOrder();
                    seckillOrder.setId(idWorker.nextId());
                    seckillOrder.setSeckillId(id);
                    seckillOrder.setMoney(seckillGoods.getCostPrice());
                    seckillOrder.setUserId(username);
                    seckillOrder.setCreateTime(new Date());
                    seckillOrder.setStatus("0");
                    //3)订单存入到Redis
                    redisTemplate.boundHashOps("SeckillOrder").put(username,seckillOrder);

                    System.out.println("准备抢单"+Thread.currentThread().getId()+"---数量："+seckillGoods.getStockCount());
                    Thread.sleep(10000);

                    //4)库存递减->库存递减
                    Long count = redisTemplate.boundHashOps("SeckillGoodsCount").increment(id, -1);

                    //用计数器的数据
                    seckillGoods.setStockCount(count.intValue());
                    //seckillGoods.setStockCount(seckillGoods.getStockCount()-1);

                    //System.out.println("递减后的数量"+Thread.currentThread().getId()+"---数量："+seckillGoods.getStockCount());

                    //如果商品库存个数<=0，将数据同步到MySQL中，并且删除Redis缓存
                    //if(seckillGoods.getStockCount()<=0){
                    if(count<=0){
                        //同步数据到MySQL
                        seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
                        //删除Redis缓存
                        redisTemplate.boundHashOps("SeckillGoods_" + time).delete(id);
                    }else{
                        //修改Redis库存
                        redisTemplate.boundHashOps("SeckillGoods_" + time).put(id,seckillGoods);
                    }

                    //更新用户排队状态
                    seckillStatus.setOrderId(seckillOrder.getId());
                    seckillStatus.setMoney(Float.valueOf(seckillGoods.getCostPrice())); //金额
                    seckillStatus.setStatus(2); //等待支付(下单成功)
                    redisTemplate.boundHashOps("UserQueueStatus").put(seckillStatus.getUsername(),seckillStatus);
                    System.out.println(Thread.currentThread().getId()+"  下单完成！");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
