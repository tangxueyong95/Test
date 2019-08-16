package com.changgou.controller;

import com.changgou.service.PageService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.controller
 ****/
@RestController
@CrossOrigin
@RequestMapping(value = "/page")
public class PageController {

    @Autowired
    private PageService pageService;

    /****
     * 生成静态页
     */
    @RequestMapping(value = "/create/{id}")
    public Result createPage(@PathVariable(value = "id")Long supId) throws Exception{
        //生成静态页
        pageService.createHtml(supId);
        return new Result(true, StatusCode.OK,"生成静态页成功！");
    }
}
