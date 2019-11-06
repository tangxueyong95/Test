package com.java.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.domain.Article;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/21 8:43
 * @Description: com.java.test
 ****/
public class ElasticsearchTest {

    /****
     * 创建索引实现
     */
    @Test
    public void testCreateIndexDemo1() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //创建索引库对象  .admin():管理当前索引库对象
        client.admin().indices().prepareCreate("blog4").get();

        //关闭资源
        client.close();
    }


    /****
     * 删除索引实现
     */
    @Test
    public void testDeleteIndexDemo2() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //创建索引库对象  .admin():管理当前索引库对象
        client.admin().indices().prepareDelete("blog4").get();

        //关闭资源
        client.close();
    }

    /****
     * 创建映射关系
     * 无法创建相同的映射关系
     */
    @Test
    public void testCreateMappingDemo3() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

//        client.admin().indices().prepareDelete("blog4").get();
        //创建索引库对象  .admin():管理当前索引库对象
        client.admin().indices().prepareCreate("blog5").get();

        //创建映射关系
        XContentBuilder builder = XContentFactory.jsonBuilder();
        //start->end 一对
        builder.startObject()
                .startObject("article")//固定
                .startObject("properties")//固定

                .startObject("id")
                .field("type", "long")
                .endObject()

                .startObject("title")
                .field("type", "string")
                .field("store", "false")     //是否存储->false
                .field("analyzer", "ik_smart")
                .endObject()

                .startObject("content")
                .field("type", "string")
                .field("store", "false")     //是否存储->false
                .field("analyzer", "ik_smart")
                .endObject()

                .endObject()
                .endObject()
                .endObject();

        //将映射关系封装到Requests里面去
        PutMappingRequest mappingRequest = Requests.putMappingRequest("blog4").type("article").source(builder);
        //创建映射
        client.admin().indices().putMapping(mappingRequest);

        //关闭资源
        client.close();
    }


    /****
     * 创建索引记录  Document文档
     */
    @Test
    public void testCreateDocumentDemo4() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //组装数据  XContentBuilder
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        builder.field("id", "1");
        builder.field("title", "ElasticSearch是一个基于Lucene的搜索服务器,深圳黑马训练营！");
        builder.field("content", "它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。");
        builder.endObject();

        //将数据对象添加到索引库中->添加一条索引记录
        client.prepareIndex("blog4", "article", "1").setSource(builder).get();

        //关闭资源
        client.close();
    }


    /****
     * 创建索引记录  Document文档
     * 使用JSON方式实现
     */
    @Test
    public void testCreateJSONDocumentDemo5() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //组装数据 Article
        Article article = new Article();
        article.setId(2);
        article.setTitle("ElasticSearch是一个基于Lucene的搜索服务器,深圳黑马训练营！欢迎来到深圳大黑马!");
        article.setContent("它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。");

        //ObjectMapper 实现JSON与JavaBean对象互转
        ObjectMapper objectMapper = new ObjectMapper();

        //将数据对象添加到索引库中->添加一条索引记录
        client.prepareIndex("blog4", "article", article.getId().toString()).setSource(
                objectMapper.writeValueAsString(article).getBytes(),//当前JavaBean对象转成JSON对象，并获取字节数组
                XContentType.JSON       //指定数据是JSON类型
        ).get();

        //关闭资源
        client.close();
    }


    /*****
     * 修改数据-方式一  prepareUpdate
     */
    @Test
    public void testUpdateDocumentDemo6() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //组装数据 Article
        Article article = new Article();
        article.setId(2);
        article.setTitle("ElasticSearch是一个基于Lucene的搜索服务器!JavaEE64  66666！");
        article.setContent("JavaEE64  66666！它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。");

        //ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        //prepareUpdate修改
        client.prepareUpdate("blog4", "article", article.getId().toString()).setDoc(
                objectMapper.writeValueAsString(article).getBytes(),//修改的数据的JSON字节数组
                XContentType.JSON   //数据的类型
        ).get();

        //关闭资源
        client.close();
    }


    /****
     * 修改数据-方式二 update
     */
    @Test
    public void testUpdateDocumentDemo7() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //组装数据 Article
        Article article = new Article();
        article.setId(2);
        article.setTitle("ElasticSearch是一个基于Lucene的搜索服务器!JavaEE64  66666！深圳黑马欢迎你！");
        article.setContent("JavaEE64  66666！它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。");

        //ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        //update修改
        client.update(new UpdateRequest("blog4", "article", article.getId().toString()).doc(
                objectMapper.writeValueAsString(article).getBytes(),//修改的数据的JSON字节数组
                XContentType.JSON   //数据的类型
        )).get();

        //关闭资源
        client.close();
    }


    /***
     * 删除索引记录-方式一  prepareDelete
     */
    @Test
    public void testDeleteDocumentDemo8() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //删除指定记录
        client.prepareDelete("blog4", "article", "2").get();

        //关闭资源
        client.close();
    }


    /***
     * 删除索引记录-方式一  delete
     */
    @Test
    public void testDeleteDocumentDemo9() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //删除指定记录
        client.delete(new DeleteRequest("blog4", "article", "1")).get();

        //关闭资源
        client.close();
    }


    /***
     * 批量增加数据
     */
    @Test
    public void testBatchInsertDemo10() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        //创建BulkRequestBuiler
        BulkRequestBuilder builder = client.prepareBulk();

        for (int i = 0; i < 100; i++) {
            //组装数据 Article
            Article article = new Article();
            article.setId(i + 1);
            article.setTitle((Math.random() * 10000) + "ElasticSearch是一个基于Lucene的搜索服务器!JavaEE64  66666！深圳黑马欢迎你！");
            article.setContent((Math.random() * 10000) + "JavaEE64  66666！它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。");

            //将每次要操作的数据对象添加到BulkRequestBuilder对象中
            builder.add(
                    client.prepareIndex("blog4", "article", article.getId().toString()).setSource(
                            objectMapper.writeValueAsString(article).getBytes(),    //要添加的记录的JSON数据字节数组
                            XContentType.JSON       //指定数据类型
                    )
            );
        }
        //执行批量操作
        builder.execute().actionGet();

        //关闭资源
        client.close();
    }


    /***
     * 字符串查询操作
     */
    @Test
    public void testQueryStringQueryDemo11() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //搜索：prepareSearch
        SearchResponse response = client.prepareSearch("blog4")
                .setTypes("article")
                .setQuery(QueryBuilders.queryStringQuery("搜索").field("title")).get();

        //解析数据
        SearchHits hits = response.getHits();
        //总记录数
        System.out.println("总记录数："+hits.getTotalHits());
        //结果集
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            //获取当前对象
            SearchHit hit = iterator.next();
            System.out.println(hit.getSource().get("title"));
            System.out.println(hit.getSourceAsString());
        }

        //关闭资源
        client.close();
    }


    /***
     * 词条查询
     */
    @Test
    public void testTermQueryDemo12() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //搜索：prepareSearch
        SearchResponse response = client.prepareSearch("blog4")
                .setTypes("article")
                .setQuery(QueryBuilders.termQuery("title","服务器")).get();

        //解析数据
        SearchHits hits = response.getHits();
        //总记录数
        System.out.println("总记录数："+hits.getTotalHits());
        //结果集
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            //获取当前对象
            SearchHit hit = iterator.next();
            System.out.println(hit.getSource().get("title"));
            System.out.println(hit.getSourceAsString());
        }

        //关闭资源
        client.close();
    }


    /***
     * 词条查询，将结果集(JSON)转成JavaBean
     */
    @Test
    public void testTermQueryJavaBeanDemo13() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //搜索：prepareSearch
        SearchResponse response = client.prepareSearch("blog4")
                .setTypes("article")
                .setQuery(QueryBuilders.termQuery("title","服务器")).get();
        //ObjectMapper->JSON<->JavaBean
        ObjectMapper objectMapper = new ObjectMapper();
        //解析数据
        SearchHits hits = response.getHits();
        //总记录数
        System.out.println("总记录数："+hits.getTotalHits());

        //集合存储数据结果集
        List<Article> articles = new ArrayList<Article>();

        //结果集
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            //获取当前对象
            SearchHit hit = iterator.next();
           //获取JSON数据
            String json = hit.getSourceAsString();
            //JavaBean
            Article article = objectMapper.readValue(json,Article.class);
            articles.add(article);
        }

        //循环输出数据
        for (Article article : articles) {
            System.out.println(article);
        }

        //关闭资源
        client.close();
    }


    /***
     * 多种查询实现
     */
    @Test
    public void testQueryDemo14() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //搜索：prepareSearch
        SearchResponse response = client.prepareSearch("blog4")
                .setTypes("article")
                //词条查询
                .setQuery(QueryBuilders.termQuery("title","服务器")
                //通配符查询
                //.setQuery(QueryBuilders.wildcardQuery("content","*Elasticsearch*".toLowerCase())
                //范围查询：查询ID为10-20之间的数据
                //.setQuery(QueryBuilders.rangeQuery("id").from(10).to(20)
                //相似度匹配
                //.setQuery(QueryBuilders.fuzzyQuery("content","elasticyearch")
                ).get();
        //ObjectMapper->JSON<->JavaBean
        ObjectMapper objectMapper = new ObjectMapper();
        //解析数据
        SearchHits hits = response.getHits();
        //总记录数
        System.out.println("总记录数："+hits.getTotalHits());

        //集合存储数据结果集
        List<Article> articles = new ArrayList<Article>();

        //结果集
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            //获取当前对象
            SearchHit hit = iterator.next();
            //获取JSON数据
            String json = hit.getSourceAsString();
            //JavaBean
            Article article = objectMapper.readValue(json,Article.class);
            articles.add(article);
        }

        //循环输出数据
        for (Article article : articles) {
            System.out.println(article);
        }

        //关闭资源
        client.close();
    }



    /***
     * 组合条件查询
     * BoolQuery
     */
    @Test
    public void testBoolQueryDemo14() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //搜索：prepareSearch
        SearchResponse response = client.prepareSearch("blog4")
                .setTypes("article")
                //词条查询
                .setQuery(
                        //2个条件同时成立
                        //title中包含 搜索服务  termQuery
                        //id的范围：75-99之间  rangeQuery
                        //QueryBuilders.boolQuery()：合并多个条件
                        QueryBuilders.boolQuery()
                                .must(QueryBuilders.termQuery("title","服务器"))
                                .must(QueryBuilders.rangeQuery("id").from(75).to(99))
                ).get();
        //ObjectMapper->JSON<->JavaBean
        ObjectMapper objectMapper = new ObjectMapper();
        //解析数据
        SearchHits hits = response.getHits();
        //总记录数
        System.out.println("总记录数："+hits.getTotalHits());

        //集合存储数据结果集
        List<Article> articles = new ArrayList<Article>();

        //结果集
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            //获取当前对象
            SearchHit hit = iterator.next();
            //获取JSON数据
            String json = hit.getSourceAsString();
            //JavaBean
            Article article = objectMapper.readValue(json,Article.class);
            articles.add(article);
        }

        //循环输出数据
        for (Article article : articles) {
            System.out.println(article);
        }

        //关闭资源
        client.close();
    }



    /***
     * 组合条件查询
     * BoolQuery
     * 分页
     * 排序
     */
    @Test
    public void testBoolQueryPageSortDemo14() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //搜索：prepareSearch
        SearchResponse response = client.prepareSearch("blog4")
                .setTypes("article")
                //词条查询
                .setQuery(
                        //2个条件同时成立
                        //title中包含 搜索服务  termQuery
                        //id的范围：75-99之间  rangeQuery
                        //QueryBuilders.boolQuery()：合并多个条件
                        QueryBuilders.boolQuery()
                                .must(QueryBuilders.termQuery("title","服务器"))
                                .must(QueryBuilders.rangeQuery("id").from(75).to(99))
                )
                //分页设置
                .setFrom(13)     //从第N条记录查询  0:下标
                .setSize(13)    //每次查询的条数
                .addSort("id", SortOrder.DESC)  //降序
                .get();
        //ObjectMapper->JSON<->JavaBean
        ObjectMapper objectMapper = new ObjectMapper();
        //解析数据
        SearchHits hits = response.getHits();
        //总记录数
        System.out.println("总记录数："+hits.getTotalHits());

        //集合存储数据结果集
        List<Article> articles = new ArrayList<Article>();

        //结果集
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            //获取当前对象
            SearchHit hit = iterator.next();
            //获取JSON数据
            String json = hit.getSourceAsString();
            //JavaBean
            Article article = objectMapper.readValue(json,Article.class);
            articles.add(article);
        }

        //循环输出数据
        for (Article article : articles) {
            System.out.println(article);
        }

        //关闭资源
        client.close();
    }



    /***
     * 高亮搜索实现
     */
    @Test
    public void testQueryHighlightDemo14() throws Exception {
        //创建TransportClient
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //指定IP和端口
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        //搜索：prepareSearch
        SearchRequestBuilder builder = client.prepareSearch("blog4")
                .setTypes("article")
                //词条查询
                .setQuery(QueryBuilders.termQuery("title", "服务器"));

        //配置高亮信息->开启高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");    //指定高亮域
        highlightBuilder.preTags("<em style='color:red;'>");    //前缀
        highlightBuilder.postTags("</em>");     //后缀
        builder.highlighter(highlightBuilder);

        //查询数据
        SearchResponse response = builder.get();

        //ObjectMapper->JSON<->JavaBean
        ObjectMapper objectMapper = new ObjectMapper();
        //解析数据
        SearchHits hits = response.getHits();
        //总记录数
        System.out.println("总记录数："+hits.getTotalHits());

        //集合存储数据结果集
        List<Article> articles = new ArrayList<Article>();

        //结果集
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            //获取当前对象
            SearchHit hit = iterator.next();
            //获取JSON数据
            String json = hit.getSourceAsString();
            //JavaBean
            Article article = objectMapper.readValue(json,Article.class);
            //获取高亮数据
            Text[] hltiles = hit.getHighlightFields().get("title").getFragments();
            //高亮数据
            String hlstr = "";
            for (Text hltile : hltiles) {
                hlstr+=hltile.toString();
            }
            //替换高亮数据
            article.setTitle(hlstr);

            articles.add(article);
        }

        //循环输出数据
        for (Article article : articles) {
            System.out.println(article);
        }

        //关闭资源
        client.close();
    }
}
