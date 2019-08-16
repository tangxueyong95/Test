package com.itheima.controller;

import com.itheima.domain.User;
import com.itheima.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/17 9:05
 * @Description: com.itheima.controller
 ****/
@RestController
@RequestMapping(value = "/feign")
public class ConsumerUserFeignController {

    //此时会给UserFeignClient生成一个代理对象
    @Autowired
    private UserFeignClient userFeignClient;

    /****
     * 使用Feign调用远程服务的方法
     * @return
     */
    @RequestMapping(value = "/{id}")
    public User findById(@PathVariable(value = "id")Integer id){
        return  userFeignClient.findById(id);
    }

}
