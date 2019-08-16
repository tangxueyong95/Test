package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @ClassName SetmealMobileController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/7/2 15:57
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/setmeal")
public class SetmealMobileController {

    @Reference
    SetmealService setmealService;

    @Autowired
    JedisPool jedisPool;

    @RequestMapping(value = "/getSetmeal")
    public Result findAll() throws Exception {
        List<Setmeal> list = null;
        String str = jedisPool.getResource().get("findAll");
        if (str == null) {
            try {
                list = setmealService.findAll();
                set("findAll", list);
                return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, list);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
            }
        } else {
            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(str, new TypeReference<List<Setmeal>>() {
            });
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, list);
        }
    }


    @RequestMapping(value = "/findById")
    public Result findById(Integer id) {
        // 使用套餐id的主键，查询套餐id的信息
        List<Setmeal> list = null;

        String str = jedisPool.getResource().get(id+"");
        if (str == null) {
                try {
                    Setmeal setmeal1 = setmealService.findById(id);
                    set(""+id+"", setmeal1);
                    return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal1);
                } catch (Exception e) {
                    e.printStackTrace();
                return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
                }
        } else {
            Setmeal setmeal = get(""+id+"", Setmeal.class);
            if (!(setmeal.getId().equals(id))) {
                try {
                    setmeal = setmealService.findById(id);
                    set("findById", setmeal);
                    return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
                }
            }else {
                setmeal = get(""+id+"", Setmeal.class);
                return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
            }
        }
    }


    public boolean set(String key, Object value) {
        Jedis jedis = null;
        try {
            String objectJson = JSON.toJSONString(value);
            jedis = jedisPool.getResource();
            jedis.set(key, objectJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public Setmeal get(String key, Class<Setmeal> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.get(key);
            JSON.parseObject(value, clazz);
            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

}
