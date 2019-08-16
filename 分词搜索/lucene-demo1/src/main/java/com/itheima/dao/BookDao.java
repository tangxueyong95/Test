package com.itheima.dao;

import com.itheima.domain.Book;

import java.util.List;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/18 10:30
 * @Description: com.itheima.dao
 ****/
public interface BookDao {

    /**
     * 查询所有的book数据
     * @return
     */
    List<Book> queryBookList() throws Exception;
}
