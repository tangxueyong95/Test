package com.example.test.controller;

import com.example.test.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("device")
public class DeviceController {

    private boolean b=true;
    @Autowired
    DeviceService deviceService;
//
//    @RequestMapping("deviceAdd/{number}")
//    @ResponseBody
//    public String deviceAdd(@PathVariable("number") Integer number){
//        if (b){
//            b=false;
//            deviceService.deviceAdd(number);
//            b=true;
//        }
//        return "设备添加成功";
//    }
//
//    @RequestMapping("deviceDelete")
//    @ResponseBody
//    public String deviceDelete(){
//        if (b){
//            b=false;
//            deviceService.deviceDelete();
//            b=true;
//        }
//        return "设备删除成功";
//    }

//    @RequestMapping("deviceImport")
//    @ResponseBody
//    public String deviceImport(@RequestBody Map<String,Object> map){
//        if (b){
//            b=false;
//            deviceService.deviceImport(map);
//            b=true;
//        }
//        return "设备添加成功";
//    }
}
