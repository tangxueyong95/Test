package com.changgou.mq.message;

import com.alibaba.fastjson.JSON;
import entity.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.mq.message
 ****/
@Component
public class TopicMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /***
     * 消息发送
     * @param message
     */
    public void sendMessage(Message message){
        rabbitTemplate.convertAndSend(message.getExechange(),message.getRoutekey(), JSON.toJSONString(message));
    }
}
