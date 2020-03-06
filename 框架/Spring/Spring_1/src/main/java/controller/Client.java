package controller;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.AccountService;

// 模拟Controller
public class Client {

    // 构造方法测试
    @Test
    public void main() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        AccountService accountService = (AccountService)ac.getBean("accountService");
        accountService.saveAccount();
    }

    // set方法测试
    @Test
    public void main2() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        AccountService accountService = (AccountService)ac.getBean("accountService2");
        accountService.saveAccount();
    }

    // 集合测试
    @Test
    public void main3() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        AccountService accountService = (AccountService)ac.getBean("accountService3");
        accountService.saveAccount();
    }

}
