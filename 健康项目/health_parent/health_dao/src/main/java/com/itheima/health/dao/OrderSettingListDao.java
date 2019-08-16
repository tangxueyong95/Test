package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.OrderSettingList;
import com.itheima.health.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSettingListDao {

    Page<OrderSettingList> findPage(String today);

    void update(Integer id);
}
