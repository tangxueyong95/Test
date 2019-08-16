package com.itheima.test;

import com.itheima.domain.Article;
import com.itheima.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/21 11:51
 * @Description: com.itheima.test
 ****/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springdata-cluster.xml"})
public class SpringDataElasticSearchClusterTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ArticleService articleService;

    /****
     * 创建索引和映射
     */
    @Test
    public void testCreateIndexMapping(){
        //创建索引
        elasticsearchTemplate.createIndex(Article.class);
        //创建映射关系
        elasticsearchTemplate.putMapping(Article.class);
    }

    /***
     * 增加数据
     */
    @Test
    public void testSave(){
        Article article = new Article();
        article.setId(100);
        article.setTitle("测试SpringData ElasticSearch");
        article.setContent("Spring Data ElasticSearch 基于 spring data API 简化 elasticSearch操作，将原始操作elasticSearch的客户端API 进行封装Spring Data为Elasticsearch Elasticsearch项目提供集成搜索引擎");
        articleService.save(article);
    }


    /***
     * 批量保存
     */
    @Test
    public void testSaveAll(){
        //存储集合数据
        List<Article> articles = new ArrayList<Article>();
        for (int i = 0; i <100 ; i++) {
            Article article = new Article();
            article.setId(i+1);
            article.setTitle(Math.random()*1000+"测试SpringData ElasticSearch");
            article.setContent(Math.random()*1000+"Spring Data ElasticSearch 基于 spring data API 简化 elasticSearch操作，将原始操作elasticSearch的客户端API 进行封装Spring Data为Elasticsearch Elasticsearch项目提供集成搜索引擎");
            articles.add(article);
        }
        //批量保存
        articleService.saveAll(articles);
    }

    /****
     * 根据ID删除
     */
    @Test
    public void testDelete(){
        Article article = new Article();
        article.setId(14);
        //删除
        articleService.delete(article);
    }

    /***
     * 查询所有
     */
    @Test
    public void testFindAlll(){
        //查询集合数据
        Iterable<Article> iterable = articleService.findAll();
        for (Article article : iterable) {
            System.out.println(article);
        }
    }


    /***
     * 分页查询
     */
    @Test
    public void testFindAlllPage(){
        //分页查询集合数据
        Page<Article> page = articleService.findAll(PageRequest.of(0, 10));

        //总页数
        System.out.println("总页数："+page.getTotalPages());

        //总记录
        System.out.println("总数量："+page.getTotalElements());

        //集合数据
        List<Article> articles = page.getContent();
        for (Article article : articles) {
            System.out.println(article);
        }

    }


    /***
     * 命名规则查询数据
     * 查询所有
     */
    @Test
    public void testFindByTitle(){
        //查询集合数据
        List<Article> articles = articleService.findByTitle("测试");
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    /***
     * 命名规则查询数据
     * 分页查询
     */
    @Test
    public void testFindByTitlePage(){
        //查询集合数据
        Page<Article> page = articleService.findByTitle("测试", PageRequest.of(0, 12));
        List<Article> articles = page.getContent();
        for (Article article : articles) {
            System.out.println(article);
        }
    }

}
