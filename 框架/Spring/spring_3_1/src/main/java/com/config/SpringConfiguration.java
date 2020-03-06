package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration //表示该类为配置类，相当于applicationContext.xml
@ComponentScan(value = {"com"})//包扫描
@EnableAspectJAutoProxy // 等同于：<aop:aspectj-autoproxy></aop:aspectj-autoproxy> //织入@Aspectj切面
public class SpringConfiguration {
}
