package com.itheima.travel.dao.impl;

import com.itheima.travel.dao.IRouteDao;
import com.itheima.travel.domain.Route;
import com.itheima.travel.domain.RouteImg;
import com.itheima.travel.utils.JDBCUtil;
import com.itheima.travel.utils.StringUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 包名:com.itheima.travel.dao.impl
 * 作者:Leevi
 * 日期2019-05-29  09:51
 */
public class RouteDaoImpl implements IRouteDao{
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public List<Route> findPopList() {
        //查询人气旅游的路线集合:1.rflag=1表示该路线是上架的(未下架)  2.按照收藏次数count的降序排列  3.只取前四条
        String sql = "select * from tab_route where rflag=1 order by count desc limit 0,4";
        List<Route> popList = null;
        try {
            popList = template.query(sql, new BeanPropertyRowMapper<>(Route.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return popList;
    }

    @Override
    public List<Route> findNewestList() {
        //查询最新旅游路线集合:1.rflag=1  2.按照上架时间rdate的降序进行排列  3.只取前四条
        String sql = "select * from tab_route where rflag=1 order by rdate desc limit 0,4";
        List<Route> newestList = null;
        try {
            newestList = template.query(sql, new BeanPropertyRowMapper<>(Route.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return newestList;
    }

    @Override
    public List<Route> findThemeList() {
        //查询主题旅游路线集合:1.rflag=1 2.isThemeTour=1表示是主题路线  3.只取前四条
        String sql = "select * from tab_route where rflag=1 and isThemeTour=1 limit 0,4";
        List<Route> themeList = null;
        try {
            themeList = template.query(sql, new BeanPropertyRowMapper<>(Route.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return themeList;
    }

    @Override
    public Integer findRouteCountByCid(String cid, String keyword) {
        String sql = "select count(*) from tab_route where rflag=1";

        //声明一个集合存储SQL语句的参数
        List<Object> params = new ArrayList<>();
        if (!StringUtil.isEmpty(cid)){
            //表示cid不为空
            sql += " and cid=?";
            params.add(cid);
        }
        if(!StringUtil.isEmpty(keyword)){
            sql += " and rname like ?";
            params.add("%"+keyword+"%");
        }

        //判断keyword是否为空，以及cid是否为空
        Integer count = null;
        try {
            //将存储参数的集合转换成数组，并且传入
            count = template.queryForObject(sql, Integer.class,params.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Route> findPageList(String cid, Integer currentPage, Integer pageSize, String keyword) {
        String sql = "select * from tab_route where rflag=1";
        //声明一个集合存储SQL语句的参数
        List<Object> params = new ArrayList<>();
        if (!StringUtil.isEmpty(cid)){
            //表示cid不为空
            sql += " and cid=?";
            params.add(cid);
        }
        if(!StringUtil.isEmpty(keyword)){
            sql += " and rname like ?";
            params.add("%"+keyword+"%");
        }
        //最后还要拼接limit分页
        sql += " limit ?,?";
        params.add((currentPage-1)*pageSize);
        params.add(pageSize);
        List<Route> list = null;
        try {
            list = template.query(sql, new BeanPropertyRowMapper<>(Route.class),params.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Object> findByRid(String rid) {
        //进行连接查询
        String sql = "SELECT * FROM tab_route r,tab_category c,tab_seller s WHERE r.cid=c.cid AND r.sid=s.sid AND rid=?";

        //将这次连接查询得到的所有数据，返回给service层
        Map<String, Object> map = template.queryForMap(sql, rid);//这个方法返回的是一个map，map中的数据是:key是结果集的字段名，value是结果集的字段值

        return map;
    }

    @Override
    public List<RouteImg> findRouteImgsByRid(String rid) {
        String sql = "SELECT * FROM tab_route_img WHERE rid=?";
        List<RouteImg> routeImgList = null;
        try {
            routeImgList = template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return routeImgList;
    }
}
