package com.java.dao;

import com.java.domain.Book;

import java.util.List;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/18 10:30
 * @Description: com.java.dao
 ****/
public interface BookDao {

    /**
     * 查询所有的book数据
     * @return
     */
    List<Book> queryBookList() throws Exception;
}
