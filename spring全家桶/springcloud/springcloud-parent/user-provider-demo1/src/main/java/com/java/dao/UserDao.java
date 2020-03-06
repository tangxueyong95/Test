package com.java.dao;

import com.java.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/15 10:13
 * @Description: com.java.dao
 ****/
public interface UserDao extends JpaRepository<User,Integer> {
}
