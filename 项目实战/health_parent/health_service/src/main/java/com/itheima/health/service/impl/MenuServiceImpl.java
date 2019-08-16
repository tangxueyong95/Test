package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.User;
import com.itheima.health.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:44
 * @Version V1.0
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuDao menuDao;
    @Autowired
    UserDao userDao;

    @Override
    public List<Menu> findMenuList(String username) {

        User user = userDao.findUserByUsername(username);
        List<Menu> menuList = menuDao.findMenuList(user.getId());
        if (menuList != null && menuList.size() > 0) {
            for (Menu menu : menuList) {
                List<Menu> childrenList = menuDao.findChildrenByMenuId(menu.getId(), user.getId());
                menu.setChildren(childrenList);
            }
        }
        return menuList;
    }
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize); // 底层用mysql计算分页
        Page<CheckItem> page = menuDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Menu menu) {
        setParams(menu);
        menuDao.add(menu);
    }

    private void setParams(Menu menu) {
        String path = menu.getPath();
        if (!path.contains("/")){
            menu.setParentMenuId(null);
            menu.setLevel(1);
            menu.setPriority(Integer.parseInt(path)-1);
        }else {
            String fu = path.split("/")[1].split("-")[0];
            String zi = path.split("/")[1].split("-")[1];
            Menu m = menuDao.findFuIdByPath(fu);
            if (m!=null){
                menu.setPriority(Integer.parseInt(zi));
                menu.setLevel(2);
                menu.setParentMenuId(m.getId());
            }else {
                throw new RuntimeException("不存在该项对应的父级，请检查之后重新输入");
            }
        }
    }

    @Override
    public void edit(Menu menu) {
        setParams(menu);
        menuDao.edit(menu);
    }

    @Override
    public Menu findById(Integer id) {
        return menuDao.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        // 1：使用检查项id，查询t_role_checkitem，判断是否存在数据，如果存在数据，则不能删除
        long count = menuDao.findCountByMenuId(id);
        // 有数据
        if(count>0){
            throw new RuntimeException(MessageConstant.DELETE_MENU_RELATION);
        }
        // 2：删除检查项
        menuDao.deleteById(id);
    }

    @Override
    public List<Menu> findAll() {
        return menuDao.findAll();
    }

    @Override
    public Menu findMenuByName(String name) {
        return menuDao.findMenuByName(name);
    }

    @Override
    public List<Menu> findMenuByPath(String path) {
        return menuDao.findMenuByPath(path);
    }
}
