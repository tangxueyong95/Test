package com.java.controller;

import com.java.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 9:19
 * @Description: com.java.controller
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
