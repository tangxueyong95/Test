package com.itheima.dao.es;

import com.itheima.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/21 11:40
 * @Description: com.itheima.dao.es
 ****/
public interface ArticleDao extends ElasticsearchRepository<Article,Integer> {
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
    Page<Article> findByTitle(String title, Pageable pageable);
}
