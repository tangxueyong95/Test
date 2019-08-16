package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> findMenuList(String username);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void add(Menu menu);

    void edit(Menu menu);

    Menu findById(Integer id);

    void deleteById(Integer id);

    List<Menu> findAll();

    Menu findMenuByName(String name);

    List<Menu> findMenuByPath(String path);
}
