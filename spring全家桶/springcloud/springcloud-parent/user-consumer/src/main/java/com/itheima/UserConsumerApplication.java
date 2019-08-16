package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/15 10:25
 * @Description: com.itheima
 ****/
@SpringBootApplication
@EnableDiscoveryClient  //开启Eureka客户端的发现功能
@EnableCircuitBreaker   //开启熔断限流机制
@EnableFeignClients     //开启Feign客户端
public class UserConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserConsumerApplication.class,args);
    }

    /***
     * 创建RestTemplate对象，并且交给SpringIOC容器管理
     */
    @Bean
    @LoadBalanced   //开启负载均衡，默认是轮询负载均衡策略
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
