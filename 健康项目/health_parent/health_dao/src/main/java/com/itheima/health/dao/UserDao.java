package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    // 使用登录名查询用户
    User findUserByUsername(String username);

    Page<User> findCurrentPage(String queryString);

    void saveUser(User user);

    void addUserReferenceRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    User findUserById(Integer id);

    Integer[] findReferenceRoleIds(Integer id);

    String findUserPasswordByUserId(Integer id);

    void updateUserDetailMessage(User user);

    void deleteUserReferenceRoleIds(Integer id);

    void deleteUserDetailMessageByUserId(Integer userId);

    void updateUserPasswordByUsername(@Param("newPassword") String newPassword,@Param("username") String username);
}
