package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/16 9:16
 * @Version V1.0
 */
@Controller
@RequestMapping(value = "user") // 通过@RequestMapping找到不同的模块
public class HelloController {

    // 返回字符串
    @RequestMapping(value = "hello",method = {RequestMethod.GET,RequestMethod.DELETE})
    public String sayHello(){
        System.out.println("欢迎访问HelloController里的sayHello方法！");
        return "redirect:/anno.jsp"; // 返回值String，执行视图解析器，/anno.jsp
    }

    /**
     * method = {RequestMethod.GET,RequestMethod.DELETE}：必须满足GET请求，或者DELETE请求
     * params = {"username"}：表示传递的参数，必须要求传递username的参数
     * headers = "Accept"：必须访问该方法的时候带有请求头，Accept，没有报错
     * @return
     */
    @RequestMapping(value = "/save",method = {RequestMethod.GET,RequestMethod.DELETE},params = {"name"},headers = "Accept")
    public String save(){
        System.out.println("欢迎访问HelloController里的save方法！");
        return "success"; // 返回值String，执行视图解析器，/success.jsp
    }

//    // 返回ModelAndView
//    @RequestMapping(value = "/hello")
//    public ModelAndView sayHello(){
//        System.out.println("欢迎访问HelloController里的sayHello方法！");
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("success"); // 等同于：return "success"
//        mv.addObject("username","张三"); // 等同于request.setAttribute()
//        return mv;
//    }
}
