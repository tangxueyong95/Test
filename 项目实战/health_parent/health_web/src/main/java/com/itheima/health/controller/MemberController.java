package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.MenuService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author NiTao
 * @date 2019/6/26-21:23
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Reference
    MemberService memberService;
    // 新增保存检查项
    @RequestMapping(value = "/add")
    public Result add(@RequestBody Member member){
        try {
            memberService.add(member);
            return new Result(true, MessageConstant.ADD_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_MEMBER_FAIL);
        }
    }

    // 分页查询会员（条件）
    @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = memberService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }
    // 编辑会员（表单回显）
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        try {
            Member member = memberService.findById(id);
            return new Result(true,MessageConstant.QUERY_MEMBER_SUCCESS,member);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_MEMBER_FAIL);
        }
    }
    // 编辑会员（编辑保存）
    @RequestMapping(value = "/edit")
    public Result edit(@RequestBody Member member){
        try {
            memberService.edit(member);
            return new Result(true,MessageConstant.EDIT_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_MEMBER_FAIL);
        }
    }
    // 会员删除
    @RequestMapping(value = "/delete")
    public Result delete(Integer id){
        try {
            memberService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_MEMBER_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_MEMBER_FAIL);
        }
    }

}
