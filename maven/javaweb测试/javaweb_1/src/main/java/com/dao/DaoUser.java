package com.dao;

import com.domain.User;
import com.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class DaoUser {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());

    public boolean register1(String name) {
        String sql = "SELECT * FROM user where username=?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), name);
        } catch (DataAccessException e) {
        }
        if (user != null) {
            return true;
        } else
            return false;
    }

    public boolean register(String name, String password) {
        String sql = "INSERT INTO user VALUES(null,?,?)";
        int i = 0;
        try {
            i = template.update(sql, name, password);
        } catch (DataAccessException e) {
        }
        if (i == 1) {
            return true;
        } else {
            return false;
        }
    }

    public User login(String name, String password) {
        String sql = "SELECT * FROM user where username=? and password=?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), name,password);
        } catch (DataAccessException e) {
        }
        return user;
    }
}
