package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:44
 * @Version V1.0
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    // 使用登录用户名查询唯一的用户信息
    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public PageResult findCurrentPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<User> page = userDao.findCurrentPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void saveUser(User user, Integer... roleIds) {
        //1.添加用户信息:
        userDao.saveUser(user);
        //2.添加用户和角色关联信息:
        Integer userId = user.getId();
        addUserReferenceRole(userId,roleIds);
    }

    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public Integer[] findReferenceRoleIds(Integer id) {
        return userDao.findReferenceRoleIds(id);
    }

    @Override
    public String findUserPasswordByUserId(Integer id) {
        return userDao.findUserPasswordByUserId(id);
    }

    @Override
    public void updateUser(User user, Integer... roleIds) {
        //1.更新用户基本数据:
        userDao.updateUserDetailMessage(user);

        //2.删除user关联的角色数据:
        userDao.deleteUserReferenceRoleIds(user.getId());

        //3.重新添加user关联关系:
        addUserReferenceRole(user.getId(),roleIds);
    }

    @Override
    public void deleteUser(Integer userId) {
        //1.删除用户关联角色信息:
        userDao.deleteUserReferenceRoleIds(userId);

        //2.删除用户基本信息:
        userDao.deleteUserDetailMessageByUserId(userId);
    }

    @Override
    public void updateUserPasswordByUsername(String newPassword, String username) {
        userDao.updateUserPasswordByUsername(newPassword,username);
    }

    private void addUserReferenceRole(Integer userId, Integer[] roleIds) {
        if (roleIds != null && roleIds.length > 0) {
            for (Integer roleId : roleIds) {
                userDao.addUserReferenceRole(userId,roleId);
            }
        }
    }
}
