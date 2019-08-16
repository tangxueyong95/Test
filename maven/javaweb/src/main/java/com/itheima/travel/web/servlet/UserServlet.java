package com.itheima.travel.web.servlet;

import com.itheima.travel.constant.Constant;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.exception.AlreadyActiveException;
import com.itheima.travel.exception.ErrorCodeException;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.IUserService;
import com.itheima.travel.utils.Md5Util;
import com.itheima.travel.utils.UuidUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user")
public class UserServlet extends BaseServlet {
    private IUserService service = (IUserService) BeanFactory.getBean("userService");
    //BaseServlet要处理什么请求，就定义一个什么方法

    /**
     * 处理退出登录的请求
     * @param request
     * @param response
     */
    private ResultInfo logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //退出登录其实就是销毁session对象
        request.getSession().invalidate();
        //跳转到首页或者登录页面
        response.sendRedirect("index.html");
        return null;
    }
    /**
     * 处理获取用户信息的请求
     * @param request
     * @param response
     */
    private ResultInfo getUserInfo(HttpServletRequest request, HttpServletResponse response){
        ResultInfo info = new ResultInfo(false);
        try {
            //从session中获取用户信息
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Constant.USER_KEY);
            //将user封装到ResultInfo对象中响应给客户端
            info.setFlag(true);
            info.setData(user);
        } catch (Exception e) {
            info.setMessage(Constant.SERVSER_ERROR);
        }
        return info;
    }
    /**
     * 处理登录请求的方法
     * @param request
     * @param response
     */
    private ResultInfo login(HttpServletRequest request,HttpServletResponse response){
        //创建一个ResultInfo封装响应结果
        ResultInfo info = new ResultInfo(false);
        //1.获取客户端传入的验证码，用户名，密码
        String checkCode = request.getParameter("checkCode");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //2.校验验证码是否正确
        //从Session中获取服务器生成的验证码
        HttpSession session = request.getSession();
        String vCode = (String) session.getAttribute(Constant.CHECKCODE_KEY);
        if (vCode.equalsIgnoreCase(checkCode)) {
            //验证码正确
            //则调用业务层的方法，校验用户名和密码
            try {
                User user = service.checkLogin(username,password);
                //登录成功
                //将user存放到session中
                session.setAttribute(Constant.USER_KEY,user);
                info.setFlag(true);
            } catch (Exception e) {
                info.setMessage(e.getMessage());
            }
        }else {
            //验证码错误
            info.setMessage(Constant.LOGINFAILED_ERROR_VCODE);
        }
        return info;
    }
    /**
     * 处理激活请求的方法
     * @param request
     * @param response
     */
    private ResultInfo active(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //1.获取客户端携带的请求参数code:激活码
        String code = request.getParameter("code");
        //2.调用业务层的方法处理激活
        try {
            service.doActive(code);
            //表示激活成功，跳转到登录页面
            response.sendRedirect("login.html");

            //以后的项目中，会针对不同的异常做不同的处理
        } catch (AlreadyActiveException e) {
            response.getWriter().write(e.getMessage());
        } catch (ErrorCodeException e) {
            response.getWriter().write(e.getMessage());
        }
        return null;
    }
    /**
     * 注册的方法
     * @param request
     * @param response
     */
    private ResultInfo register(HttpServletRequest request,HttpServletResponse response){
        //创建一个封装结果的ResultInfo对象
        ResultInfo info = new ResultInfo();
        //1.获取客户端提交的所有请求参数
        Map<String, String[]> map = request.getParameterMap();
        //2.将所有请求参数封装到User对象中
        User user = new User();
        try {
            BeanUtils.populate(user,map);
            //3.将密码加密
            String password = user.getPassword();
            password = Md5Util.encodeByMd5(password);
            user.setPassword(password);
            //4.设置user的status(激活状态)、code(激活码)
            user.setStatus(Constant.UNACTIVE);
            //激活码就是一串唯一的字符串，使用UUIDUtil生成一串唯一的字符串
            String code = UuidUtil.getUuid();
            user.setCode(code);
            //调用业务层的方法，进行注册
            service.doRegister(user);

            info.setFlag(true);
        } catch (Exception e) {
            info.setFlag(false);
            info.setMessage(Constant.REGISTER_FAILED);
        }
        return info;
    }
    /**
     * 处理校验用户名是否已存在的请求
     * @param request
     * @param response
     */
    private ResultInfo checkUsername(HttpServletRequest request,HttpServletResponse response){
        //创建一个对象封装响应数据
        ResultInfo info = new ResultInfo();
        try {
            //1.获取传入的用户名
            String username = request.getParameter("username");
            //2.调用业务层的方法，根据username查找用户
            User user = service.findUserByUsername(username);
            //3.判断用户是否已存在
            if (user == null) {
                //说明用户名不存在，就是可用
                info.setFlag(true);
                info.setMessage(Constant.USERNAME_ENABLE);
            }else {
                //说明用户名已存在，就是不可用
                info.setFlag(false);
                info.setMessage(Constant.USERNAME_EXIST);
            }
        } catch (Exception e) {
            //服务器出现异常
            info.setFlag(false);
            info.setMessage(Constant.SERVSER_ERROR);
        }
        return info;
    }
}
