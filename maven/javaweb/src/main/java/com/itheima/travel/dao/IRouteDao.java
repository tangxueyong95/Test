package com.itheima.travel.dao;

import com.itheima.travel.domain.Route;
import com.itheima.travel.domain.RouteImg;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.itheima.travel.dao
 * 作者:Leevi
 * 日期2019-05-29  09:51
 */
public interface IRouteDao {
    List<Route> findPopList();

    List<Route> findNewestList();

    List<Route> findThemeList();

    Integer findRouteCountByCid(String cid, String keyword);

    List<Route> findPageList(String cid, Integer currentPage, Integer pageSize, String keyword);

    Map<String, Object> findByRid(String rid);

    List<RouteImg> findRouteImgsByRid(String rid);
}
