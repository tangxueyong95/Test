package com.java.dao;

import com.java.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 12:03
 * @Description: com.java.dao
 * JpaRepository:里面有增删改查各种操作方法
 ****/
public interface UserDao extends JpaRepository<User,Integer> {
}
