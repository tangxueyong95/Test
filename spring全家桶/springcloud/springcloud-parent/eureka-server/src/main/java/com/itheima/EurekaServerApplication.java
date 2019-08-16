package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/15 11:08
 * @Description: com.itheima
 ****/
@SpringBootApplication
//开启Eureka服务
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class,args);
    }

}
