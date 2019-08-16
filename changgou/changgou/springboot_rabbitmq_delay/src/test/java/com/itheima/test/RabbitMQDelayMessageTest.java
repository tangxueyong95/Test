package com.itheima.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima.test
 ****/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQDelayMessageTest {


    //RabbitTemplate发消息
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /******
     * 消息发送
     */
    @Test
    public void testSendMessage() throws IOException {
        rabbitTemplate.convertAndSend("delayMessageQueue", (Object) "hello!", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("10000");
                return message;
            }
        });

        System.in.read();
    }


}
