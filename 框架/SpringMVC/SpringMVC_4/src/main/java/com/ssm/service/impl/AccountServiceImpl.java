package com.ssm.service.impl;


import com.ssm.dao.AccountDao;
import com.ssm.domain.Account;
import com.ssm.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAllAccount() {
        System.out.println("调用Service类的findAllAccount方法！");
        List<Account> list = accountDao.findAll();
        return list;
    }

    @Override
    public int saveAccount(Account account) {
        System.out.println("调用Service类的saveAccount方法！");
        int rows = accountDao.save(account);
        //int i=10/0;
        return rows;
    }
}
