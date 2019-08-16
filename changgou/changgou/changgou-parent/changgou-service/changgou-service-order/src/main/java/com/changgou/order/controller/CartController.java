package com.changgou.order.controller;

import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import entity.StatusCode;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.order.controller
 * 购物车操作对象
 ****/
@RestController
@RequestMapping(value = "/cart")
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;

    /***
     * 查询用户购物车集合
     * @return
     */
    @GetMapping(value = "/list")
    public Result<List<OrderItem>> list(){
        String username = TokenDecode.getUserInfo().get("username");
        //String username = "szitheima";
        //查询购物车
        List<OrderItem> orderItems = cartService.list(username);
        return new Result<List<OrderItem>>(true,StatusCode.OK,"查询成功！",orderItems);
    }

    /***
     * 添加购物车实现
     */
    @RequestMapping(value = "/add")
    public Result add(Long id,Integer num){
        String username = TokenDecode.getUserInfo().get("username");
        //设置用户名  ?
        //String username="szitheima";

        //调用Service实现增加购物车
        cartService.add(id,num,username);
        return new Result(true, StatusCode.OK,"加入购物车成功！");
    }
}
