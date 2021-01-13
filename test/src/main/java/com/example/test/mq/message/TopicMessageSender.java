package com.example.test.mq.message;

import com.alibaba.fastjson.JSON;
import com.example.test.entity.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
