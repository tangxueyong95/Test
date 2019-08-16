package com.itheima.travel.dao.impl;

import com.itheima.travel.dao.IFavoriteDao;
import com.itheima.travel.domain.Favorite;
import com.itheima.travel.domain.User;
import com.itheima.travel.utils.DateUtil;
import com.itheima.travel.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 包名:com.itheima.travel.dao.impl
 * 作者:Leevi
 * 日期2019-05-31  10:21
 */
public class FavoriteDaoImpl implements IFavoriteDao{
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public Favorite findFavoriteByUidAndRid(User user, String rid) {
        String sql = "select * from tab_favorite where uid=? and rid=?";
        Favorite favorite = null;
        try {
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), user.getUid(), rid);
        } catch (DataAccessException e) {
        }
        return favorite;
    }

    @Override
    public void addFavorite(User user, String rid, JdbcTemplate jdbcTemplate) {
        //往tab_favorite表中插入一条数据
        String sql = "insert into tab_favorite values (?,?,?)";
        jdbcTemplate.update(sql, rid, DateUtil.getCurrentDate(), user.getUid());
    }

    @Override
    public void updateCount(String rid, JdbcTemplate jdbcTemplate) {
        //更新tab_route表中的count字段 +1
        String sql = "update tab_route set count=count+1 where rid=?";
        jdbcTemplate.update(sql,rid);
    }
    @Override
    public void updateCount1(String rid, JdbcTemplate jdbcTemplate) {
        //更新tab_route表中的count字段 +1
        String sql = "update tab_route set count=count-1 where rid=?";
        jdbcTemplate.update(sql,rid);
    }

    @Override
    public Integer findCountByRid(String rid, JdbcTemplate jdbcTemplate) {
        //从tab_route表中根据rid查询count
        String sql= "select count from tab_route where rid=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, rid);
        return count;
    }

    @Override
    public void romeFavorite(User user, String rid, JdbcTemplate jdbcTemplate) {
        String sql = "DELETE FROM  tab_favorite WHERE rid=? and uid=?";
        jdbcTemplate.update(sql,rid,user.getUid());
    }
}
