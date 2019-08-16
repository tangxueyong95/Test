package com.itheima.travel.service;

import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.Route;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 包名:com.itheima.travel.service
 * 作者:Leevi
 * 日期2019-05-29  09:50
 */
public interface IRouteService {
    Map<String,List<Route>> careChoose();

    PageBean<Route> findPage(String cid, Integer currentPage, String keyword);

    Route findByRid(String rid) throws InvocationTargetException, IllegalAccessException;
}
