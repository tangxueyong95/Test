package com.itheima.domain;

import java.io.Serializable;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/21 9:14
 * @Description: com.itheima.domain
 ****/
public class Article implements Serializable {

    private Integer id;
    private String title;
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
