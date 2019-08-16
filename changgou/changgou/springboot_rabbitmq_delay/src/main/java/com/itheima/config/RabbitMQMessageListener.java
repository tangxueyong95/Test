package com.itheima.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima.config
 ****/
@Configuration
@RabbitListener(queues = {"messageQueue"})
public class RabbitMQMessageListener {

    /***
     * 消息监听
     */
    @RabbitHandler
    public void getMessage(String msg){
        System.out.println("监听到的消息是："+msg);
    }
}
