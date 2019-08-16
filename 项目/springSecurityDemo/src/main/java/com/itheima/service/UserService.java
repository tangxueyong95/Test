package com.itheima.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/7/5 17:15
 * @Version V1.0
 */
@Component
public class UserService implements UserDetailsService {

    // 查询数据库
    // 初始化数据库的数据Map集合的key，表示登录名，Map集合的value：表示User对象
    static Map<String,com.itheima.health.pojo.User> map = new HashMap<>();
    static{
        // 初始化2条数据
        com.itheima.health.pojo.User user1 = new com.itheima.health.pojo.User();
        user1.setGender("1");
        user1.setUsername("zhangsan");
        user1.setPassword("$2a$10$i0E6IfooRo/ZDiEPGs4BJ.F1GoAjBQgTkB4MuZ754RdrN17ffQkva");

        com.itheima.health.pojo.User user2 = new com.itheima.health.pojo.User();
        user2.setGender("2");
        user2.setUsername("admin");
        user2.setPassword("$2a$10$sUr5N1oW3r06ynMuK6YiVOAgcEUrF6sx20MG9EUUwvlUCFUvhOM4C");

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }
    // 在SpringSecurity认证的过程（登录的过程）先执行loadUserByUsername方法，传递登录名，使用登录名查询数据库，返回User对象
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        // 使用登录名查询数据库
        com.itheima.health.pojo.User user = map.get(name);
        // 表示登录名没有查到数据（没有用户）
        if(user==null){
            return null;// 表示登录名输入有误！抛出异常
        }

        // 认证、授权
        // 授权的集合
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("add"));// 具有add权限
        authorities.add(new SimpleGrantedAuthority("delete"));// 具有delete权限
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));// 具有ROLE_USER角色
        /**
         * User(String username, String password, Collection<? extends GrantedAuthority> authorities)
         *   参数一：表示登录名
         *   参数二：从数据库中查询的密码（使用页面中传递的密码和数据库中查询的密码进行比对，不用你做，SpringSecurity帮你完成；如果成功匹配，就登录成功；如果没有成功匹配，就会抛出异常）
         *   参数三：表示权限
         */
        // User springSecurityUser = new User(name,"{noop}"+user.getPassword(),authorities);
        User springSecurityUser = new User(name,user.getPassword(),authorities);
        return springSecurityUser;
    }
}
