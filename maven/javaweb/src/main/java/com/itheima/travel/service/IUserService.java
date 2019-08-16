package com.itheima.travel.service;

import com.itheima.travel.domain.User;
import com.itheima.travel.exception.AlreadyActiveException;
import com.itheima.travel.exception.ErrorCodeException;

/**
 * 包名:com.itheima.travel.service
 * 作者:Leevi
 * 日期2019-05-28  09:54
 */
public interface IUserService {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 处理注册的业务方法
     * @param user
     * @return
     */
    void doRegister(User user) throws Exception;

    /**
     * 激活的方法
     * @param code
     * @throws AlreadyActiveException
     * @throws ErrorCodeException
     */
    void doActive(String code) throws AlreadyActiveException, ErrorCodeException;

    /**
     * 处理登录的方法
     * @param username
     * @param password
     * @return
     */
    User checkLogin(String username, String password);

}
