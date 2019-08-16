package com.itheima.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/14 9:04
 * @Description: com.itheima.controller
 ****/
@RestController
public class UserController {

    /****
     * 测试
     * @return
     */
    @RequestMapping(value = "/hello")
    public String hello(){
        return "hello springboot!";
    }
}
