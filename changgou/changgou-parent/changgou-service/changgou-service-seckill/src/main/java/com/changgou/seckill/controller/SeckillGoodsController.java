package com.changgou.seckill.controller;

import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SeckillGoodsService;
import com.github.pagehelper.PageInfo;
import entity.DateUtil;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/
@Api(value = "SeckillGoodsController")
@RestController
@RequestMapping("/seckillGoods")
@CrossOrigin
public class SeckillGoodsController {

    @Autowired
    private SeckillGoodsService seckillGoodsService;


    /***
     * 获取指定商品详情信息
     * @param time
     * @param id
     */
    @RequestMapping(value = "/one")
    public SeckillGoods one(Long id,String time){
        return seckillGoodsService.one(id,time);
    }

    /****
     * 秒杀菜单查询
     */
    @RequestMapping(value = "/menus")
    public List<Date> dateMenus(){
        List<Date> dateMenus = DateUtil.getDateMenus();

        for (Date dateMenu : dateMenus) {
            System.out.println(DateUtil.data2str(dateMenu, DateUtil.PATTERN_YYYY_MM_DDHHMM));
        }
        return dateMenus;
    }


    /****
     * URL:/seckill/goods/list
     * 对应时间段秒杀商品集合查询
     * 调用Service查询数据
     * @param time:2019050716
     */
    @RequestMapping(value = "/list")
    public List<SeckillGoods> list(String time){
        //调用Service查询数据
        return seckillGoodsService.list(time);
    }

}
