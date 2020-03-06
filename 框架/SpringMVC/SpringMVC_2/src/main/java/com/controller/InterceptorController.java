package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/interceptor")
public class InterceptorController {

    // 普通测试
    @RequestMapping(value = "/testInterceptor")
    public String testInterceptor() {
        System.out.println("欢迎访问InterceptorController里的testInterceptor方法！");
        return "redirect:success"; //重定向相对路径，http://localhost:8080/SpringMVC_2/interceptor/success
    }
    @RequestMapping(value = "/success")
    public String testInterceptor1() {
        System.out.println("欢迎访问InterceptorController里的testInterceptor1方法！");
        return "redirect:/success.jsp";
    }
}
