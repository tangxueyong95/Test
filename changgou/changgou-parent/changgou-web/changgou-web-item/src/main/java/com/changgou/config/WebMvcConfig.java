package com.changgou.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.config
 ****/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /*
    * <mvc:resources   mapping   locaiton />
    * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //所有以/items/**的请求，都直接到classpath:/templates/items/中找文件
        registry.addResourceHandler("/items/**").addResourceLocations("classpath:/templates/items/");
    }
}
