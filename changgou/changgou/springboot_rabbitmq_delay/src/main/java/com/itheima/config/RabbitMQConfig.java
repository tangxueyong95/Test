package com.itheima.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima
 ****/
@Configuration
public class RabbitMQConfig {

    /****
     * 延时队列->过期队列   10000
     */
    @Bean
    public Queue delayMessageQueue(){
       return QueueBuilder.durable("delayMessageQueue")
                .withArgument("x-dead-letter-exchange","messageExchange")   //消息过期后，发送给指定交换机
                .withArgument("x-dead-letter-routing-key","messageQueue").build(); //按照路由规则将数据发给指定的队列
                //过期之后，转发规则配置
    }

    /***
     * 真正接收过期消息的队列
     */
    @Bean
    public Queue messageQueue(){
        return new Queue("messageQueue");
    }


    /***
     * 交换机配置
     */
    @Bean
    public Exchange messageExchange(){
        return new DirectExchange("messageExchange",true,false);
    }


    /****
     * 交换机绑定队列
     */
    @Bean
    public Binding messageBindingExchange(Queue messageQueue,Exchange messageExchange){
        return BindingBuilder.bind(messageQueue).to(messageExchange).with("messageQueue").noargs();
    }


}
