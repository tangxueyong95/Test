package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/17 12:05
 * @Description: com.java
 ****/
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer //开启配置中心服务
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class,args);
    }
}
