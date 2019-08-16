package com.itheima.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/21 9:14
 * @Description: com.itheima.domain
 ****/
@Document(indexName = "blog4",type = "article")
public class Article implements Serializable {

    //@Id唯一标识
    @Id
    private Integer id;
    //analyzer = "ik_smart":增加索引数据时使用的分词器
    //searchAnalyzer = "ik_smart":搜索时使用的分词器
    //type = FieldType.Text:文本类型，支持分词
    @Field(store = false,index = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",type = FieldType.Text)
    private String title;
    @Field(store = false,index = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",type = FieldType.Text)
    private String content;

    @Override
    public String toString() {
        return "Artilce{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
