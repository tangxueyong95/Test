package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:44
 * @Version V1.0
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    CheckItemDao checkItemDao;

    // 新增保存检查项
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
        //int i = 10/0;
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        // 传统的做法
        // 查询2次
        // 第1次查询总记录数：select count(*) from t_checkitem
        // 第2次使用分页查询：select * from t_checkitem where name=? or code=? limit ?,?(0,2;2,2;(currentPage-1)*pageSize,pageSize)
        /**使用Mybatis的分页插件PageHelper*/
        PageHelper.startPage(currentPage,pageSize); // 底层用mysql计算分页
        Page<CheckItem> page = checkItemDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        // 1：使用检查项id，查询t_checkgroup_checkitem，判断是否存在数据，如果存在数据，则不能删除
        long count = checkItemDao.findCountByCheckItemId(id);
        // 有数据
        if(count>0){
            throw new RuntimeException(MessageConstant.DELETE_CHECKITEM_RELATION);
        }
        // 2：删除检查项
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
