package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:45
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Reference
    UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 获取当前登录用户的用户信息（SpringSecurity）
    @RequestMapping(value = "/getUsername")
    public Result upload(){
        try {
            // 不使用SpringSecurity，从Session中获取（登录成功需要放置到Session）
            // 使用SpringSecurity（UserDetails user = new User(username,password,list); ）
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();//Principal() == User()
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    /**
     * 获取当前页用户
     * @return
     */
    @RequestMapping("/findCurrentPage")
    public PageResult findCurrentPage (@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = userService.findCurrentPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return pageResult;
    }

    /**
     * 保存用户方法:
     * @param user
     * @param roleIds
     * @return
     */
    @RequestMapping("/saveUser")
    public Result saveUser (@RequestBody com.itheima.health.pojo.User user, Integer[] roleIds) {
        try {
            //将用户传输过来的密码加密处理:
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user,roleIds);
            return new Result(true,MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_USER_FAIL);
        }
    }

    /**
     * 更具id获取user对象信息
     * @param id
     * @return
     */
    @RequestMapping("/findUserById")
    public Result findUserById (Integer id) {
        try {
            com.itheima.health.pojo.User user = userService.findUserById(id);
            return new Result(true,MessageConstant.QUERY_USER_SUCCESS,user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_USER_FAIL);
        }
    }

    @RequestMapping("/findReferenceRoleIds")
    public Result findReferenceRoleIds (Integer id) {
        try {
            Integer[] roleIds = userService.findReferenceRoleIds(id);
            return new Result(true,MessageConstant.QUERY_USERREFERENCEROLEID_SUCCESS,roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_USERREFERENCEROLEID_FAIL);
        }
    }

    /**
     * 更新用户信息
     * @param
     * @return
     */
    @RequestMapping("/updateUser")
    public Result updateUser (Integer[] roleIds, @RequestBody com.itheima.health.pojo.User user) {
        try {
            //判断用户是否更改了密码:
            String password = userService.findUserPasswordByUserId(user.getId());
            String newPassword = user.getPassword();
            if (newPassword == null && newPassword == "") {
                throw new Exception(MessageConstant.PASSWORD_ERROR);
            } else if (!newPassword.equals(password)){
                user.setPassword(passwordEncoder.encode(newPassword));
            }
            //继续执行
            userService.updateUser(user,roleIds);
            return new Result(true,MessageConstant.UPDATE_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.UPDATE_USER_FAIL);
        }
    }

    @RequestMapping("/deleteUser")
    public Result deleteUser(@RequestBody com.itheima.health.pojo.User user){
        try {
            userService.deleteUser(user.getId());
            return new Result(true,MessageConstant.DELETE_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_USER_FAIL);
        }
    }

    /**
     * 当前用户点击修改密码操作:
     * @param map
     * @return
     */
    @RequestMapping("/changePassword")
    public Result changePassword(@RequestBody Map<String,String> map){
        try {
            //获取当前对象:
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            //获取当前对象密码:
            com.itheima.health.pojo.User userInDb = userService.findUserByUsername(username);
            String passwordInDb = userInDb.getPassword();
            //对比旧密码
            if (!passwordEncoder.matches(map.get("oldPassword"),passwordInDb)) {
                return new Result(false,MessageConstant.PASSWORD_FAULT);
            }
            String newPassword = passwordEncoder.encode(map.get("newPassword"));
            userService.updateUserPasswordByUsername(newPassword, username);
            return new Result(true,MessageConstant.CHANGE_PASSWORD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.CHANGE_PASSWORD_FAIL);
        }
    }


}
