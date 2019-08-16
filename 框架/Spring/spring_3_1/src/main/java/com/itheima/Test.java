package com.itheima;

import com.itheima.config.SpringConfiguration;
import com.itheima.service.AccountService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Test {
    static AccountService accountService;

    public static void findAll() {
        // 加载spring容器
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        accountService = ac.getBean("accountService", AccountService.class);
    }


    public static void main(String[] args) {
        findAll();
        accountService.saveAccount();
        try {
            accountService.updateAccount(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = accountService.deleteAccount();
        System.out.println(i);
    }
}
