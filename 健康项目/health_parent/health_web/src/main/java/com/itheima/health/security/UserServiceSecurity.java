package com.itheima.health.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/7/7 15:08
 * @Version V1.0
 */
@Component
public class UserServiceSecurity implements UserDetailsService {

    @Reference
    UserService userService;

    // 认证和授权
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1：使用登录名查询唯一的用户的信息
        com.itheima.health.pojo.User u = userService.findUserByUsername(username);
        // 用户名输入有误
        if(u==null){
            return null;
        }
        // 2：获取当前登录人的信息中的密码（$2a$10$0soPGxPDqaTOLYfMj/G20uKWA79/jgBJZ8CJvxLII7YxXCvlQcP7W）
        String password = u.getPassword();
        // 3：授权的集合
        List<GrantedAuthority> list  = new ArrayList<>();
        // 有角色
        if(u!=null && u.getRoles()!=null && u.getRoles().size()>0){
            for (Role role : u.getRoles()) {
                // 不采用角色进行控制，采用权限进行控制
                //String keyword = role.getKeyword();
                //list.add(new SimpleGrantedAuthority(keyword));
                if(role!=null && role.getPermissions()!=null && role.getPermissions().size()>0){
                    for (Permission permission : role.getPermissions()) {
                        // 获取权限的keyword
                        String keyword = permission.getKeyword();
                        list.add(new SimpleGrantedAuthority(keyword));
                    }
                }
            }
        }

        // User(String username, String password, Collection<? extends GrantedAuthority> authorities)
        UserDetails user = new User(username,password,list); // 认证和授权
        return user;
    }
}
