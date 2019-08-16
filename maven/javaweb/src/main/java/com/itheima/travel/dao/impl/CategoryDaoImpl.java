package com.itheima.travel.dao.impl;

import com.itheima.travel.dao.ICategoryDao;
import com.itheima.travel.domain.Category;
import com.itheima.travel.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 包名:com.itheima.travel.dao.impl
 * 作者:Leevi
 * 日期2019-05-29  08:41
 */
public class CategoryDaoImpl implements ICategoryDao{
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        List<Category> list = null;
        try {
            list = template.query(sql, new BeanPropertyRowMapper<>(Category.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
