package com.itheima.service;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;

import java.util.List;

/**
 * 包名:com.itheima.service
 * 作者:Leevi
 * 日期2019-05-21  11:20
 */
public class UserService {
    private UserDao dao = new UserDao();
    public List<User> search(String name) {
        //调用dao层的方法进行搜索
        return dao.search(name);
    }
}
