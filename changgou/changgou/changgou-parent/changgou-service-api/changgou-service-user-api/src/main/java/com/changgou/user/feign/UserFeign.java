package com.changgou.user.feign;

import com.changgou.user.pojo.User;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.user.feign
 ****/
@FeignClient(value = "user")
@RequestMapping(value = "/user")
public interface UserFeign {

    /***
     * 根据ID查询User数据
     * @param id
     * @return
     */
    @GetMapping("/load/{id}")
    Result<User> findById(@PathVariable(value = "id") String id);


    /***
     * 增加用户积分
     * @param points:要添加的积分
     */
    @GetMapping(value = "/points/add")
    Result addPoints(@RequestParam(value = "points") Integer points);
}
