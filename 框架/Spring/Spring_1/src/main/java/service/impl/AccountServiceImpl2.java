package service.impl;



import service.AccountService;

import java.util.Date;

public class AccountServiceImpl2 implements AccountService {

    private String name;
    private Integer age;
    private Date birthday;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void saveAccount() {
        System.out.println("执行AccountServiceImpl中的saveAccount方法！ name:"+name+"        age:"+age+"      birthday:"+birthday);
    }
}
