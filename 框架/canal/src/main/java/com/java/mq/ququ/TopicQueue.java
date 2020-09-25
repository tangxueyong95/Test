package com.java.mq.ququ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*****
 * @Author: www.itheima.com
 * @Description: com.java.mq.ququ
 ****/
@Configuration
public class TopicQueue {

    //队列的名字
    public static final String TOPIC_QUEUE_SPU="topic_queue_spu";
    //创建交换机
    public static final String TOPIC_EXCHANGE_SPU="topic_exchange_spu";

    /****
     * 创建队列Queue
     */
    @Bean
    public Queue topicSpuQueue(){
        return new Queue(TOPIC_QUEUE_SPU);
    }


    /***
     * 创建交换机
     * Topic
     */
    @Bean
    public Exchange toplicSpuExchange(){
        return new TopicExchange(TOPIC_EXCHANGE_SPU);
    }


    /***
     * 队列绑定交换机
     */
    @Bean
    public Binding topicBindingSpu(Queue topicSpuQueue,Exchange toplicSpuExchange){
        return BindingBuilder.bind(topicSpuQueue).to(toplicSpuExchange).with(TOPIC_QUEUE_SPU).noargs();
    }

}
