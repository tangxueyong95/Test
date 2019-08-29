package com.changgou.order.message;

import com.alibaba.fastjson.JSON;
import com.changgou.order.pojo.OrderLog;
import com.changgou.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.order.message
 ****/
@Component
@RabbitListener(queues = {"${mq.pay.queue.order}"})
public class OrderMessageListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderService orderService;

    /***
     * 接收消息
     */
    @RabbitHandler
    public void consumeMessage(String msg){
        //将数据转成Map
        Map<String,String> result = JSON.parseObject(msg,Map.class);

        //return_code=SUCCESS
        String return_code = result.get("return_code");
        //业务结果
        String result_code = result.get("result_code");

        //业务结果 result_code=SUCCESS/FAIL，修改订单状态
        if(return_code.equalsIgnoreCase("success") ){
            //获取支付日志ID
            String outtradeno = result.get("out_trade_no");

            //获取支付日志信息
            OrderLog orderLog = (OrderLog) redisTemplate.boundHashOps("OrderLog").get(outtradeno);

            //业务结果
            if(result_code.equalsIgnoreCase("success")){
                if(orderLog!=null){
                    //修改订单状态  out_trade_no
                    orderService.updateStatus(orderLog.getOrderId(), "",result.get("transaction_id"), orderLog);
                }
            }else{
                //订单删除
                orderService.deleteOrder(orderLog);
            }
        }

    }

}
