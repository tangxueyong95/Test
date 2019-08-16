package com.controller;


import com.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/16 9:16
 * @Version V1.0
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {


    // 2.2.1返回字符串
    /**
     * String success;执行视图解析器，转发到WEB-INF/pages/success.jsp
     * 转发（一次请求）：http://localhost:8080/springmvc_day02_response/user/testResponse
     * @return
     */
    @RequestMapping(value = "/testResponse")
    public String testResponse(){
        System.out.println("欢迎访问UserController里的testResponse方法！");
        return "success";
    }

    /**
     * 表单回显
     * Model model：map结构，用于存值，request作用域存值
     * @return
     */
    @RequestMapping(value = "/testUpdate")
    public String testUpdate(Model model){
        System.out.println("欢迎访问UserController里的testUpdate方法！");
        User user = new User();
        user.setUsername("张三");
        user.setAge(22);
        model.addAttribute("user",user);
        return "success";
    }

    /**
     * 更新
     * @return
     */
    @RequestMapping(value = "/update")
    public String update(User user){
        System.out.println("欢迎访问UserController里的update方法！");
        System.out.println(user); // 执行更新
        return "success";
    }

    // 2.2.2返回值是void（了解）
    /**
     * 情况1：使用void跳转到testVoid.jsp
     * 执行的也是视图解析器
     * 请求：http://localhost:8080/springmvc_day02_response/user/testVoid
     * 转发到：/springmvc_day02_response/WEB-INF/pages/user/testVoid.jsp
     */
//    @RequestMapping(value = "/testVoid")
//    public void testVoid(){
//        System.out.println("欢迎访问UserController里的testVoid方法！");
//    }

    /**
     * 不想转发到：/springmvc_day02_response/WEB-INF/pages/user/testVoid.jsp
     * 情况2：可以使用请求转发或者重定向跳转到指定的页面
     */
//    @RequestMapping(value = "/testVoid")
//    public void testVoid(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        System.out.println("欢迎访问UserController里的testVoid方法！");
//        // 转发（路径：http://localhost:8080/springmvc_day02_response/user/testVoid）（可以转发到WEB-INF下的页面）
//        // request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request,response);
//        // 重定向：(路径：http://localhost:8080/springmvc_day02_response/WEB-INF/pages/success.jsp）
//        //response.sendRedirect(request.getContextPath()+"/WEB-INF/pages/success.jsp");
//        // 重定向：（路径：http://localhost:8080/springmvc_day02_response/user/testUpdate），跳转到success.jsp
//        response.sendRedirect(request.getContextPath()+"/user/testUpdate");
//
//    }

    /**
     * 情况3：如果不指定转发和重定向，直接响应数据。
     */
    @RequestMapping(value = "/testVoid")
    public void testVoid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("欢迎访问UserController里的testVoid方法！");
        // ajax请求，不走视图解析器
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("你好！spring");
    }

    // 2.2.3返回值是ModelAndView对象
    @RequestMapping(value = "/testModelAndView")
    public ModelAndView testModelAndView() throws Exception {
        System.out.println("欢迎访问UserController里的testModelAndView方法！");
        /**
         * ModelAndView
         *  2个功能
         *    1个是Model
         *    另1个是View
         */
        ModelAndView modelAndView = new ModelAndView();

        List<User> list = new ArrayList<User>();

        User user = new User();
        user.setUsername("张三");
        user.setAge(22);

        User user2 = new User();
        user2.setUsername("李四");
        user2.setAge(20);

        list.add(user);
        list.add(user2);
        // Model的功能
        modelAndView.addObject("list",list);
        // View的功能（视图解析器）
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     * 2.3	SpringMVC框架提供的转发和重定向（关键字）
            2.3.1forward请求转发
            2.3.2redirect重定向
     */
    @RequestMapping(value = "/testForwardOrRedirect")
    public String testForwardOrRedirect() throws Exception {
        System.out.println("欢迎访问UserController里的testForwardOrRedirect方法！");
        // 转发(forward关键字，视图解析器失效了)
        // return "forward:/WEB-INF/pages/success.jsp";
        // return "forward:/user/testUpdate";
        // 重定向（redirect关键字）
        return "redirect:/user/testUpdate";
    }
    @RequestMapping(value = "/testAjax")
    @ResponseBody
    public User testAjax(@RequestBody User user){
        System.out.println("欢迎访问UserController里的testAjax方法！");
        // 第一步：看请求
        System.out.println(user);
        // 第二步：看响应（json的形式返回）（将对象转换成json）
        User u = new User();
        u.setUsername("赵薇");
        u.setAge(30);
        return u;
    }
}
