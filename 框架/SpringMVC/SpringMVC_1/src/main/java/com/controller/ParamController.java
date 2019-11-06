package com.controller;

import com.domain.Account;
import com.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/param") // 通过@RequestMapping找到不同的模块
public class ParamController {

    // 3.6.2基本数据类型和字符串类型
    @RequestMapping(value = "/testParam")
    public String testParam(String username,Integer age){
        System.out.println("欢迎访问ParamController里的testParam方法！username:"+username+"   age:"+age);
        return "success";
    }

    // 传递实体类型
    @RequestMapping(value = "/testParamBean")
    public String testParamBean(User user){
        System.out.println("欢迎访问ParamController里的testParam方法！user:"+user);
        return "success";
    }

    // 3.6.3实体类型（JavaBean）
    @RequestMapping(value = "/testParamSaveAccount")
    public String testParamSaveAccount(Account account){
        System.out.println("欢迎访问ParamController里的testParamSaveAccount方法！account:"+account);
        return "success";
    }

    // 3.6.6自定义类型转换器（了解）

    /**
     * 自定义类型转换
     * 1：定义转换的类，类实现Convertor接口
     * 2：在springmvc.xml中配置类型转换类
     * @param user
     * @return
     */
    @RequestMapping(value = "/saveUser")
    public String saveUser(User user){
        System.out.println("欢迎访问ParamController里的saveUser方法！user:"+user);
        return "success";
    }

    // 3.6.7在控制器中使用原生的ServletAPI对象
    @RequestMapping(value = "/testServlet")
    public String testServlet(HttpServletRequest request, HttpSession session, HttpServletResponse response){
        System.out.println("欢迎访问ParamController里的testServlet方法！");
        System.out.println("name:"+request.getParameter("name"));
        System.out.println("request:"+request);
        System.out.println("session:"+session);
        System.out.println("application:"+request.getSession().getServletContext());
        System.out.println("response:"+response);
        return "success";
    }

}
