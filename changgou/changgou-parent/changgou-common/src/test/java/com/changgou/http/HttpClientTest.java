package com.changgou.http;

import entity.HttpClient;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.http
 * 实现各个应用之间相互发送Http请求调用
 ****/
public class HttpClientTest {


    public static void main(String[] args) throws Exception{
        String url ="http://www.itheima.com";

        //HttpClient
        HttpClient httpClient = new HttpClient(url);

        //支持HTTPS
        httpClient.setHttps(false);

        //设置参数
        //httpClient.setXmlParam("<xml></xml>");

        //发送请求
        httpClient.get();
        //httpClient.post();

        //获取返回结果
        String content = httpClient.getContent();
        System.out.println(content);

    }
}
