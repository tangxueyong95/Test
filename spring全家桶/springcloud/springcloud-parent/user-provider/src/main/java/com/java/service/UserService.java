package com.java.service;

import com.java.domain.User;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/15 10:13
 * @Description: com.java.service
 ****/
public interface UserService {

    /***
     * 根据ID查询用户信息
     * @param id
     */
    User getById(Integer id);

}
