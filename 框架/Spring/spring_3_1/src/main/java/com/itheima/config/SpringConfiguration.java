package com.itheima.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ClassName SpringConfiguration
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/12 12:33
 * @Version V1.0
 */
@Configuration //表示该类为配置类，相当于applicationContext.xml
@ComponentScan(value = {"com.itheima"})//包扫描
@EnableAspectJAutoProxy // 等同于：<aop:aspectj-autoproxy></aop:aspectj-autoproxy> //织入@Aspectj切面
public class SpringConfiguration {
}
