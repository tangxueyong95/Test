package com.changgou.oauth.service;

import com.changgou.oauth.util.AuthToken;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.oauth.service
 ****/
public interface LoginService {
    /***
     * 登录
     * @param username:用户名
     * @param password：密码
     * @param clientid：客户端ID
     * @param secret：秘钥
     * @param granttype：授权模式
     * @return
     */
    AuthToken login(String username, String password, String clientid, String secret, String granttype) throws Exception;
}
