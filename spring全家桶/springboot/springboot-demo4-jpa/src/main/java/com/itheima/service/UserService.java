package com.itheima.service;

import com.itheima.domain.User;

import java.util.List;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/14 12:05
 * @Description: com.itheima.service
 ****/
public interface UserService {

    /***
     * 查询所有
     */
    List<User> findAll();

    /***
     * 根据ID查询
     */
    User findById(Integer id);

    /***
     * 添加用户
     */
    void add(User user);

    /****
     * 修改操作
     */
    void update(User user);

    /***
     * 删除
     */
    void delete(Integer id);
}
