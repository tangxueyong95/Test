package com.changgou.oauth.controller;

import com.changgou.oauth.service.LoginService;
import com.changgou.oauth.util.AuthToken;
import com.changgou.oauth.util.CookieUtil;
import com.changgou.oauth.util.UserJwt;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.oauth.controller
 ****/
@RestController
@RequestMapping(value = "/user")
public class LoginController {


    @Autowired
    private LoginService loginService;

    @Value("${auth.clientId}")
    private String clientid;

    @Value("${auth.clientSecret}")
    private String secret;

    /*****
     * clientid：客户端ID
     * secret:秘钥
     * ->base64加密  Basic +Base64密文
     * username：账号
     * password：密码
     * grant_type：password
     * 提交地址： http://localhost:9001/oauth/token
     * 提交方式：Post
     */
    @PostMapping(value = "/login")
    public Result login(String username, String password, HttpServletRequest request, HttpServletResponse response) throws Exception{
        //登录实现
        AuthToken authToken = loginService.login(username,password,clientid,secret,"password");

        if(authToken!=null){
            CookieUtil.addCookie(response,"localhost", "/", "Authorization",authToken.getAccessToken(), 3600,false);
        }
        return new Result(true, StatusCode.OK,"登录成功！",authToken);
    }

}
