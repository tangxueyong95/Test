package com.itheima.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/7/5 18:16
 * @Version V1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('add')")
    public String add(){
        System.out.println("add...");
        return null;
    }

    @RequestMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String update(){
        System.out.println("update...");
        return null;
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasRole('ABC')")
    public String delete(){
        System.out.println("delete...");
        return null;
    }
}
