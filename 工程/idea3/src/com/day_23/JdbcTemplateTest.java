package com.day_23;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcTemplateTest {

    public static void main(String[] args) {
        String sql = "SELECT NAME,age FROM use2 WHERE NAME=? AND age = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(Druid.getDataSource());
        List<User> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class),"李四","33");
        for (User user : list) {
            System.out.println(user);
        }
       /* List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }*/
       /* List<User> list = jdbcTemplate.query(sql, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User p = new User();
                p.set姓名(resultSet.getString("姓名"));
                p.set总分(resultSet.getInt("总分"));
                p.set班级(resultSet.getString("班级"));
                return p;
            }
        });*/
        /*for (User user : list) {
            System.out.println(user);
        }*/
    }
}
