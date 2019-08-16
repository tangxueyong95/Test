package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    @RequestMapping("/findAllRoles")
    public Result findAllRoles () {
        try {
            List<Role> roles = roleService.findAllRoles();
            return new Result(true, MessageConstant.QUERY_ROLELIST_SUCCESS,roles);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_ROLELIST_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage (@RequestBody QueryPageBean queryPageBean) {
        return roleService.findPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),queryPageBean.getQueryString());
    }

    @RequestMapping("/findAllPermissionAndMenu")
    public Result findAllPermissionAndMenu () {
        try {
            Map<String,Object> permissionAndMenuMap = roleService.findAllPermissionAndMenu();
            return new Result(true,MessageConstant.FIND_PERMISSION_AND_MENU_SUCCESS,permissionAndMenuMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.FIND_PERMISSION_AND_MENU_FAIL);
        }
    }

    @RequestMapping("/addRole")
    public Result addRole (@RequestBody Role role,Integer[] permissionIds,Integer[] menuIds) {
        try {
            roleService.addRole(role,permissionIds,menuIds);
            return new Result(true,MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_ROLE_FAIL);
        }
    }

    @RequestMapping("/findRoleById")
    public Result findRoleById (Integer roleId) {
        try {
            Role role = roleService.findRoleById(roleId);
            return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    @RequestMapping("/findRoleReferencePermissionsAndMenuIds")
    public Result findRoleReferencePermissionsAndMenuIds (Integer roleId) {
        try {
            Map<String,Object> resultMap = roleService.findRoleReferencePermissionsAndMenuIds(roleId);
            return new Result(true,MessageConstant.QUERY_ROLE_REFERENCE_PERMISSION_AND_MENU_SUCCESS,resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ROLE_REFERENCE_PERMISSION_AND_MENU_FAIL);
        }
    }

    @RequestMapping("/updateRole")
    public Result updateRole (@RequestBody Role role,Integer[] permissionIds,Integer[] menuIds) {
        try {
            roleService.updateRole(role,permissionIds,menuIds);
            return new Result(true,MessageConstant.UPDATE_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.UPDATE_ROLE_ERROR);
        }
    }

    @RequestMapping("/deleteRole")
    public Result deleteRole (Integer id) {
        try {
            roleService.deleteRoleById(id);
            return new Result(true,MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_ROLE_FAIL);
        }
    }

}
