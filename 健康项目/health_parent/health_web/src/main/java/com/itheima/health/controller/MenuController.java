package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
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
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;

    @RequestMapping("/getMenu")
    public Result getMenu() {
        try {
            //        获取当前用户名
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Menu> menuList = menuService.findMenuList(user.getUsername());
            return new Result(true, MessageConstant.GET_MENU_SUCCESS,menuList);
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_MENU_FAIL);
        }
    }
    // 新增保存菜单
    @RequestMapping(value = "/add")
    public Result add(@RequestBody Menu menu){
        try {
            //判断菜单名是否存在
            Menu m1= menuService.findMenuByName(menu.getName());
            if(m1!=null){
                return new Result(false,MessageConstant.MENU_NAME_HAS_EXIST);
            }
            List<Menu> list = menuService.findMenuByPath(menu.getPath());
            if (list.size()!=0){
                return new Result(false,MessageConstant.MENU_PATH_HAS_EXIST);
            }
            menuService.add(menu);
            return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
    }

    // 分页查询菜单（条件）
    @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = menuService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    // 删除菜单
    @RequestMapping(value = "/delete")
    public Result delete(Integer id){
        try {
            menuService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_MENU_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_MENU_FAIL);
        }
    }

    // 编辑菜单（表单回显）
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        try {
            Menu menu = menuService.findById(id);
            return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_MENU_FAIL);
        }
    }

    // 编辑菜单（编辑保存）
    @RequestMapping(value = "/edit")
    public Result edit(@RequestBody Menu menu){
        try {
            List<Menu> list= menuService.findMenuByPath(menu.getPath());
            /*if (list.size()>1){
                return new Result(false,MessageConstant.MENU_PATH_NOT_EDIT);
            }*/
            menuService.edit(menu);
            return new Result(true,MessageConstant.EDIT_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }
    }

    // 查询所有菜单
    @RequestMapping(value = "/findAll")
    public Result findAll(){
        try {
            List<Menu> list = menuService.findAll();
            return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_MENU_FAIL);
        }
    }


}
