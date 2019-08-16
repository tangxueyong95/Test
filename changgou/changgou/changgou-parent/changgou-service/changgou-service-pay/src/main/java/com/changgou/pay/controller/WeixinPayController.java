package com.changgou.pay.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.pay.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.pay.controller
 ****/
@RestController
@RequestMapping(value = "/weixin/pay")
@CrossOrigin
public class WeixinPayController {

    @Autowired
    private WeixinPayService weixinPayService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    /***
     * 支付回调
     * http://2cw4969042.wicp.vip:34488/weixin/pay/notify/url
     * @param request
     * @return
     */
    @RequestMapping(value = "/notify/url")
    public String notifyUrl(HttpServletRequest request){
        InputStream inStream;
        try {
            //读取支付回调数据
            inStream = request.getInputStream();
            //ByteArrayOutputStream
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            // 将支付回调数据转换成xml字符串
            String result = new String(outSteam.toByteArray(), "utf-8");
            //将xml字符串转换成Map结构
            Map<String, String> map = WXPayUtil.xmlToMap(result);
            System.out.println(map);
            if(map!=null && map.get("attach")!=null){
                //attach=Map   exchange=交换机   routing=路由key
                // attach= {"exchange":"mq.pay.exchange.order","routing":"mq.pay.routing.key"}
                Map<String,String> attachMap = JSON.parseObject(map.get("attach"),Map.class);

                //将整个Map对象发送到RabbitMQ中
                //rabbitTemplate.convertAndSend(env.getProperty("mq.pay.exchange.order"),env.getProperty("mq.pay.routing.key"), JSON.toJSONString(map));
                rabbitTemplate.convertAndSend(env.getProperty(attachMap.get("exchange")),env.getProperty(attachMap.get("routing")), JSON.toJSONString(map));
            }

            //响应数据设置
            Map respMap = new HashMap();
            respMap.put("return_code","SUCCESS");
            respMap.put("return_msg","OK");
            return WXPayUtil.mapToXml(respMap);
        } catch (Exception e) {
            e.printStackTrace();
            //记录错误日志
        }
        return null;
    }

    /***
     * 查询支付状态
     * @param outtradeno
     * @return
     */
    @GetMapping(value = "/status/query")
    public Result queryStatus(String outtradeno) throws Exception{
        Map<String,String> resultMap = weixinPayService.queryPayStatus(outtradeno);
        return new Result(true,StatusCode.OK,"查询状态成功！",resultMap);
    }

    /***
     * 创建二维码
     * @return
     */
    @RequestMapping(value = "/create/native")
    //public Result createNative(String outtradeno, String money) throws Exception{
    public Result createNative(@RequestParam Map<String,String> parameters) throws Exception{
        //exchange
        //queue
        String exchange = parameters.get("exchange");
        String routing =parameters.get("routing");
        String username =parameters.get("username");
        Map<String,String> attachMap = new HashMap<String,String>();
        attachMap.put("exchange",exchange);
        attachMap.put("routing",routing);
        attachMap.put("username",username);
        parameters.put("attach",JSON.toJSONString(attachMap));

        Map<String,String> resultMap = weixinPayService.createNative(parameters);
        return new Result(true, StatusCode.OK,"创建二维码预付订单成功！",resultMap);
    }
}
