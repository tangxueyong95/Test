package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

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
@RequestMapping(value = "/order")
public class OrderMobileController {

    @Reference
    OrderService orderService;

    @Autowired
    JedisPool jedisPool;

    // 体检预约
    @RequestMapping(value = "/submit")
    public Result submit(@RequestBody Map<String,Object> map){
        try {
            // 手机号
            String telephone = (String)map.get("telephone");
            // 必须验证输入的验证码是对的，从redis中获取数据，和 页面传递的验证码比对
            String redisCode = jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_ORDER);
            // 页面输入的code
            String validateCode = (String)map.get("validateCode");
            if(redisCode==null || !redisCode.equals(validateCode)){
                return new Result(false,MessageConstant.VALIDATECODE_ERROR);
            }
            // 使用手机预约（微信）
            map.put("orderType", Order.ORDERTYPE_WEIXIN); // 微信预约
            Result result = orderService.submit(map);
            // 表示预约成功
            if(result.isFlag()){
                // 发送短信通知预约人，哪个时间，哪个医院，几点，预约号
                // SMSUtils.sendShortMessage();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }

    // 体检预约的查询
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){ //订单id
        try {
            Map<String,Object> map = orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
