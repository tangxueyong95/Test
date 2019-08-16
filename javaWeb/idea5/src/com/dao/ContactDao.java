package com.dao;

import com.domain.User;
import com.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class ContactDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());

    public User longin(String name) {
        User user = null;
        String sql = "SELECT * FROM use1 WHERE NAME=?";
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), name);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void updateUser(User user) {
        String sql = "update use1 set number=?,time=?,suo=? where id=?";
        template.update(sql, user.getNumber(), user.getTime(), user.getSuo(), user.getId());
    }

    public boolean add(String name, String password) {
        String sql = "INSERT INTO use1(NAME,PASSWORD) VALUES (?,?)";
        int i=0;
        try {
            i = template.update(sql, name, password);
        } catch (Exception e){
            e.printStackTrace();
        }
        if(i==1){
            return true;
        }
        return false;
    }
}
