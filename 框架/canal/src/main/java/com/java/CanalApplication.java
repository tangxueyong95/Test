package com.java;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/*****
 * @Author: www.itheima.com
 * @Date: 2019/7/30 12:29
 * @Description: com.java
 ****/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableCanalClient      //开启Canal客户端
public class CanalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class,args);
    }
}
