package com;

import com.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test {
    static AccountService accountService;

    static{
        // 加载spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        accountService = ac.getBean("accountService", AccountService.class);
    }

    public static void main(String[] args) {
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
