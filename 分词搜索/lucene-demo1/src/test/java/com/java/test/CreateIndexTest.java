package com.java.test;

import com.java.dao.BookDao;
import com.java.dao.impl.BookDaoImpl;
import com.java.domain.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/18 10:00
 * @Description: com.java.test
 *      测试：创建索引数据
 ****/
public class CreateIndexTest {

    private BookDao bookDao = new BookDaoImpl();

    /****
     * 测试创建索引
     */
    @Test
    public void testCreateIndex() throws Exception{
        //采集数据
        List<Book> books = bookDao.queryBookList();

        //创建[Document]文档对象
        List<Document> documents   = new ArrayList<Document>();

        for (Book book : books) {
            Document document = new Document();
            //不分词、创建索引、存储
            //document.add(new TextField("id",book.getId().toString(), Field.Store.YES));
            document.add(new StringField("id",book.getId().toString(), Field.Store.YES));

            //分词、创建索引、存储
            document.add(new TextField("name",book.getName(), Field.Store.YES));

            //不分词、创建索引、存储
            document.add(new StringField("price",book.getPrice().toString(),Field.Store.YES));

            //不分词(数字分词)、创建索引、存储
            //document.add(new TextField("price",book.getPrice().toString(), Field.Store.YES));
//            document.add(new FloatField("price",book.getPrice(), Field.Store.YES));

            //不分词、不创建索引、存储
            //document.add(new TextField("pic",book.getPic(), Field.Store.YES));
            document.add(new StoredField("pic",book.getPic()));

            //分词、创建索引、存储
            document.add(new TextField("desc",book.getDesc(),Field.Store.YES));

//            //分词、创建索引、不存在
//            document.add(new TextField("desc",book.getDesc(), Field.Store.NO));
            documents.add(document);
        }

        //创建分析器（分词器）
        //Analyzer analyzer = new StandardAnalyzer();
        //使用IK分词器
        Analyzer analyzer = new IKAnalyzer(); //默认打印扩展词典和扩展停止词典

        //创建IndexWriterConfig配置信息类
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        //创建Directory对象，声明索引库存储位置
        Directory directory = FSDirectory.open(new File("D:\\Program Files\\luke-5.3.0-luke-release/index").toPath());

        //创建IndexWriter写入对象
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);

        //把Document写入到索引库中
        indexWriter.addDocuments(documents);
        indexWriter.commit();
        //释放资源
        indexWriter.close();
    }

    /***
     * 创建索引测试
     */
    @Test
    public void testCreateIndexDemo1() throws Exception{
        //查询数据库
        Book book = new Book();
        book.setId(1);
        book.setName("百科全书 金蝶");
        book.setDesc("百科全书真好啊！mybatis");
        book.setPic("http://1.jpg");
        book.setPrice(998f);

        //1.[Document]  其实就是相当于Lucene索引库所对应的一个JavaBean，和数据库中表对应的JavaBean意义是一样的
        Document document = new Document();
        //域->理解成列|属性
        document.add(new TextField("id",book.getId().toString(), Field.Store.YES));
        document.add(new TextField("name",book.getName(), Field.Store.YES));
        document.add(new TextField("price",book.getPrice().toString(), Field.Store.YES));
        document.add(new TextField("pic",book.getPic(), Field.Store.YES));
        document.add(new TextField("desc",book.getDesc(), Field.Store.YES));
        //分词器
        Analyzer analyzer = new StandardAnalyzer();
        //2.[IndexWriterConfig] 写文件的参数优化配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        //3.[Directory]索引存储的位置，索引库
        File file = new File("D:\\Program Files\\luke-5.3.0-luke-release/index");
        Directory directory = FSDirectory.open(file.toPath());

        //4.[IndexWriter],操作索引库的对象，增加索引库，修改索引库，删除索引库
        //第1个参数：写入到指定的索引库去(写入到指定目录去)
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
        //增加数据
        indexWriter.addDocument(document);
        //提交数据
        indexWriter.commit();
    }
}
