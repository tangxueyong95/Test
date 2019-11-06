package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 9:06
 * @Description: com.java
 *  1.在当前类上添加@SpringBootApplication注解
 *      1)当前类会成为配置类[@SpringBootConfiguration]
 *      2)@ComponentScan:扫描指定类上的注解(默认扫描当前引导类所在的包以及子包下的所有的注解)
 *      3)@EnableAutoConfiguration:
 *           根据当前用户引入的jar包，猜测用户要做的配置，并自动完成配置
 *  2.在主方法中指定当前类为引导类
 ****/
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        /***
         * 1.在主方法中指定当前类为引导类
         *   第1个参数：指定SpringBoot的引导类
         *              等会创建的Bean对象只要在该类所在的包下或者子包下，都会被扫描
         * 2.第2个参数是args参数，用户执行java命令行可以输入的额外参数，一般不管
         */
        SpringApplication.run(DemoApplication.class,args);
    }
}
