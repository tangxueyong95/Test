package com.java.service.impl;

import com.java.dao.ArticleDao;
import com.java.domain.Article;
import com.java.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/21 11:42
 * @Description: com.java.service.impl
 ****/
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /***
     * 根据标题查询
     * @param title
     * @return
     */
    @Override
    public List<Article> findByTitle(String title) {
        return articleDao.findByTitle(title);
    }

    /***
     * 根据标题分页查询
     * @param title
     * @param pageable
     * @return
     */
    @Override
    public Page<Article> findByTitle(String title, Pageable pageable) {
        return articleDao.findByTitle(title,pageable);
    }

    /***
     * 增加索引数据
     * @param article
     */
    @Override
    public void save(Article article) {
        articleDao.save(article);
    }

    /***
     * 批量增加
     * @param articles
     */
    @Override
    public void saveAll(List<Article> articles) {
        articleDao.saveAll(articles);
    }

    /***
     * 根据ID删除
     * @param article
     */
    @Override
    public void delete(Article article) {
        articleDao.delete(article);
    }

    /***
     * 查询所有
     * @return
     */
    @Override
    public Iterable<Article> findAll() {
        return articleDao.findAll();
    }

    /***
     * 分页查询
     * @param pageable：分页封装对象
     * @return
     */
    @Override
    public Page<Article> findAll(Pageable pageable) {
        return articleDao.findAll(pageable);
    }
}
