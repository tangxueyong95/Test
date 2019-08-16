package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/15 10:14
 * @Description: com.itheima.service.impl
 ****/
@Service
public class UserServiceImpl implements UserService{

    //注入Dao
    @Autowired
    private UserDao userDao;

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public User getById(Integer id) {
        return userDao.findById(id).get();
    }
}
