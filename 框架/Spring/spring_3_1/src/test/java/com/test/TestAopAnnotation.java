package com.test;

import com.config.SpringConfiguration;
import com.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class TestAopAnnotation {

    // 注入Service
    @Autowired
    AccountService accountService;

    // 测试
    @Test
    public void testAopService(){
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
