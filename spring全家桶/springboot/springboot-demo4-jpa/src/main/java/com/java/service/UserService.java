package com.java.service;

import com.java.domain.User;

import java.util.List;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 12:05
 * @Description: com.java.service
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
