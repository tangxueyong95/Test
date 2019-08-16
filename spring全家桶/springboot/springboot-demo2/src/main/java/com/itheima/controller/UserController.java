package com.itheima.controller;

import com.itheima.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/14 9:19
 * @Description: com.itheima.controller
 ****/
@RestController
public class UserController {

    @Value("${uname}")
    private String username;

    @Value("${address}")
    private String[] address;

    @Autowired
    private User user;

    /***
     * 测试
     * @return
     */
    @RequestMapping(value = "/hello")
    public String hello(){
        for (String s : address) {
            System.out.println(s);
        }
        return "hello springboot demo2!"+username+"---"+user;
    }

}
