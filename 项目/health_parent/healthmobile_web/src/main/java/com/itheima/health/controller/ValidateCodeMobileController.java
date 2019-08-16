package com.itheima.health.controller;

import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName SetmealMobileController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/7/2 15:57
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/validateCode")
public class ValidateCodeMobileController {

    @Autowired
    JedisPool jedisPool;

    @RequestMapping(value = "/send4Order")
    public Result send4Order(String telephone){
        try {
            // 1：获取手机号
            // 2：生成4位验证码，根据手机号，发送短信
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            // SMSUtils.sendShortMessage(telephone,code.toString()); // 不发送
            System.out.println("验证码："+code);

            // 3：将手机号和发送的验证码进行绑定，存放到redis中，用于后续校验和比对
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,5*60,code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
    // 登录的时候发送短信
    @RequestMapping(value = "/send4Login")
    public Result send4Login(String telephone){
        try {
            // 1：获取手机号
            // 2：生成4位验证码，根据手机号，发送短信
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            // SMSUtils.sendShortMessage(telephone,code.toString()); // 不发送
            System.out.println("验证码："+code);

            // 3：将手机号和发送的验证码进行绑定，存放到redis中，用于后续校验和比对
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_LOGIN,5*60,code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

}
