package com.example.test.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.LineIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/****************************
 *
 *@author Xcc😊
 *@ClassName: HttpClientUtils
 *@CreateTime: 2019年08月22日 19:53 
 *@description
 *
 *
 *****************************/
public class HttpClientUtils {

    /*public static void main(String[] args) throws Exception{
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("keyt","d751d24f930711e99f1094c69177852d");
        map.put("type","zt");

        JSONObject jsonObject = new JSONObject(map);
        String json = jsonObject.toString();
        System.out.println(json);
        // 获取常量自带
        String url = "http://test.cnmaker.top/data.cfrc?action=get_dictionary";
        String restul = sendPost(url,json);
        System.out.println(restul);
        // 去除数据中的转意符"\"
        String tmp = StringEscapeUtils.unescapeJavaScript(restul);
        // 去除数据首尾两端的双引号
        String newStr = tmp.substring(1, tmp.length()-1);
        System.out.println(newStr);
        // 将数据转成json对象
        JSONObject js = JSON.parseObject(newStr);
        if ("true".equals(js.getString("Success"))) {
            if ("zjlx".equals(map.get("type"))||"zt".equals(map.get("type"))||"hy".equals(map.get("type"))){
                JSONObject jsonObject1 = JSONObject.parseObject(js.getString("Data"));
                String data = jsonObject1.getString("dictionary_list");
                JSONArray array = JSONArray.parseArray(data);
                System.out.println(array);
                for (int i = 0; i < array.size(); i++) {
                    JSONObject job = array.getJSONObject(i);
                    System.out.println(job.get("id") + ":" + job.get("title"));
                }
            }else {
                String data = js.getString("dictionary_list");
                JSONArray array = JSONArray.parseArray(data);
                System.out.println(array);
                for (int i = 0; i < array.size(); i++) {
                    JSONObject job = array.getJSONObject(i);
                    System.out.println(job.get("id") + ":" + job.get("title"));
                }
            }
        }
    }*/

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    //设置超时时间
    private int timeout = 30000;

    private static RequestConfig requestConfig;

    private static final HttpClientUtils httpsRequest = new HttpClientUtils();

    private HttpClientUtils(){
        requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(timeout).build();

    }

    public static HttpClientUtils getHttpsRequestSingleton() {
        return httpsRequest;
    }

    /**
     * 发送get请求
     * @param url
     * @param params
     * @return
     */
    public JSONObject sendGet(String url, Map<String, String> params) {
        Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
        StringBuffer urlParamsBuffer = new StringBuffer();
        while(iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            urlParamsBuffer.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        String getUrl = url;
        if(urlParamsBuffer.length() > 0) {
            urlParamsBuffer.deleteCharAt(urlParamsBuffer.length() - 1);
            getUrl += '?'+ urlParamsBuffer.toString();
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.info(getUrl);
        HttpGet httpGet;
        httpGet = new HttpGet(getUrl);
        httpGet.setConfig(requestConfig);

        JSONObject jsonObject = new JSONObject();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseContent = EntityUtils.toString(entity);
            logger.info("&*&*&*&*&*&*&*"+responseContent+"#%^$^&*@$^#%^%$");
            jsonObject = JSON.parseObject(responseContent);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        finally {

        }
        return jsonObject;
    }


    /**
     * 发送post请求
     * @param url
     * @param params
     * @return
     */
    public static JSONObject sendPost(String url, Map<String, String> params) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);
        httpost.setConfig(requestConfig);
        JSONObject jsonObject = new JSONObject();
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(httpost);
            HttpEntity entity = response.getEntity();
            String responseContent = EntityUtils.toString(entity);
            jsonObject = JSON.parseObject(responseContent);
        } catch(UnsupportedEncodingException e){
            logger.error(e.getMessage(),e);
        } catch (IOException e){
            logger.error(e.getMessage(),e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }
        return jsonObject;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        StringBuffer strb=new StringBuffer(result);
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setConnectTimeout(3000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("charset", "utf-8");   // 设置字符编码

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line="";
            LineIterator lineItr=new LineIterator(in);
            while(lineItr.hasNext()){
                line=(String) lineItr.next();
                strb.append(line);
            }
            return strb.toString();
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常！",e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                logger.error("关闭post请求IO流异常",ex);
            }
        }
        return result;
    }

    public String sendGetTets(String url, Map<String, String> params){
        String result="";
        BufferedReader in=null;
        StringBuffer strb=new StringBuffer(result);
        Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
        String getUrl = url;
        StringBuffer urlParamsBuffer = new StringBuffer();
        while(iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            urlParamsBuffer.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        if(urlParamsBuffer.length() > 0) {
            urlParamsBuffer.deleteCharAt(urlParamsBuffer.length() - 1);
            getUrl += '?'+ urlParamsBuffer.toString();
        }
        try {
            URL realUrl=new URL(getUrl);
            URLConnection conn=realUrl.openConnection();
            //设置通道的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //建立实际的连接
            conn.connect();
            in=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line="";
            LineIterator lineItr=new LineIterator(in);
            while(lineItr.hasNext()){
                line=(String)lineItr.next();
                strb.append(line);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }finally{
            try {
                if(in !=null){
                    in.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }
        return strb.toString();
    }

}
