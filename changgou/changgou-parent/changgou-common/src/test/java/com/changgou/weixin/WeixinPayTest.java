package com.changgou.weixin;

import com.github.wxpay.sdk.WXPayUtil;

import java.util.HashMap;
import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.weixin
 * 微信支付SDK的使用
 ****/
public class WeixinPayTest {

    public static void main(String[] args) throws Exception {
        //随机字符
        System.out.println(WXPayUtil.generateNonceStr());

        //将Map转成XML
        Map<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("age","28");
        dataMap.put("address","北京");
        System.out.println(WXPayUtil.mapToXml(dataMap));

        //XML字符串，转成Map
        String xmlstr = "<xml><address>北京</address><age>28</age></xml>";
        Map<String, String> map = WXPayUtil.xmlToMap(xmlstr);
        System.out.println(map);

        //将Map转成XML自带签名(验签)
        System.out.println(WXPayUtil.generateSignature(dataMap, "UUUUUUUUUUUUUUU"));

    }
}
