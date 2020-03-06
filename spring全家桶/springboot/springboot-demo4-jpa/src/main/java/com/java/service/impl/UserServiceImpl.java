package com.java.service.impl;

import com.java.dao.UserDao;
import com.java.domain.User;
import com.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 12:07
 * @Description: com.java.service.impl
 ****/
@Service
public class UserServiceImpl implements UserService {

    //注入Dao
    @Autowired
    private UserDao userDao;

    /***
     * 查询所有
     * @return
     */
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public User findById(Integer id) {
        return userDao.findById(id).get();
    }

    /***
     * 添加用户
     * @param user
     */
    @Override
    public void add(User user) {
        userDao.save(user);     //save方法：会根据数据库识别，是否有该数据，如果有，则修改，如果没有，则增加
    }

    /***
     * 添加用户
     * @param user
     */
    @Override
    public void update(User user) {
        userDao.save(user);
    }

    /****
     * 删除操作
     * @param id
     */
    @Override
    public void delete(Integer id) {
        userDao.deleteById(id);
    }
}
