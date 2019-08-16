package com.itheima.travel.dao;

import com.itheima.travel.domain.Category;

import java.util.List;

/**
 * 包名:com.itheima.travel.dao
 * 作者:Leevi
 * 日期2019-05-29  08:41
 */
public interface ICategoryDao {
    List<Category> findAll();
}
