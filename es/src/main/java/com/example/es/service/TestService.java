package com.example.es.service;

import com.example.es.pojo.TestBean;

import java.util.List;
import java.util.Map;

public interface TestService {
    Iterable<TestBean> findAll();

    void save();

    void saveOne(TestBean bean);

    List<TestBean> findByName(String text);

    List<TestBean> queryByage(String age);

    List<TestBean> findByAgeAndName(Integer age,String name);
    List<TestBean> findByAgeOrName(Integer age,String name);

    List<TestBean> findByDescLike(String desc);
    List<Map<String,Object>> findByTestBean(TestBean testBean);

    List<TestBean> findByDesc(String desc);

    List<Map<String, Object>> highlighted(String desc);
}
