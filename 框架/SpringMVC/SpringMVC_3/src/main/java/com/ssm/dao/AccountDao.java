package com.ssm.dao;


import com.ssm.domain.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 因为spring管理了所有的Dao接口，@Repository也可以不加
public interface AccountDao {

    // 查询所有
    @Select(value = "select * from account")
    public List<Account> findAll();
    // 保存
    @Insert(value = "insert into account(name,money) values (#{name},#{money})")
    public int save(Account account);
}
