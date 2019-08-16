package com.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.Date;

//@Configuration
//@ComponentScan
//@Component
@Repository
public class Date1 {
    @Bean(name = "date") //将当前方法的返回值作为bean对象存入spring的ioc容器中
    public Date date() {
        return new Date();
    }
}
