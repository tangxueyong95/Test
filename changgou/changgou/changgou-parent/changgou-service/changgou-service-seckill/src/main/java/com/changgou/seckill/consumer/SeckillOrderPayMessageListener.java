package com.changgou.seckill.consumer;

import com.alibaba.fastjson.JSON;
import com.changgou.seckill.service.SeckillOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.seckill.consumer
 ****/
@Component
@RabbitListener(queues = {"${mq.pay.queue.seckillorder}"})
public class SeckillOrderPayMessageListener {

    @Autowired
    private SeckillOrderService seckillOrderService;

    /***
     * 秒杀支付消息监听
     * @param msg
     */
    @RabbitHandler
    public void seckillOrderMessage(String msg){
        //消息数据读取
        Map<String,String> message = JSON.parseObject(msg,Map.class);

        //attach 获取用户名字->令牌[不行]

        //通信成功
        if(message.get("return_code").equalsIgnoreCase("success")){
            //获取用户名字
            Map<String,String> attachMap =JSON.parseObject(message.get("attach"),Map.class);
            String username = attachMap.get("username");
            //如果name为空，则跳出
            if(StringUtils.isEmpty(username)){
                return;
            }

            //业务成功{如果支付成功}
            if(message.get("result_code").equalsIgnoreCase("success")){
                //修改订单状态
                seckillOrderService.updateStatus(username,message.get("transaction_id"));
            }else{
                //支付失败(做日志记录)
                //删除订单
                seckillOrderService.delete(username);
            }
        }
    }
}
