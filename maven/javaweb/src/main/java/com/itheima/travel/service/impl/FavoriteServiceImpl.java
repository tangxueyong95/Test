package com.itheima.travel.service.impl;

import com.itheima.travel.dao.IFavoriteDao;
import com.itheima.travel.domain.Favorite;
import com.itheima.travel.domain.User;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.IFavoriteService;
import com.itheima.travel.utils.JDBCUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 包名:com.itheima.travel.service.impl
 * 作者:Leevi
 * 日期2019-05-31  10:21
 */
public class FavoriteServiceImpl implements IFavoriteService{
    private IFavoriteDao dao = (IFavoriteDao) BeanFactory.getBean("favoriteDao");
    @Override
    public Favorite findFavoriteByUidAndRid(User user, String rid) {

        return dao.findFavoriteByUidAndRid(user,rid);
    }

    @Override
    public Integer addFavorite(User user, String rid) {
        //开启事务,获取connection,调用setAutoCommit(false)
        //1.开始初始化同步
        TransactionSynchronizationManager.initSynchronization();
        //2.获取数据源对象
        DataSource dataSource = JDBCUtil.getDataSource();
        Connection conn = null;
        Integer count = null;
        try {
            //3.创建一个JdbcTemplate对象
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            //4.获取当前的JbdcTemplate上使用的连接对象
            conn = DataSourceUtils.getConnection(dataSource);//这个获取的连接，就是JdbcTemplate对象中使用的连接
            //它里面是通过叫做"ThreadLocal"这个对象绑定的
            //保证了开启事务的connection和执行SQL语句的connection是同一个连接对象

            conn.setAutoCommit(false);
            //1.调用dao层的方法，往tab_favorite表中添加一条数据
            dao.addFavorite(user, rid, jdbcTemplate);
            //2.调用dao层的方法，修改route表的count字段
            dao.updateCount(rid, jdbcTemplate);
            //3.调用dao层的方法，查询route表中count字段的值
            count = dao.findCountByRid(rid, jdbcTemplate);


            //没有出现异常，提交事务,commit()方法
            conn.commit();
        } catch (Exception e) {
            //出现异常回滚事务,rollback()
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            //1.清除事务同步
            TransactionSynchronizationManager.clearSynchronization();
            //2.将conn对象的autoCommit属性还原
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public Integer romeFavorite(User user, String rid) {
        //开启事务,获取connection,调用setAutoCommit(false)
        //1.开始初始化同步
        TransactionSynchronizationManager.initSynchronization();
        //2.获取数据源对象
        DataSource dataSource = JDBCUtil.getDataSource();
        Connection conn = null;
        Integer count = null;
        try {
            //3.创建一个JdbcTemplate对象
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            //4.获取当前的JbdcTemplate上使用的连接对象
            conn = DataSourceUtils.getConnection(dataSource);//这个获取的连接，就是JdbcTemplate对象中使用的连接
            //它里面是通过叫做"ThreadLocal"这个对象绑定的
            //保证了开启事务的connection和执行SQL语句的connection是同一个连接对象

            conn.setAutoCommit(false);
            //1.调用dao层的方法，往tab_favorite表中删除一条数据
            dao.romeFavorite(user, rid, jdbcTemplate);
            //2.调用dao层的方法，修改route表的count字段
            dao.updateCount1(rid, jdbcTemplate);
            //3.调用dao层的方法，查询route表中count字段的值
            count = dao.findCountByRid(rid, jdbcTemplate);


            //没有出现异常，提交事务,commit()方法
            conn.commit();
        } catch (Exception e) {
            //出现异常回滚事务,rollback()
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            //1.清除事务同步
            TransactionSynchronizationManager.clearSynchronization();
            //2.将conn对象的autoCommit属性还原
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }
}
