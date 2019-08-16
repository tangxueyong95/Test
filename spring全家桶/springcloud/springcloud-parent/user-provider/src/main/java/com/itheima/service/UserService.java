package com.itheima.service;

import com.itheima.domain.User;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/15 10:13
 * @Description: com.itheima.service
 ****/
public interface UserService {

    /***
     * 根据ID查询用户信息
     * @param id
     */
    User getById(Integer id);

}
