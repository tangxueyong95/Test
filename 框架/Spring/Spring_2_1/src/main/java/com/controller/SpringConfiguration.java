package com.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration //表示该类为配置类，相当于applicationContext.xml
@ComponentScan(value = "com")//包扫描
//@Import(value= JdbcConfig.class)//导入其他的配置类
//@PropertySource("classpath:jdbcConfig.properties")//指定properties文件的位置
public class SpringConfiguration {

}
