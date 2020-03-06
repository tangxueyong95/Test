package com.java.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/18 10:57
 * @Description: com.java.test
 *  搜索测试
 ****/
public class SearchTest {

    /***
     * 搜索数据
     */
    @Test
    public void testSearch() throws Exception{
        //分词器
        //Analyzer analyzer = new StandardAnalyzer();
        //IK分词器
        Analyzer analyzer = new IKAnalyzer();

        //创建Query搜索对象(搜索条件封装)  第1个参数：默认搜索域配置   第2个参数：分词器配置
        QueryParser queryParser = new QueryParser("desc",analyzer);
        Query query = queryParser.parse("name:美国人");

        //创建Directory流对象,声明索引库位置
        Directory directory = FSDirectory.open(new File("D:/index").toPath());

        //创建索引读取对象IndexReader
        IndexReader indexReader= DirectoryReader.open(directory);

        //创建索引搜索对象IndexSearcher
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //使用索引搜索对象，执行搜索，返回结果集TopDocs
        //第1个参数：搜索条件   第2个参数：搜索前N条记录
        TopDocs topDocs = indexSearcher.search(query, 5);       //去索引域中搜索
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        //解析结果集
        for (ScoreDoc scoreDoc : scoreDocs) {
            //获取下标
            int number = scoreDoc.doc;

            //根据下标找文档完整数据
            Document document = indexSearcher.doc(number);

            System.out.println(document.get("name"));
            System.out.println(document.get("pic"));
            System.out.println("====================");
        }

        //释放资源
        indexReader.close();
    }

}
