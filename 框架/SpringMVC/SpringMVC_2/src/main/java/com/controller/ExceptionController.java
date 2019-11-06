package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/exception")
public class ExceptionController {

    // 普通测试
    @RequestMapping(value = "/testException")
    public String testException() {
        System.out.println("欢迎访问ExceptionController里的testException方法！");
        // Controller，Service，Dao抛出异常
        try {
            int i=10/0;
        } catch (Exception e) {
            e.printStackTrace(); // 不能用，忽略了异常（测试环境）
        }
        return "success";
    }

}
