package com.itheima.dao;

import com.itheima.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/15 10:13
 * @Description: com.itheima.dao
 ****/
public interface UserDao extends JpaRepository<User,Integer> {
}
