package com.itheima.controller;

import com.itheima.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/16 9:16
 * @Version V1.0
 */
@Controller
@RequestMapping(value = "anno") // 通过@RequestMapping找到不同的模块
@SessionAttributes(value = {"name", "birthday"}, types = {Integer.class}) // value按照名称存放session；type按照类型存放session
public class AnnoController {

    /**
     * 4.1@RequestParam注解
     * 作用：
     * 把请求中指定名称的参数给控制器中的形参赋值。
     * 属性：
     * value：请求参数中的名称。
     * required：请求参数中是否必须提供此参数。默认值：true。表示必须提供，如果不提供将报错。
     * defaultValue：表示默认值，如果不传递值
     * 应用场景：分页的使用  page（1），size（5，10）
     */
    @RequestMapping(value = "testRequestParam")
    public String testRequestParam(@RequestParam(value = "name", required = true, defaultValue = "李四") String username, @RequestParam(value = "age") Integer age) {
        System.out.println("欢迎访问HelloController里的testRequestParam方法！username:" + username + "     age:" + age);
        return "success";
    }

    /**
     * 4.2@RequestBody注解
     * 作用：
     * 1：@RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)
     * 2：用于获取请求体内容。直接使用得到是key=value&key=value...结构的数据。 get请求方式不适用。（不多）
     * <p>
     * 属性：
     * required：是否必须有请求体。默认值是:true。
     * 当取值为true时,get请求方式会报错。
     * 如果取值为false，get请求得到是null。
     */
    @RequestMapping(value = "/testRequestBody")
    public String testRequestBody(@RequestBody(required = false) String body) {
        System.out.println("欢迎访问HelloController里的testRequestBody方法！body:" + body);
        return "success";
    }

    /**
     * 4.3@PathVariable注解
     * 作用：
     * 用于绑定url中的占位符。例如：请求url中 /delete/{id}/{name}，这个{id},{name}就是url占位符。
     * 传统方式：/delete?id=3&name=zhangsan
     * rest风格：/delete/3/zhangsan
     * url支持占位符是spring3.0之后加入的。是springmvc支持restful风格URL的一个重要标志。
     * 属性：
     * value：用于指定url中占位符名称。
     * required：是否必须提供占位符。
     */
    @RequestMapping(value = "/testPathVariable/{id}/{name}")
    public String testPathVariable(@PathVariable(value = "id") Integer id, @PathVariable(value = "name") String name) {
        System.out.println("欢迎访问HelloController里的testPathVariable方法！id:" + id + "         name:" + name);
        return "success";
    }

    /****************
     * RESTFUL风格的请求处理
     */
    // POST请求：新增方法
    @RequestMapping(value = "/testPathVariable", method = RequestMethod.POST)
    public String save(User user) {
        System.out.println("【保存】：欢迎访问HelloController里的save方法！user:" + user);
        return "success";
    }

    // PUT请求：修改方法
    @RequestMapping(value = "/testPathVariable", method = RequestMethod.PUT)
    @ResponseBody// 此时success不会执行视图解析器，响应一个字符串（json数组，对象）
    public /*@ResponseBody*/ String update(User user) {
        System.out.println("【更新】：欢迎访问HelloController里的update方法！user:" + user);
        return "success";
    }

    // DELETE请求：删除方法
    @RequestMapping(value = "/testPathVariable/{id}", method = RequestMethod.DELETE)
    // 此时success不会执行视图解析器，响应一个字符串（json数组，对象）
    public @ResponseBody
    String delete(@PathVariable(value = "id") Integer id) {
        System.out.println("【删除】：欢迎访问HelloController里的update方法！id:" + id);
        return "success";
    }

    // GET请求：查询方法
    @RequestMapping(value = "/testPathVariable/{id}", method = RequestMethod.GET)
    public String find(@PathVariable(value = "id") Integer id) {
        System.out.println("【查询】：欢迎访问HelloController里的find方法！id:" + id);
        return "success";
    }

    /**
     * 4.4@RequestHeader注解（了解）
     * 作用：
     * 用于获取请求消息头。
     * 属性：
     * value：提供消息头名称
     * required：是否必须有此消息头
     */
    @RequestMapping(value = "/testRequestHeader")
    public String testRequestHeader(@RequestHeader(value = "Accept") String header) {
        System.out.println("欢迎访问HelloController里的testRequestHeader方法！header：" + header);
        return "success";
    }

    /**
     * 4.5@CookieValue 注解（了解）
     * 作用：
     * 用于把指定cookie名称的值传入控制器方法参数。
     * 属性：
     * value：指定cookie的名称。
     * required：是否必须有此cookie。
     */
    @RequestMapping(value = "/testCookieValue")
    public String testCookieValue(@CookieValue(value = "JSESSIONID") String jsessionId) {
        System.out.println("欢迎访问HelloController里的testCookieValue方法！jsessionId：" + jsessionId);
        return "success";
    }

    /**
     * 4.6@ModelAttribute注解（了解）
     *    4.6.1基于POJO属性的基本使用（掌握）
     */
//    @ModelAttribute  // @ModelAttribute放置到model的方法上，当执行url请求的时候，先执行model的方法，然后再执行Controller中对应的请求方法
//    public void model(User user){
//        System.out.println("执行【@ModelAttribute】封装数据!user:"+user);
//        // 从数据库查询，将查询的生日封装到birthday的属性中
//        user.setBirthday(new Date());
//    }
//
//    @RequestMapping(value = "/testModelAttribute")
//    public String testModelAttribute(User user){
//        System.out.println("欢迎访问HelloController里的testModelAttribute方法！user："+user);
//        return "success";
//    }

    /**
     * 4.6.2基于Map的应用场景一：ModelAttribute修饰方法带返回值（了解）
     */
//    @ModelAttribute  // @ModelAttribute放置到model的方法上，当执行url请求的时候，先执行model的方法，然后再执行Controller中对应的请求方法
//    public User model(String username){
//        System.out.println("执行【@ModelAttribute】封装数据!username:"+username);
//        // 使用username从数据库查询，将查询的生日封装到birthday的属性中
//        User user = getUserByUserName(username);
//        return user;
//    }
//
//    @RequestMapping(value = "/testModelAttribute")
//    public String testModelAttribute(User user){
//        System.out.println("欢迎访问HelloController里的testModelAttribute方法！user："+user);
//        return "success";
//    }
//
//    private User getUserByUserName(String username) {
//        User user = new User();
//        user.setUsername(username); // 数据库
//        user.setAge(40); // 数据库
//        user.setBirthday(new Date()); // 数据库
//        return user;
//    }

    /**
     * 4.6.3基于Map的应用场景二：ModelAttribute修饰方法不带返回值（了解）
     */
    @ModelAttribute  // @ModelAttribute放置到model的方法上，当执行url请求的时候，先执行model的方法，然后再执行Controller中对应的请求方法
    public void model(String username, Map<String, User> map) {
        System.out.println("执行【@ModelAttribute】封装数据!username:" + username);
        // 使用username从数据库查询，将查询的生日封装到birthday的属性中
        User user = getUserByUserName(username);
        map.put("abc", user);
    }

    @RequestMapping(value = "/testModelAttribute")
    public String testModelAttribute(@ModelAttribute(value = "abc") User user) {
        System.out.println("欢迎访问HelloController里的testModelAttribute方法！user：" + user);
        return "success";
    }

    private User getUserByUserName(String username) {
        User user = new User();
        user.setUsername(username); // 数据库
        user.setAge(40); // 数据库
        user.setBirthday(new Date()); // 数据库
        return user;
    }

    /**
     * 4.7@SessionAttributes注解（了解）
     */
    // 设置
    @RequestMapping(value = "/sessionAttributePut")
    public String sessionAttributePut(Model model) {
        System.out.println("欢迎访问HelloController里的sessionAttributePut方法！");
        model.addAttribute("name", "张三"); // 等同于request
        model.addAttribute("age", 22);//只能设置@SessionAttributes注解中有的参数
        model.addAttribute("birthday", new Date());
        return "success";
    }

    // 获取
    @RequestMapping(value = "/sessionAttributeGet")
    public String sessionAttributeGet(ModelMap modelMap) {
        System.out.println("欢迎访问HelloController里的sessionAttributeGet方法！name:" + modelMap.get("name") + "        age:" + modelMap.get("age") + "          birthday:" + modelMap.get("birthday"));
        return "success";
    }

    // 清空
    @RequestMapping(value = "/sessionAttributeClean")
    public String sessionAttributeClean(SessionStatus sessionStatus) {
        System.out.println("欢迎访问HelloController里的sessionAttributeClean方法！");
        sessionStatus.setComplete();// 清空
        return "success";
    }
    @RequestMapping(value = "a")
    public ModelAndView a(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("anno");
        return modelAndView;
    }
}
