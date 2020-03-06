package com.service.impl;

import com.service.AccountService;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    public void saveAccount() {
        System.out.println("执行【save】方法！");
    }

    public void updateAccount(int i) {
        System.out.println("执行【update】方法！传递的参数是："+i);
        // 目标对象的切入点方法抛出异常，此时执行异常通知
        int ii=10/0;
    }

    public int deleteAccount() {
        System.out.println("执行【delete】方法！");
        return 10;
    }
}
