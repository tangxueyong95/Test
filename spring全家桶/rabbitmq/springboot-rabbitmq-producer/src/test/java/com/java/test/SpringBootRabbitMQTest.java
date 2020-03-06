package com.java.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/24 12:19
 * @Description: com.java.test
 * 消息发送测试
 ****/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRabbitMQTest {

    /***
     * RabbitTemplate：RabbitMQ的消息发送模板对象
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /***
     * 消息发送
     */
    @Test
    public void testSendMessage(){
        /***
         * 1:交换机
         * 2:Routingkey
         * 3:Message消息
         */
        rabbitTemplate.convertAndSend("topic_exchange_springboot","item.add","都能收到消息Item!");
        rabbitTemplate.convertAndSend("topic_exchange_springboot","user.add","Queue2可以收到，跟Queue1没关系!");

        //topic_queue_springboot1:1条
        //topic_queue_springboot2:2条
    }

}
