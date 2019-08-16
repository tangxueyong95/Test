package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima
 * prefix="classpath:templates"
 * suffix=".html"
 ****/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThymeleafApplication.class,args);
    }
}
