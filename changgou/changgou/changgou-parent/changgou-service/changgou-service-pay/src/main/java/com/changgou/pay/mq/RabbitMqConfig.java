package com.changgou.pay.mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.pay.mq
 * 队列创建与交换机绑定
 ****/
@Configuration
public class RabbitMqConfig {

    /***
     * 用于加载properties配置文件内容
     */
    @Autowired
    private Environment env;

    /***
     * 创建队列
     * @return
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(env.getProperty("mq.pay.queue.order"));
    }

    /***
     * 交换机创建
     * @return
     */
    @Bean
    public Exchange orderExchange(){
        return new DirectExchange(env.getProperty("mq.pay.exchange.order"));
    }

    /***
     * 绑定操作
     * @return
     */
    @Bean
    public Binding orderQueueBinding(Queue orderQueue,Exchange orderExchange){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(env.getProperty("mq.pay.routing.key")).noargs();
    }






    /***
     * 创建队列
     * @return
     */
    @Bean
    public Queue orderSeckillQueue() {
        return new Queue(env.getProperty("mq.pay.queue.seckillorder"));
    }

    /***
     * 交换机创建
     * @return
     */
    @Bean
    public Exchange orderSeckillExchange(){
        return new DirectExchange(env.getProperty("mq.pay.exchange.seckillorder"));
    }

    /***
     * 绑定操作
     * @return
     */
    @Bean
    public Binding orderSeckillQueueBinding(Queue orderSeckillQueue,Exchange orderSeckillExchange){
        return BindingBuilder.bind(orderSeckillQueue).to(orderSeckillExchange).with(env.getProperty("mq.pay.routing.seckillkey")).noargs();
    }

}
