package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleDao {

    // 使用用户id，查询用户id所具有的角色集合
    Set<Role> findRolesByUserId(Integer userId);

    List<Role> findAllRoles();

    Page<Role> findPageByQueryString(String queryString);

    void addRoleDetailMessage(Role role);

    void addRoleReferencePermission(@Param("roleId") Integer roleId,@Param("permissionId") Integer permissionId);

    void addRoleReferenceMenu(@Param("roleId") Integer roleId,@Param("menuId") Integer menuId);

    Role findRoleById(Integer roleId);

    Integer[] findReferencePermissionIds(Integer roleId);

    Integer[] findReferenceMenuIds(Integer roleId);

    void updateRoleDetailMessage(Role role);

    void deleteRoleReferencePermissionIds(Integer roleId);

    void deleteRoleReferenceMenuIds(Integer roleId);

    void deleteRoleDetailMessage(Integer roleId);
}
