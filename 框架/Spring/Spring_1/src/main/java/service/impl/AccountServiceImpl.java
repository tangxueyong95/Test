package service.impl;



import service.AccountService;

import java.util.Date;

/**
 * @ClassName AccountServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/9 9:15
 * @Version V1.0
 */
public class AccountServiceImpl implements AccountService {

    private String name;
    private Integer age;
    private Date birthday;

    public AccountServiceImpl() {
    }

    public AccountServiceImpl(String name, Integer age, Date birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public void saveAccount() {
        System.out.println("执行AccountServiceImpl中的saveAccount方法！ name:"+name+"        age:"+age+"      birthday:"+birthday);
    }
}
