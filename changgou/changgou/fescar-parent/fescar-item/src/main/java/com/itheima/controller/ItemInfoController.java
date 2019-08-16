package com.itheima.controller;

import com.itheima.service.ItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima.controller
 ****/
@RestController
@RequestMapping(value = "/itemInfo")
@CrossOrigin
public class ItemInfoController {


    @Autowired
    private ItemInfoService itemInfoService;

    /**
     * 库存递减
     * @param id : 商品ID
     * @param count : 购买数量
     * @return
     */
    @PostMapping(value = "/decrCount")
    public String decrCount(@RequestParam(value = "id") int id, @RequestParam(value = "count") int count){
        //库存递减
        itemInfoService.decrCount(id,count);
        return "success";
    }

}
