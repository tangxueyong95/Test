package com.itheima.service;

import com.itheima.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/21 11:42
 * @Description: com.itheima.service
 ****/
public interface ArticleService {

    /***
     * 根据标题查询
     * @param title
     * @return
     */
    List<Article> findByTitle(String title);

    /***
     * 根据标题分页查询
     * @param title
     * @param pageable
     * @return
     */
    Page<Article> findByTitle(String title,Pageable pageable);

    /***
     * 增加索引数据
     * @param article
     */
    void save(Article article);

    /**
     * 批量保存
     * @param articles
     */
    void saveAll(List<Article> articles);

    /**
     * 根据ID删除数据
     * @param article
     */
    void delete(Article article);

    /**
     * 查询所有
     * @return
     */
    Iterable<Article> findAll();

    /**
     * 分页查询
     * @param pageable：分页封装对象
     * @return
     */
    Page<Article> findAll(Pageable pageable);

}
