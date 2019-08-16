package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou
 ****/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //禁止加载数据库装配
@EnableEurekaClient
@EnableElasticsearchRepositories(basePackages = {"com.changgou.search.dao"})//开启Elasticsearch，并且指定ES的DAO包扫描
@EnableFeignClients(basePackages = {"com.changgou.goods.feign"})   //指定Feign接口包
public class SearchApplication {

    public static void main(String[] args) {
        /**
         * Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错
         * 解决netty冲突后初始化client时还会抛出异常
         * availableProcessors is already set to [12], rejecting [12]
         ***/
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SearchApplication.class,args);
    }
}
