package com.itheima;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima
 ****/
@SpringBootApplication
@EnableRabbit
public class RabbitMQDelayMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQDelayMessageApplication.class,args);
    }
}
