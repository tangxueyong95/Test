package com.dao;

import com.domain.Contact;
import com.domain.PageBean;
import com.utils.JDBCUtil;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 包名:com.dao
 * 作者:Leevi
 * 日期2019-05-13  11:57
 */
public class ContactDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());
    private boolean b = false;
    private int i;
    private List<Contact> contacts = null;

    public List<Contact> findAllContacts() {
        String sql = "select * from use2";
        try {
            contacts = template.query(sql, new BeanPropertyRowMapper<>(Contact.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public boolean remove(String id) {
        String sql = "DELETE FROM use2 WHERE id=?";
        try {
            i = template.update(sql, id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return p(i);
    }

    public boolean add(Contact contact) {
        String sql = "INSERT INTO use2 VALUES (NULL,?,?)";
        try {
            i = template.update(sql, contact.getName(), contact.getAge());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return p(i);
    }

    public boolean update(Contact contact) {
        String sql = "UPDATE use2 SET NAME=?,age=? WHERE id=?";
        try {
            i = template.update(sql, contact.getName(), contact.getAge(), contact.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return p(i);
    }

    private boolean p(int i) {
        if (i == 1) {
            b = true;
        }
        return b;
    }


    public PageBean find(Integer currentPage) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(currentPage);
        String sql = "select * from use2 LIMIT ?,?";
        String sql1 = "select count(*) from use1";
        int n = (currentPage - 1) * 5;
        int m = currentPage * 5;
        try {
            contacts = template.query(sql, new BeanPropertyRowMapper<>(Contact.class), n, m);
            pageBean.setList(contacts);
            Integer o = template.queryForObject(sql1, Integer.class);
            pageBean.setPageSize(5);
            pageBean.setTotalSize(o);
            pageBean.setTotalPage(o / 5 + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageBean;
    }
}
