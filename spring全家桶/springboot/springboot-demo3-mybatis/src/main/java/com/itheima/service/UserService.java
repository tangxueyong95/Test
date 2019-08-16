package com.itheima.service;

import com.itheima.domain.User;

import java.util.List;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/14 11:09
 * @Description: com.itheima.service
 ****/
public interface UserService {

    /***
     * 查询所有
     */
    List<User> findAll();
}
