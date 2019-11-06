package com.java.test;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/***
*实现索引的增加(2种方式JSON/Map->JSON)、查询(根据ID查询、查询所有、搜索数据[字符搜索、词条搜索、通配符匹配])
 */
public class ElasticsearchTest {

    /***
     * 创建索引信息->JSON操作
     */
    @Test
    public void testCreateJsonDemo1() throws Exception{
        /***
         * 1.创建TransportClient对象->1)指定IP  2）指定TCP端口
         * Settings.EMPTY:设置是否集群，EMPTY：不集群
         */
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP  2）指定TCP端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));

        //2.构建数据XContentBuilder，构建数据节点->Document对象->Field域
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject();//开始构建节点对象
        builder.field("id",1);
        builder.field("title","ElasticSearch是一个基于Lucene的搜索服务器。");
        builder.field("content","它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。");
        builder.endObject();

        //3.使用TransportClient对象，将构建的数据增加到ES索引库去  id:唯一标识,相当于数据库表的主键
        client.prepareIndex("blog","article","1").setSource(builder).get(); //get相当于执行操作

        //4.回收资源
        client.close();
    }


    /***
     * 创建索引信息(Map)->JSON操作
     */
    @Test
    public void testCreateMapDemo2() throws Exception{
        /***
         * 1.创建TransportClient对象->1)指定IP  2）指定TCP端口
         * Settings.EMPTY:设置是否集群，EMPTY：不集群
         */
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP  2）指定TCP端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));

        //2.构建一个Map
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",2);
        map.put("title","2-ElasticSearch是一个基于Lucene的搜索服务器-javaee646666");
        map.put("content","2-它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。");

        //3.使用TransportClient对象，将构建的数据增加到ES索引库去  id:唯一标识,相当于数据库表的主键
        client.prepareIndex("blog","article","2").setSource(map).get(); //get相当于执行操作

        //4.回收资源
        client.close();
    }


    /***
     * 根据ID查询数据
     * 不走索引域
     */
    @Test
    public void testQueryById() throws Exception{
        /***
         * 1.创建TransportClient对象->1)指定IP  2）指定TCP端口
         * Settings.EMPTY:设置是否集群，EMPTY：不集群
         */
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP  2）指定TCP端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));

        //查询ID=2的数据
        String id="2";
        GetResponse response = client.prepareGet("blog", "article", id).get();
        //获取数据-->JSON
        String result = response.getSourceAsString();
        System.out.println(result);

        //关闭资源
        client.close();
    }


    /***
     * 查询所有
     * 不走索引域
     */
    @Test
    public void testQueryAll() throws Exception{
        /***
         * 1.创建TransportClient对象->1)指定IP  2）指定TCP端口
         * Settings.EMPTY:设置是否集群，EMPTY：不集群
         */
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP  2）指定TCP端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));

        //查询所有
        SearchResponse response = client.prepareSearch("blog")  //设置索引对象--->数据库
                .setTypes("article")       //设置类型--->表
                .setQuery(QueryBuilders.matchAllQuery())     //设置搜索条件  QueryBuilders.matchAllQuery():查询所有
                .get();

        //获取命中(找到)的数据
        SearchHits hits = response.getHits();
        //总记录数
        long totalHits = hits.getTotalHits();

        //获取数据结果集
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            //获取下一条数据
            SearchHit hit = iterator.next();
            //获取指定域的数据
            String title = hit.getSource().get("title").toString();

            //获取所有数据
            String result = hit.getSourceAsString();
            System.out.println(title);
            System.out.println(result);
        }

        //关闭资源
        client.close();
    }



    /***
     * 字符串搜索
     * 走索引域
     */
    @Test
    public void testQueryStringQuery() throws Exception{
        /***
         * 1.创建TransportClient对象->1)指定IP  2）指定TCP端口
         * Settings.EMPTY:设置是否集群，EMPTY：不集群
         */
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP  2）指定TCP端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));

        //查询所有
        SearchResponse response = client.prepareSearch("blog")  //设置索引对象--->数据库
                .setTypes("article")       //设置类型--->表
                //设置搜索条件  QueryBuilders.queryStringQuery("搜索"):搜索关键词 搜索，默认搜索所有域,.field("title"):指定搜索title域
                //关键词：搜索--->分词:搜、索
                 .setQuery(QueryBuilders.queryStringQuery("搜索").field("title"))
                .get();

        //获取命中(找到)的数据
        SearchHits hits = response.getHits();
        //总记录数
        long totalHits = hits.getTotalHits();

        //获取数据结果集
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            //获取下一条数据
            SearchHit hit = iterator.next();
            //获取指定域的数据
            String title = hit.getSource().get("title").toString();

            //获取所有数据
            String result = hit.getSourceAsString();
            System.out.println(title);
            System.out.println(result);
        }

        //关闭资源
        client.close();
    }

    /***
     * 词项搜索
     * 走索引域
     */
    @Test
    public void testQueryTerm() throws Exception{
        /***
         * 1.创建TransportClient对象->1)指定IP  2）指定TCP端口
         * Settings.EMPTY:设置是否集群，EMPTY：不集群
         */
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP  2）指定TCP端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));

        //查询所有
        SearchResponse response = client.prepareSearch("blog")  //设置索引对象--->数据库
                .setTypes("article")       //设置类型--->表
                //设置搜索条件
                //QueryBuilders.termQuery("title","搜索"):词项搜索-->分词中需要有 【搜索】关键词
                .setQuery(QueryBuilders.termQuery("title","搜索"))
                .get();

        //获取命中(找到)的数据
        SearchHits hits = response.getHits();
        //总记录数
        long totalHits = hits.getTotalHits();

        //获取数据结果集
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            //获取下一条数据
            SearchHit hit = iterator.next();
            //获取指定域的数据
            String title = hit.getSource().get("title").toString();

            //获取所有数据
            String result = hit.getSourceAsString();
            System.out.println(title);
            System.out.println(result);
        }

        //关闭资源
        client.close();
    }



    /***
     * 模糊搜索
     * 走索引域
     */
    @Test
    public void testQueryWildWord() throws Exception{
        /***
         * 1.创建TransportClient对象->1)指定IP  2）指定TCP端口
         * Settings.EMPTY:设置是否集群，EMPTY：不集群
         */
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP  2）指定TCP端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));

        //查询所有
        SearchResponse response = client.prepareSearch("blog")  //设置索引对象--->数据库
                .setTypes("article")       //设置类型--->表
                //设置搜索条件
                .setQuery(QueryBuilders.wildcardQuery("title","*搜索*"))
                .get();

        //获取命中(找到)的数据
        SearchHits hits = response.getHits();
        //总记录数
        long totalHits = hits.getTotalHits();

        //获取数据结果集
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            //获取下一条数据
            SearchHit hit = iterator.next();
            //获取指定域的数据
            String title = hit.getSource().get("title").toString();

            //获取所有数据
            String result = hit.getSourceAsString();
            System.out.println(title);
            System.out.println(result);
        }

        //关闭资源
        client.close();
    }
}