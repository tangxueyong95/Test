package com;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JDBC {
    public static User b(String name, String age) {
        User user=null;
        String sql = "SELECT NAME,age FROM use2 WHERE NAME=? AND age = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(Druid.getDataSource());
        List<User> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class),name,age);
        for (User users : list) {
            user=users;
        }
        return user;
    }

}
