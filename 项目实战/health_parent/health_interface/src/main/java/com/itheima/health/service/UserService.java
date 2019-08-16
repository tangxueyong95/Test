package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.User;

public interface UserService {

    User findUserByUsername(String username);

    PageResult findCurrentPage(Integer currentPage, Integer pageSize, String queryString);

    void saveUser(User user, Integer... roleIds);

    User findUserById(Integer id);

    Integer[] findReferenceRoleIds(Integer id);

    String findUserPasswordByUserId(Integer id);

    void updateUser(User user, Integer... roleIds);

    void deleteUser(Integer userId);

    void updateUserPasswordByUsername(String newPassword, String username);
}
