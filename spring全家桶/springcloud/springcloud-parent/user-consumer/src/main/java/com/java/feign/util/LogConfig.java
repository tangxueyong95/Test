package com.java.feign.util;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/17 10:11
 * @Description: com.java.feign.util
 * 定义日志级别
 ****/
@Configuration
public class LogConfig {

    /***
     * 指定日志级别
     * @return
     */
    @Bean
    public Logger.Level feilog(){
        return  Logger.Level.FULL;
    }

}
