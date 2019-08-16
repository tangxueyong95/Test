package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName SetmealMobileController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/7/2 15:57
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/login")
public class LoginMobileController {

    @Reference
    MemberService memberService;

    @Autowired
    JedisPool jedisPool;

    // 登录
    @RequestMapping(value = "/check")
    public Result check(@RequestBody Map<String,Object> map, HttpServletResponse response){
        try {
            // 手机号
            String telephone = (String)map.get("telephone");
            // 必须验证输入的验证码是对的，从redis中获取数据，和 页面传递的验证码比对
            String redisCode = jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_LOGIN);
            // 页面输入的code
            String validateCode = (String)map.get("validateCode");
            if(redisCode==null || !redisCode.equals(validateCode)){
                return new Result(false,MessageConstant.VALIDATECODE_ERROR);
            }
            // 注册会员
            // 使用手机号查询当前登录是否是会员（唯一）
            Member member = memberService.findMemeberByTelephone(telephone);
            // 不是会员，注册
            if(member==null){
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            // 将登录信息存放到Cookie中
            Cookie cookie = new Cookie("mobile_health_web",telephone);
            cookie.setMaxAge(30*24*60*60);// 30天
            cookie.setPath("/");
            response.addCookie(cookie);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.LOGIN_FAIL);
        }
    }


}
