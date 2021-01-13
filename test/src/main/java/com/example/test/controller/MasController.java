package com.example.test.controller;

import com.example.test.entity.Msg;
import com.example.test.util.MasUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("Mas")
public class MasController {

    @RequestMapping("sendMas")
    @ResponseBody
    public Msg sendMas(@RequestBody Map map){
        String url=(String)map.get("url");
        String id=(String)map.get("id");
        String pwd=(String)map.get("pwd");
        String serviceid=(String)map.get("serviceid");
        String phone=(String)map.get("phone");
        String message=(String)map.get("message");
        return MasUtils.sendMas(url,id,pwd,serviceid,phone,message);
    }
}
