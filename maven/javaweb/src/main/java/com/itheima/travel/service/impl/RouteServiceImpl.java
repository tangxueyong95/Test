package com.itheima.travel.service.impl;

import com.itheima.travel.constant.Constant;
import com.itheima.travel.dao.IRouteDao;
import com.itheima.travel.domain.*;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.IRouteService;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包名:com.itheima.travel.service.impl
 * 作者:Leevi
 * 日期2019-05-29  09:51
 */
public class RouteServiceImpl implements IRouteService{
    private IRouteDao dao = (IRouteDao) BeanFactory.getBean("routeDao");
    @Override
    public Map<String, List<Route>> careChoose() {
        //1.创建一个Map来存放数据
        Map<String,List<Route>> map = new HashMap<>();
        //2.查询前4条人气旅游路线集合
        List<Route> popList = dao.findPopList();
        //3.查询前四条最新旅游路线集合
        List<Route> newestList = dao.findNewestList();
        //4.查询前四条主题旅游路线集合
        List<Route> themeList = dao.findThemeList();
        //将上述查询到的三组路线集合，存放到map
        map.put(Constant.POPKEY,popList);
        map.put(Constant.NEWESTKEY,newestList);
        map.put(Constant.THEMEKEY,themeList);
        return map;
    }

    @Override
    public PageBean<Route> findPage(String cid, Integer currentPage, String keyword) {
        //1.创建PageBean对象
        PageBean<Route> pageBean = new PageBean<>();
        //2.设置PageBean的对象的内容
        //2.1设置当前页数
        pageBean.setCurrentPage(currentPage);
        //2.2设置每页的数据条数
        Integer pageSize = Constant.ROUTE_PAGESIZE;
        pageBean.setPageSize(pageSize);
        //2.3设置总数据条数
        Integer totalSize = dao.findRouteCountByCid(cid,keyword);
        pageBean.setTotalSize(totalSize);
        //2.4设置当期页的数据集合
        List<Route> list = dao.findPageList(cid,currentPage,pageSize,keyword);
        pageBean.setList(list);
        return pageBean;
    }

    @Override
    public Route findByRid(String rid) throws InvocationTargetException, IllegalAccessException {
        Route route = new Route();
        //1.调用dao层的方法，根据rid连接查询tab_category、tab_route、tab_seller三张表进行查询
        Map<String, Object> map = dao.findByRid(rid);
        //将map中的数据封装到route对象中
        BeanUtils.populate(route,map);

        //封装category对象和seller对象
        Category category = new Category();
        BeanUtils.populate(category,map);
        route.setCategory(category);

        Seller seller = new Seller();
        BeanUtils.populate(seller,map);
        route.setSeller(seller);

        //2.调用dao层的犯法，根据rid查询tab_route_img表，查询图片集合
        List<RouteImg> routeImgList = dao.findRouteImgsByRid(rid);
        route.setRouteImgList(routeImgList);
        return route;
    }
}
