package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PermissionDao {

    // 使用用户id，查询用户id所具有的角色集合
    Set<Permission> findPermissionsByRoleId(Integer roleId);

    void add(Permission permission);

    Page<Permission> findPage(String queryString);

    void deleteById(Integer id);

    Permission findById(Integer id);

    void edit(Permission permission);

    List<Permission> findAll();

    long findCountByPermissionId(Integer id);

    List<Permission> findAllPermission();
}
