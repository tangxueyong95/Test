package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SetmealDao {

    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(Map<String, Object> map);

    Page<Setmeal> findPage(String queryString);

    Setmeal findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void editCheckGroup(Setmeal setmeal);

    void deleteCheckGroupAndCheckItemByCheckGroupId(Integer id);

    void deleteById(Integer id);

    List<Setmeal> findAll();
}
