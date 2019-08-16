package com.itheima.travel.dao;

import com.itheima.travel.domain.User;

/**
 * 包名:com.itheima.travel.dao
 * 作者:Leevi
 * 日期2019-05-28  09:55
 */
public interface IUserDao {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 保存用户信息的方法
     * @param user
     */
    void saveUser(User user);

    /**
     * 根据激活码查找user的方法
     * @param code
     * @return
     */
    User findUserByCode(String code);

    /**
     * 修改用户信息的方法
     * @param user
     */
    void updateUser(User user);
}
