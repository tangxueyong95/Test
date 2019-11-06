package com.java.service.impl;

import com.java.dao.UserDao;
import com.java.domain.User;
import com.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/15 10:14
 * @Description: com.java.service.impl
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
