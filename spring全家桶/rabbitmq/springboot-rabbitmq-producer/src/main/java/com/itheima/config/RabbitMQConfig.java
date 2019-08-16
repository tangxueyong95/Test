package com.itheima.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*****
 * @Author: shenzhen-itheima
 * @Date: 2019/7/24 12:11
 * @Description: com.itheima.config
 ****/
@Configuration
public class RabbitMQConfig {


    /****
     * 创建队列 Queue1
     */
    @Bean
    public Queue topicQueue1(){
        return new Queue("topic_queue_springboot1");
    }



    /****
     * 创建队列 Queue2
     */
    @Bean
    public Queue topicQueue2(){
        return new Queue("topic_queue_springboot2");
    }

    /***
     * 创建交换机->可以指定交换机类型 Topic
     */
    @Bean
    public Exchange topicExchange(){
        return new TopicExchange("topic_exchange_springboot");
    }

    /****
     * 交换机绑定队列 ->可以指定交换机绑定类型
     */
    @Bean
    public Binding topicQueueBind1(Queue topicQueue1,Exchange topicExchange){
        return BindingBuilder.bind(topicQueue1).to(topicExchange).with("item.#").noargs();
    }

    /****
     * 交换机绑定队列 ->可以指定交换机绑定类型
     */
    @Bean
    public Binding topicQueueBind3(Queue topicQueue2,Exchange topicExchange){
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with("item.#").noargs();
    }

    /****
     * 交换机绑定队列 ->可以指定交换机绑定类型
     */
    @Bean
    public Binding topicQueueBind2(Queue topicQueue2,Exchange topicExchange){
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with("user.#").noargs();
    }


}
