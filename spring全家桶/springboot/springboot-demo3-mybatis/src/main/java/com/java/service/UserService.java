package com.java.service;

import com.java.domain.User;

import java.util.List;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 11:09
 * @Description: com.java.service
 ****/
public interface UserService {

    /***
     * 查询所有
     */
    List<User> findAll();
}
