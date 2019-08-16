package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CheckGroupDao {

    void add(CheckGroup checkGroup);

    void addCheckGroupAndCheckItem(@Param(value = "checkgroupid") Integer checkgroupid, @Param(value = "checkitemid") Integer checkitemid);

    void addCheckGroupAndCheckItemMap(Map<String, Object> map);

    Page<CheckGroup> findPage(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void editCheckGroup(CheckGroup checkGroup);

    void deleteCheckGroupAndCheckItemByCheckGroupId(Integer id);

    void deleteById(Integer id);

    List<CheckGroup> findAll();

    List<CheckGroup> findCheckGroupListById(Integer id);
}
