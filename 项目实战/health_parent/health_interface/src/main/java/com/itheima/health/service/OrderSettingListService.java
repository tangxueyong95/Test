package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.User;

public interface OrderSettingListService {


    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void update(Integer id);
}
