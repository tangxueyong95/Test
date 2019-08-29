package com.changgou.pay.service.impl;

import com.changgou.pay.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.pay.service.impl
 ****/
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    //weixin:
    //appid: wx8397f8696b538317     #应用ID
    //partner: 1473426802           # 商户号
    //partnerkey: T6m9iK73b0kn9g5v426MKfHQH7X8rKwb    # 秘钥
    //notifyurl: http://www.itcast.cn  # 回到地址

    @Value("${weixin.appid}")
    private String appid;           //应用ID

    @Value("${weixin.partner}")
    private String partner;           //商户ID

    @Value("${weixin.partnerkey}")
    private String partnerkey;           //秘钥

    @Value("${weixin.notifyurl}")
    private String notifyurl;           //回调地址

    /****
     * 查询支付状态
     * @param outtradeno
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> queryPayStatus(String outtradeno) throws Exception{
        //准备参数
        Map<String,String> parameterMap = new HashMap<String,String>();
        parameterMap.put("appid",appid);        //应用ID
        parameterMap.put("mch_id",partner);     //商户号
        parameterMap.put("out_trade_no",outtradeno);    //畅购微服务生成的唯一标识
        parameterMap.put("nonce_str", WXPayUtil.generateNonceStr());    //随机字符
        //将Map转成XML字符串，自带签名
        String xmlParameters = WXPayUtil.generateSignedXml(parameterMap, partnerkey);

        //准备URL
        String url = "https://api.mch.weixin.qq.com/pay/orderquery";

        //设置请求地址
        HttpClient httpClient = new HttpClient(url);
        //提交的参数适
        httpClient.setXmlParam(xmlParameters);
        //https设置
        httpClient.setHttps(true);
        //执行请求
        httpClient.post();

        //获取结果
        String content = httpClient.getContent();
        //将XML转成Map
        return WXPayUtil.xmlToMap(content);
    }

    /****
     * 创建二维码实现
     */
    @Override
    //public Map<String, String> createNative(String outtradeno, String money) throws Exception{
    public Map<String, String> createNative(Map<String,String> parameters) throws Exception{
        //准备参数
        Map<String,String> parameterMap = new HashMap<String,String>();
        parameterMap.put("appid",appid);        //应用ID
        parameterMap.put("mch_id",partner);     //商户号
        parameterMap.put("nonce_str", WXPayUtil.generateNonceStr());    //随机字符
        parameterMap.put("body","畅购商品");
        parameterMap.put("out_trade_no",parameters.get("outtradeno"));    //畅购微服务生成的唯一标识
        parameterMap.put("total_fee",parameters.get("money"));
        //附加参数
        parameterMap.put("attach",parameters.get("attach"));
        parameterMap.put("spbill_create_ip","127.0.0.1");
        parameterMap.put("notify_url",notifyurl);   //  outtradeno-> http://2cw4969042.wicp.vip:34488/weixin/pay/notify/url
                                                      //  outtradeno-> http://2cw4969042.wicp.vip:34488/weixin/pay/notify/url
        parameterMap.put("trade_type","NATIVE");
        //将Map转成XML字符串，自带签名
        String xmlParameters = WXPayUtil.generateSignedXml(parameterMap, partnerkey);

        //准备URL
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        //设置请求地址
        HttpClient httpClient = new HttpClient(url);
        //提交的参数适
        httpClient.setXmlParam(xmlParameters);
        //https设置
        httpClient.setHttps(true);
        //执行请求
        httpClient.post();

        //获取结果
        String content = httpClient.getContent();
        //将XML转成Map
        return WXPayUtil.xmlToMap(content);
    }
}
