package com.itheima.travel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.constant.Constant;
import com.itheima.travel.dao.ICategoryDao;
import com.itheima.travel.domain.Category;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.ICategoryService;
import com.itheima.travel.utils.JedisUtil;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

/**
 * 包名:com.itheima.travel.service.impl
 * 作者:Leevi
 * 日期2019-05-29  08:40
 */
public class CategoryServiceImpl implements ICategoryService{
    private ICategoryDao dao = (ICategoryDao) BeanFactory.getBean("categoryDao");
    /*@Override
    public List<Category> findAll() {
        return dao.findAll();
    }*/
    /*@Override
    public List<Category> findAll() throws IOException {
        //1.从redis中获取所有的分类信息(以json的形式存放的)
        Jedis jedis = JedisUtil.getJedis();//获取连接
        String jsonStr = jedis.get(Constant.ALL_CATEGORY_KEY);
        //2.判断jsonStr是否为null
        List<Category> list = null;
        ObjectMapper mapper = new ObjectMapper();
        if (jsonStr == null) {
            //说明redis中还没有缓存，则需要到mysql中获取数据
            list = dao.findAll();
            //将list转换成jsonStr
            jsonStr = mapper.writeValueAsString(list);
            //将jsonStr存放到redis中
            jedis.set(Constant.ALL_CATEGORY_KEY,jsonStr);
        }else {
            //表示redis中有缓存，那么jsonStr就是缓存的所有分类信息的json数据
            //但是我们要返回的并不是这个json数据，而是一个集合List
            //我们就得将这个json数据转换成List集合
            list = mapper.readValue(jsonStr,new TypeReference<List<Category>>(){});
        }
        //将连接归还到连接池
        jedis.close();
        return list;
    }*/

    @Override
    public String findAll() throws JsonProcessingException {
        //1.从redis中获取所有的分类信息(以json的形式存放的)
        Jedis jedis = JedisUtil.getJedis();//获取连接
        String jsonStr = jedis.get(Constant.ALL_CATEGORY_KEY);
        //2.判断jsonStr是否为null
        if (jsonStr == null) {
            ObjectMapper mapper = new ObjectMapper();
            //说明redis中还没有缓存，则需要到mysql中获取数据
            List<Category> list = dao.findAll();
            //将list转换成jsonStr
            jsonStr = mapper.writeValueAsString(list);
            //将jsonStr存放到redis中
            jedis.set(Constant.ALL_CATEGORY_KEY,jsonStr);
        }
        //如果没有走到if说明从redis中获取到了jsonStr
        //将连接归还到连接池
        jedis.close();
        return jsonStr;
    }
}
