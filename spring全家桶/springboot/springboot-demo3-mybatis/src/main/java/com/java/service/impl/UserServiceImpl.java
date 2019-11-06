package com.java.service.impl;

import com.java.domain.User;
import com.java.mapper.UserMapper;
import com.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 11:09
 * @Description: com.java.service.impl
 ****/
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    //RedisTemplate 操作Redis缓存
    private RedisTemplate redisTemplate;

    /***
     * 查询集合
     * @return
     */
    @Override
    public List<User> findAll() {
        //先判断Redis中是否有数据
        List<User> users = (List<User>) redisTemplate.boundValueOps("UserList").get();

        //如果有，则直接返回数据
        if(users!=null){
            System.out.println("有数据==========");
            return users;
        }
        System.out.println("========没有数据");

        //如果没有，则查询数据库
        users = userMapper.findAll();

        //再讲数据存入到缓存中
        if(users!=null && users.size()>0){
            redisTemplate.boundValueOps("UserList").set(users);
        }
        return users;
    }
}
