package com.changgou.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.oauth.controller
 ****/
@Controller
public class LoginRedirect {

    /***
     * 登录跳转页
     * @return
     */
    @RequestMapping(value = "/oauth/login")
    public String login(@RequestParam(value = "FROM")String from, Model model){
        //存储用户请求地址
        model.addAttribute("from",from);
        return "login";
    }

}
