package com.changgou.search.controller;

import com.changgou.search.service.SkuService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.search.controller
 ****/
@RestController
@RequestMapping(value = "/search")
@CrossOrigin
public class SkuController {

    @Autowired
    private SkuService skuService;

    /****
     * 搜索实现
     */
    @GetMapping
    public Map search(@RequestParam(required = false)Map<String,String> searchMap){
        return skuService.searhc(searchMap);
    }

    /***
     * 查询Sku数据，导入到ES索引库
     * @return
     */
    @GetMapping(value = "/import")
    public Result importSku(){
        //导入
        skuService.importSku();
        return new Result(true, StatusCode.OK,"导入数据成功！");
    }
}
