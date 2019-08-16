package com.itheima.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/18 12:15
 * @Description: com.itheima.test
 * 删除索引
 ****/
public class DeleteIndexTest {


    /****
     * 删除索引
     */
    @Test
    public void testDelete() throws Exception{
        //使用IK分词器
        Analyzer analyzer = new IKAnalyzer();

        //创建IndexWriterConfig配置信息类
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        //创建Directory对象，声明索引库存储位置
        Directory directory = FSDirectory.open(new File("D:/index").toPath());

        //创建IndexWriter写入对象
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);

        //name:美国人
        //delete from table where name=美国人
        //terms：词项，分词   Term("指定条件判断的域","判断的条件")   name:美国人
        //indexWriter.deleteDocuments(new Term("name","美国人"));

        //删除所有
        indexWriter.deleteAll();

        //提交
        indexWriter.commit();
        //关闭
        indexWriter.close();
    }
}
