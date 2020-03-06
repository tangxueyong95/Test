package controller;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.AccountService;

public class Client {

    // 使用工厂加载spring容器
//    public static void main(String[] args) {
//        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//        AccountService accountService = ac.getBean("accountService", AccountService.class);
//        accountService.saveAccount();
//    }

    // 测试单例和多例
//    public static void main(String[] args) {
//        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//        AccountService accountService = ac.getBean("accountService", AccountService.class);
//        System.out.println(accountService);
//        AccountService accountService2 = ac.getBean("accountService", AccountService.class);
//        System.out.println(accountService2);
//        System.out.println(accountService == accountService2);
//    }

    // 测试生命周期
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        AccountService accountService = ac.getBean("accountService", AccountService.class);
        accountService.saveAccount();
        System.out.println(accountService);
        ac.close();
    }
}
