package com.java.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/18 12:15
 * @Description: com.java.test
 * 修改索引
 ****/
public class UpdateIndexTest {


    /****
     * 修改索引
     */
    @Test
    public void testUpdate() throws Exception{
        //使用IK分词器
        Analyzer analyzer = new IKAnalyzer();

        //创建IndexWriterConfig配置信息类
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        //创建Directory对象，声明索引库存储位置
        Directory directory = FSDirectory.open(new File("D:/index").toPath());

        //创建IndexWriter写入对象
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);

        //name:美国人
        //update table set name=? ,age=? where name=美国人
        //terms：词项，分词   Term("指定条件判断的域","判断的条件")   name:美国人
        Document document = new Document();
        //不分词、创建索引、存储
        document.add(new StringField("id","56789", Field.Store.YES));
        //分词、创建索引、存储
        document.add(new TextField("name","美国的空气太难闻了！", Field.Store.YES));
        //不分词(数字分词)、创建索引、存储
        document.add(new FloatField("price",-10, Field.Store.YES));
        //修改操作
        indexWriter.updateDocument(new Term("name","美国人"),document);
        //提交
        indexWriter.commit();
        //关闭
        indexWriter.close();
    }
}
