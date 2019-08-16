package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAllRoles();
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Role> page = roleDao.findPageByQueryString(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Map<String, Object> findAllPermissionAndMenu() {
        Map<String,Object> permissionAndMenuMap = new HashMap<>();
        //查询所有的权限信息:
        List<Permission> permissions = permissionDao.findAllPermission();
        //查询所有的菜单信息:
        List<Menu> menus = menuDao.findAllMenu();
        permissionAndMenuMap.put("permissionList",permissions);
        permissionAndMenuMap.put("menuList",menus);
        return permissionAndMenuMap;
    }

    /**
     * 添加角色业务
     * @param role
     * @param permissionIds
     * @param menuIds
     */
    @Override
    public void addRole(Role role, Integer[] permissionIds, Integer... menuIds) {
        //1.添加角色基本信息:
        roleDao.addRoleDetailMessage(role);
        //添加角色关联的权限和菜单关联:
        addRoleReferencePermissionAndMenuMessage(role.getId(),permissionIds,menuIds);
    }

    @Override
    public Role findRoleById(Integer roleId) {
        return roleDao.findRoleById(roleId);
    }

    /**
     *  根据roleId获取角色关联权限及菜单
     * @param roleId
     * @return
     */
    @Override
    public Map<String, Object> findRoleReferencePermissionsAndMenuIds(Integer roleId) {
        HashMap<String, Object> permissionAndMenuIdsMap = new HashMap<>();
        Integer[] permissionIds = roleDao.findReferencePermissionIds(roleId);
        Integer[] menuIds = roleDao.findReferenceMenuIds(roleId);
        permissionAndMenuIdsMap.put("permissionIds",permissionIds);
        permissionAndMenuIdsMap.put("menuIds",menuIds);
        return permissionAndMenuIdsMap;
    }

    @Override
    public void updateRole(Role role, Integer[] permissionIds, Integer[] menuIds) {
        //1.更新角色基本信息:
        roleDao.updateRoleDetailMessage(role);

        //2.删除角色关联信息:
        deleteRoleReferencePermissionAndMenuIds(role.getId());

        //3.重新添加关联:
        addRoleReferencePermissionAndMenuMessage(role.getId(),permissionIds,menuIds);
    }

    @Override
    public void deleteRoleById(Integer roleId) {
        //1.删除关联信息:
        deleteRoleReferencePermissionAndMenuIds(roleId);
        //2.删除基本信息:
        roleDao.deleteRoleDetailMessage(roleId);
    }

    private void deleteRoleReferencePermissionAndMenuIds(Integer roleId) {
        if (roleId != null) {
            roleDao.deleteRoleReferencePermissionIds(roleId);
            roleDao.deleteRoleReferenceMenuIds(roleId);
        }
    }

    private void addRoleReferencePermissionAndMenuMessage(Integer roleId, Integer[] permissionIds, Integer[] menuIds) {
        //1.关联权限
        if (permissionIds != null && permissionIds.length > 0) {
            for (Integer permissionId : permissionIds) {
                roleDao.addRoleReferencePermission(roleId,permissionId);
            }
        }

        //2.添加菜单关联:
        if (menuIds != null && menuIds.length > 0) {
            for (Integer menuId : menuIds) {
                roleDao.addRoleReferenceMenu(roleId,menuId);
            }
        }
    }
}
