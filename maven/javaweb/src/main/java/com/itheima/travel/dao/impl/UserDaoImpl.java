package com.itheima.travel.dao.impl;

import com.itheima.travel.dao.IUserDao;
import com.itheima.travel.domain.User;
import com.itheima.travel.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 包名:com.itheima.travel.dao.impl
 * 作者:Leevi
 * 日期2019-05-28  09:55
 */
public class UserDaoImpl implements IUserDao{
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public User findUserByUsername(String username) {
        String sql = "select * from tab_user where username=?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (DataAccessException e) {

        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        String sql = "insert into tab_user values (null,?,?,?,?,?,?,?,?,?)";
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),user.getStatus(),user.getCode());
    }

    @Override
    public User findUserByCode(String code) {
        String sql = "select * from tab_user where code=?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        } catch (DataAccessException e) {

        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sql = "update tab_user set username=?,password=?,name=?,telephone=?,status=? where uid=?";
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getTelephone(),user.getStatus(),user.getUid());
    }
}
