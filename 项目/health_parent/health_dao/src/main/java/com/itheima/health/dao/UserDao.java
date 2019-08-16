package com.itheima.health.dao;

import com.itheima.health.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    // 使用登录名查询用户
    User findUserByUsername(String username);
}
