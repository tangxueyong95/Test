package com.changgou.item.feign;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.item.feign
 ****/
@FeignClient(value = "item")
@RequestMapping(value = "/page")
public interface PageFeign {

    /****
     * 生成静态页
     */
    @RequestMapping(value = "/create/{id}")
    Result createPage(@PathVariable(value = "id")Long supId) throws Exception;
}
