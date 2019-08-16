package com.itheima.test;

import com.itheima.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ClassName JdbcTemplateDemo01
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/15 9:21
 * @Version V1.0
 */
// 在学习spring之前，使用JdbcTemplate
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestAccount {

    @Autowired
    AccountService accountService;

    // 转账的案例（aaa取100元钱给bbb)
    @Test
    public void transfer() throws Exception{
        try {
            accountService.transfer("aaa","bbb",100f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
