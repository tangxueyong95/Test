package com.itheima.travel.dao;

import com.itheima.travel.domain.Favorite;
import com.itheima.travel.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 包名:com.itheima.travel.dao
 * 作者:Leevi
 * 日期2019-05-31  10:21
 */
public interface IFavoriteDao {
    Favorite findFavoriteByUidAndRid(User user, String rid);

    void addFavorite(User user, String rid, JdbcTemplate jdbcTemplate);

    void updateCount(String rid, JdbcTemplate jdbcTemplate);

    Integer findCountByRid(String rid, JdbcTemplate jdbcTemplate);

    void romeFavorite(User user, String rid, JdbcTemplate jdbcTemplate);

    void updateCount1(String rid, JdbcTemplate jdbcTemplate);
}
