package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:45
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/ordersetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    // 从excel中读取数据库，批量导入到数据库中
    @RequestMapping(value = "/upload")
    public Result upload(@RequestParam(value = "excelFile") MultipartFile excelFile){
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            // 数据转换 List<String[]> == > List<OrderSetting>
            List<OrderSetting> orderSettingList = new ArrayList<OrderSetting>();
            if(list!=null && list.size()>0){
                for (String[] s : list) {
                    OrderSetting orderSetting = new OrderSetting(new Date(s[0]),Integer.parseInt(s[1]));
                    orderSettingList.add(orderSetting);
                }
            }
            // 向Service传递，在Service层处理
            orderSettingService.uploadAdd(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    // 首页初始化预约设置的数据
    @RequestMapping(value = "/findMonthListByMonthBetween")
    public Result findMonthListByMonthBetween(String date){
        try {
            List<Map<String,Object>> list = orderSettingService.findMonthListByMonthBetween(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    // 预约设置从页面进行预约人数的设置
    @RequestMapping(value = "/edit")
    public Result edit(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.edit(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
