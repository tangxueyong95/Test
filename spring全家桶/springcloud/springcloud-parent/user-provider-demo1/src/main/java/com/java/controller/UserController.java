package com.java.controller;

import com.java.domain.User;
import com.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/15 10:15
 * @Description: com.java.controller
 ****/
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /****
     * http://localhost:18081/user/2
     */
    @RequestMapping(value = "/{id}")
    public User findById(@PathVariable(value = "id")Integer id){
        User user =  userService.getById(id);
        user.setUsername(user.getUsername()+"----user-provider-demo1");
        return user;
    }

}
