package com.controller;

import com.service.AccountService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Client {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        AccountService accountService = ac.getBean("accountService", AccountService.class);
        accountService.saveAccount();
        System.out.println(accountService);
        ac.close();
    }
}
