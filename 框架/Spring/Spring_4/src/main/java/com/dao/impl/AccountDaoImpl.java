package com.dao.impl;

import com.dao.AccountDao;
import com.domain.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {


    // 主键id查询
    public Account findAccountById(Integer id) {
        Account account = this.getJdbcTemplate().queryForObject("select * from account where id=?", new BeanPropertyRowMapper<Account>(Account.class), id);
        return account;
    }

    // 按照名称查询（返回的是一个唯一的结果）
    public Account findAccountByName(String name) {
        Account account = null;
        List<Account> list = this.getJdbcTemplate().query("select * from account where name=?", new BeanPropertyRowMapper<Account>(Account.class), name);
        if(list.isEmpty()){
            System.out.println("没有查到结果");
        }
        // 结果集多条
        else if(list.size()>1){
            throw new RuntimeException("查询的结果只能返回1条数据");
        }
        else{
            // 返回一个值
            account = list.get(0);
        }
        return account;
    }


    public void updatAccount(Account account) {
        this.getJdbcTemplate().update("update account set name=?,money=? where id=?",account.getName(),account.getMoney(),account.getId());
    }
}
