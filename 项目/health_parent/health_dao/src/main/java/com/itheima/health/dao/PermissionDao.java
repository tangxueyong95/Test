package com.itheima.health.dao;

import com.itheima.health.pojo.Permission;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PermissionDao {

    // 使用用户id，查询用户id所具有的角色集合
    Set<Permission> findPermissionsByRoleId(Integer roleId);
}
