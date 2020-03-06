package com.java.controller;

import com.java.domain.User;
import com.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 12:10
 * @Description: com.java.controller
 ****/
@RestController
@RequestMapping(value = "/user")
public class UserController {

    //注入Service
    @Autowired
    private UserService userService;

    /****
     * 查询所有
     */
    @RequestMapping(value = "/findAll")
    public List<User> findAll(){
        //调用Service查询数据
        return  userService.findAll();
    }

}
