package com.changgou.seckill.controller;

import com.changgou.seckill.pojo.SeckillOrder;
import com.changgou.seckill.service.SeckillOrderService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.SeckillStatus;
import entity.StatusCode;
import entity.TokenDecode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/
@Api(value = "SeckillOrderController")
@RestController
@RequestMapping("/seckillOrder")
@CrossOrigin
public class SeckillOrderController {

    @Autowired
    private SeckillOrderService seckillOrderService;


    /***
     * 用户排队状态查询
     */
    @GetMapping(value = "/query")
    public Result queryStatus(String username){
        //获取用户登录名
        //String username = "wangwu"; //TokenDecode.getUserInfo().get("username");

        //调用Service
        SeckillStatus seckillStatus = seckillOrderService.queryStatus(username);
        if(seckillStatus!=null){
            return new Result(true,seckillStatus.getStatus(),"查询抢单信息",seckillStatus);
        }
        return new Result(true,StatusCode.OK,"没有找到对应的抢单信息");
    }


    /****
     * URL:/seckill/order/add
     * 添加订单
     * 调用Service增加订单
     * 匿名访问：anonymousUser
     * @param time
     * @param id
     */
    @RequestMapping(value = "/add")
    public Result add(String time, Long id,String username){
        try {
            //用户登录名
            //String username = "szitheima";//TokenDecode.getUserInfo().get("username");

            //调用Service增加订单
            Boolean bo = seckillOrderService.add(id, time, username);

            if(bo){
                //抢单成功
                return new Result(true,StatusCode.OK,"抢单成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true,StatusCode.ERROR,"服务器繁忙，请稍后再试");
    }

}
