package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/15 10:19
 * @Description: com.java
 ****/
@SpringBootApplication
@EnableDiscoveryClient  //开启客户端的发现功能,扫描到该注解后，会将该服务注册到Eureka中
public class UserProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProviderApplication.class,args);
    }

}
