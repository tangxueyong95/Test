package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:44
 * @Version V1.0
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupDao checkGroupDao;

    @Autowired
    CheckItemDao checkItemDao;

    // 新增检查组
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 1：保存检查组信息
        checkGroupDao.add(checkGroup);
        // 2：向检查组和检查项的中间表保存数据
        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);
    }

    // 分页查询检查组
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        // mybatis 分页插件
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    // 编辑检查组的保存
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 1：更新检查组的信息（update）
        checkGroupDao.editCheckGroup(checkGroup);
        // 2：更新检查组和检查项的中间表数据（中间表）
        //（1）先使用检查组的id，删除之前的数据
        checkGroupDao.deleteCheckGroupAndCheckItemByCheckGroupId(checkGroup.getId());
        //（2）再使用检查组的id，和检查项id，新增数据
        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);

    }

    @Override
    public void deleteById(Integer id) {
        // 1：删除中间表
        checkGroupDao.deleteCheckGroupAndCheckItemByCheckGroupId(id);
        // 2：删除检查项
        checkGroupDao.deleteById(id);
    }

    // 向检查组和检查项的中间表保存数据（Map指定参数）
    // 查询所有检查组
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    private void setCheckGroupAndCheckItem(Integer checkgourpid, Integer[] checkitemIds) {
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("checkgroup_id", checkgourpid);
                map.put("checkitem_id", checkitemId);
                checkGroupDao.addCheckGroupAndCheckItemMap(map);
            }
        }
    }


//    // 向检查组和检查项的中间表保存数据（@Param指定参数）
//    private void setCheckGroupAndCheckItem(Integer checkgourpid, Integer[] checkitemIds) {
//        if(checkitemIds!=null && checkitemIds.length>0){
//            for (Integer checkitemId : checkitemIds) {
//                checkGroupDao.addCheckGroupAndCheckItem(checkgourpid,checkitemId);
//            }
//        }
//    }

}
