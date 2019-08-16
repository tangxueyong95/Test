package com.changgou.seckill.service;

import com.changgou.seckill.pojo.SeckillGoods;
import com.github.pagehelper.PageInfo;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:SeckillGoods业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface SeckillGoodsService {

    /****
     * 查询指定时间段的秒杀商品列表
     * @param key
     */
    List<SeckillGoods> list(String key);

    /***
     * 查询某个商品详情
     * @param id
     * @param time
     * @return
     */
    SeckillGoods one(Long id, String time);
}
