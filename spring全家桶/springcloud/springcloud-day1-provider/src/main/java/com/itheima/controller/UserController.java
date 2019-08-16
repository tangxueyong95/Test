package com.itheima.controller;

import com.itheima.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/15 9:40
 * @Description: com.itheima.controller
 ****/
@RestController
@RequestMapping(value = "/user")
public class UserController {

    /****
     * http://localhost:18081/user/list
     */
    @RequestMapping(value = "/list")
    public List<User> list(){
        List<User> users = new ArrayList<User>();

        //封装用户信息
        users.add(new User("张三","深圳",24));
        users.add(new User("李四","南京",26));
        users.add(new User("王五","天津",28));
        return  users;
    }

}
