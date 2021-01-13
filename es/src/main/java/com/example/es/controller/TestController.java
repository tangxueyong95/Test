package com.example.es.controller;


import com.example.es.pojo.TestBean;
import com.example.es.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/testes")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping("findAll")
    public Iterable<TestBean> findAll() {

        return testService.findAll();
    }

    @RequestMapping("list")
    public String save() {
        testService.save();
        return "success";
    }

    @RequestMapping("save")
    public String saveOne(@RequestParam TestBean bean) {
        testService.saveOne(bean);
        return "success";
    }

    @RequestMapping("findByName")
    public List<TestBean> findByName(@RequestParam String name) {
        return testService.findByName(name);
    }

    @RequestMapping("queryByage")
    public List<TestBean> queryByage(@RequestParam String age) {
        return testService.queryByage(age);
    }

    @RequestMapping("findByDescLike")
    public List<TestBean> findByDescLike(@RequestParam String desc) {
        return testService.findByDescLike(desc);
    }

    @RequestMapping("findByTestBean")
    public List<Map<String,Object>> findByTestBean(TestBean testBean) {
        return testService.findByTestBean(testBean);
    }

    @RequestMapping("findByDesc")
    public List<TestBean> findByDesc(@RequestParam String desc) {
        return testService.findByDesc(desc);
    }

    @RequestMapping("highlighted")
    public List<Map<String, Object>> highlighted(@RequestParam String desc) {
        return testService.highlighted(desc);
    }

    @RequestMapping("findByAgeAndName")
    public List<TestBean> findByAgeAndName(@RequestParam Integer age,@RequestParam String name) {
        return testService.findByAgeAndName(age,name);
    }
    @RequestMapping("findByAgeOrName")
    public List<TestBean> findByAgeOrName(Integer age,String name) {
        return testService.findByAgeOrName(age,name);
    }
}
