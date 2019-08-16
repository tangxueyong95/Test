package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.User;
import com.itheima.health.pojo.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author NiTao
 * @date 2019/6/26-21:47
 */
@Repository
public interface MenuDao {

    List<Menu> findMenuList(Integer id);

    List<Menu> findChildrenByMenuId(@Param("menu_id") Integer menu_id, @Param("role_id") Integer role_id);

    Page<CheckItem> findPage(String queryString);

    void add(Menu menu);

    void edit(Menu menu);

    Menu findById(Integer id);

    long findCountByMenuId(Integer id);

    void deleteById(Integer id);

    List<Menu> findAll();

    Menu findMenuByName(String name);

    List<Menu> findMenuByPath(String path);

    List<Menu> findAllMenu();

    Menu findFuIdByPath(String fu);
}
