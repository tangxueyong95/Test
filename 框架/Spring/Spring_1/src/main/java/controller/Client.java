package controller;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.AccountService;

/**
 * @ClassName Client
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/9 9:16
 * @Version V1.0
 */
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
