package com.service.impl;


import com.dao.AccountDao;
import com.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;


@Service(value = "accountService")
@Scope(value = "singleton") // prototype：多例；singleton：单例
public class AccountServiceImpl implements AccountService {

    /**
     * @Autowired（spring提供的）
     * 放置到属性上，放置到set方法上
     * 默认按照类型注入，如果找到对应类型，就将这个类型进行注入
    如果没有找到对应类型，就会按照名称注入，将对象进行注入
    如果名称也没有找到，就会抛出异常
     */

    /**
     *@Qualifier,用来配合@Autowired使用
     让spring的@Autowired只能按照名称注入，因为类型在spring容器会很多，回退到只按名称注入，查找更加精确
     */
//    @Autowired
//    @Qualifier(value = "accountDao2")
//    AccountDao accountDao3;

//    @Autowired
//    @Qualifier(value = "accountDao2")
//    public void setAccountDao(AccountDao accountDao) {
//        this.accountDao3 = accountDao;
//    }


    /**
     * @Resource（jdk提供的）（了解）
     * 放置到属性上，放置到set方法上
     * 默认按照名称注入，如果找到对应名称，就将这个对象进行注入
    如果没有找到对应名称，就会按照类型注入，将对象进行注入
    如果名称和类型都没有找到，就会抛出异常
       @Resource注解如果行精确查找（只能按照名称）
     @Resource(name = "accountDao2")：表示查找名称为accountDao2
     */
//    @Resource(name = "accountDao2")
//    AccountDao accountDao;

    @Autowired // 对象的注入
            AccountDao accountDao;

    // @Value从属性文件中读取信息@Value(value="${name}") ,一会再说
    @Value(value = "张三") // 基本数据类型的注入
    private String name;
    @Value(value = "22")
    private Integer age;
    @Autowired
    private Date date;

    public void saveAccount() {
        System.out.println("执行AccountServiceImpl中的saveAccount方法！name:"+name+"          age:"+age+"        DATE:"+ date);
        accountDao.save();
    }

    @PostConstruct
    public void init(){
        System.out.println("初始化...");
    }

    @PreDestroy
    public void destory(){
        System.out.println("销毁...");
    }
}
