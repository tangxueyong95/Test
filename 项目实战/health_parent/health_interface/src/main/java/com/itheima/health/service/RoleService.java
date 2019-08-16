package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
    List<Role> findAllRoles();

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    Map<String, Object> findAllPermissionAndMenu();

    void addRole(Role role, Integer[] permissionIds, Integer... menuIds);

    Role findRoleById(Integer roleId);

    Map<String, Object> findRoleReferencePermissionsAndMenuIds(Integer roleId);

    void updateRole(Role role, Integer[] permissionIds, Integer[] menuIds);

    void deleteRoleById(Integer roleId);
}
