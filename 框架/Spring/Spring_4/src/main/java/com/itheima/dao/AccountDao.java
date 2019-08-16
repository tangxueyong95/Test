package com.itheima.dao;

import com.itheima.domain.Account;

public interface AccountDao {
    // 主键查询
    public Account findAccountById(Integer id);

    // 名称查询（用）
    public Account findAccountByName(String name);

    // 更新（用）
    public void updatAccount(Account account);


}
