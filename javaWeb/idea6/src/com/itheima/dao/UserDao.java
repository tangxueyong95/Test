package com.itheima.dao;

import com.itheima.domain.User;
import com.itheima.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 包名:com.itheima.dao
 * 作者:Leevi
 * 日期2019-05-21  11:19
 */
public class UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());
    public List<User> search(String name) {
        String sql = "select * from user where name like ? limit 0,4";
        List<User> list = null;
        try {
            list = template.query(sql, new BeanPropertyRowMapper<>(User.class), "%" + name + "%");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
