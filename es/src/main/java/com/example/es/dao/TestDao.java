package com.example.es.dao;
import com.example.es.pojo.TestBean;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TestDao extends CrudRepository<TestBean, Long> {
    List<TestBean> findByName(String name);

    @Query("{\n" +
            "    \"match\": {\n" +
            "      \"age\": {\n" +
            "        \"query\": \"?0\"\n" +
            "      }\n" +
            "    }\n" +
            "  }")
    List<TestBean> queryByage(String age);

    List<TestBean> findByAgeAndName(Integer age,String name);

    //无法实现2选1查询
    List<TestBean> findByAgeOrName(Integer age,String name);

    List<TestBean> findByDescLike(String desc);

    @Highlight(fields = {
            @HighlightField(name = "desc")
    }, parameters = @HighlightParameters(
            preTags = "<font color='red'>",
            postTags = "</font>"
    ))
    List<SearchHit<TestBean>> findByDesc(String Desc);
}