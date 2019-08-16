package com.itheima.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/17 12:17
 * @Description: com.itheima.controller
 ****/
@RestController
@RefreshScope   //刷新配置文件数据
@RequestMapping(value = "/msg")
public class LoadMessageController {

//    @Value("${test.message}")
//    private String message;
//
//    /***
//     * 读取配置文件数据
//     * @return
//     */
//    @RequestMapping(value = "/load")
//    public String load(){
//        return message;
//    }

}
