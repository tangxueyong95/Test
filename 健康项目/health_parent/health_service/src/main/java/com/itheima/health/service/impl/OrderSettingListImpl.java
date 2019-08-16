package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderSettingListDao;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.OrderSettingList;
import com.itheima.health.pojo.User;
import com.itheima.health.service.OrderSettingListService;
import com.itheima.health.service.UserService;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service(interfaceClass = OrderSettingListService.class)
@Transactional
public class OrderSettingListImpl implements OrderSettingListService {

    @Autowired
    OrderSettingListDao orderSettingListDao;
    @Autowired
    MemberDao memberDao;

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize); // 底层用mysql计算分页
        String today = null;
        try {
            today = DateUtils.parseDate2String(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Page<OrderSettingList> page = orderSettingListDao.findPage(today);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void update(Integer id) {
        orderSettingListDao.update(id);
    }
}
