package com.java.controller;

import com.java.domain.User;
import com.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 11:11
 * @Description: com.java.controller
 ****/
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /***
     * 查询集合
     */
    @RequestMapping(value = "/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }

}
