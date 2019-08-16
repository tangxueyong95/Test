package com.itheima.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/*****
 * @Author: shenzhen-itheima
 * @Date: 2019/7/24 12:26
 * @Description: com.itheima.listener
 ****/
@Component
public class MessageListener {

    /***
     * 监听topic_queue_springboot2
     * @param msg
     */
    @RabbitListener(queues = {"topic_queue_springboot2"})
    public void getMsg(String msg){
        System.out.println("topic_queue_springboot2:"+msg);
    }



    /***
     * 监听topic_queue_springboot1
     * @param msg
     */
    @RabbitListener(queues = {"topic_queue_springboot1"})
    public void getMsg1(String msg){
        System.out.println("topic_queue_springboot1:"+msg);
    }


}
