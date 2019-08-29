package com.itheima.controller;

import com.itheima.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima.controller
 ****/
@Controller
@RequestMapping(value = "/test")
public class TestController {


    /***
     * 模板渲染案例
     * @return
     */
    @RequestMapping(value = "/hello")
    public String hello(Model model){
        System.out.println("--------");
        //存储一个数据
        model.addAttribute("msg","hello！欢迎来到大黑马！");


        //数据集合输出
        List<User> users = new ArrayList<User>();
        users.add(new User(1,"张三","深圳"));
        users.add(new User(2,"李四","北京"));
        users.add(new User(3,"王五","武汉"));
        model.addAttribute("users",users);

        //Map定义
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("No","123");
        dataMap.put("address","深圳");
        model.addAttribute("dataMap",dataMap);

        //存储一个数组
        String[] names = {"张三","李四","王五"};
        model.addAttribute("names",names);

        //日期
        model.addAttribute("now",new Date());

        //if条件
        model.addAttribute("age",22);

        return "demo1";
    }

}
