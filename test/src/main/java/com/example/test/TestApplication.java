package com.example.test;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@MapperScan(basePackages = "com.example.test.dao")
@EnableCanalClient      //开启Canal客户端
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    /**
     * redis序列化
     */
    /*@Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }*/
}
