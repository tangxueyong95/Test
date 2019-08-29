package com.changgou.pay.service;

import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.pay.service
 ****/
public interface WeixinPayService {

    /***
     * 查询微信支付结果
     * @param outtradeno
     */
    Map<String,String> queryPayStatus(String outtradeno) throws Exception;

    /****
     * 创建二维码
     */
    //Map<String, String> createNative(String outtradeno, String money) throws Exception;
    Map<String, String> createNative(Map<String,String> parameters) throws Exception;
}
