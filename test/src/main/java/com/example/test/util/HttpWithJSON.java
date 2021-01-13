package com.example.test.util;

/**
 * Created with IntelliJ IDEA.
 * User: Cya
 * Date: 2019/11/14
 * Time: 10:22
 * Description: 获取消火栓水压实时数据
 */
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class HttpWithJSON
{




    public static JSONArray httpPostWithJSON(String url,String json)
    {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String auth_key = jedis.get("auth_key");
        if(auth_key == null){
            auth_key = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiSkt6aGlyYWRtaW4iLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjhmYWQ0ZWU1LTJhNTQtNDg1Ni05MmVhLTBhOTcxZjJmZjQxMCIsIkFwcElkIjoiSDc5MjIxRkM5R0JIRDk0MzMyIiwiU2VjcmV0IjoiOTM5RkE5OUREQzNHMjlBRkNINzlISDhBQ0VCNSIsIkNvbXBhbnkiOiIxMDE1IiwiRGVwdCI6IjAiLCJleHAiOjE1OTQzNTEwNDIsImlzcyI6Imh0dHA6Ly9jaGVlci56aGlhbmRha2pzbHQuY29tOjgwMDEiLCJhdWQiOiJodHRwOi8vY2hlZXIuemhpYW5kYWtqc2x0LmNvbTo4MDAxIn0.bt1rm6LtHITfkf0ZLSkVAFIqJbS8xJW7M22uw6gMDEE";
        }
        // 创建默认的httpClient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try
        {
            // 创建httppost
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-type", "application/json; charset=utf-8");
            httppost.addHeader("Authorization","Bearer "+auth_key);
            //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiSkt6aGlyYWRtaW4iLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjhmYWQ0ZWU1LTJhNTQtNDg1Ni05MmVhLTBhOTcxZjJmZjQxMCIsIkFwcElkIjoiSDc5MjIxRkM5R0JIRDk0MzMyIiwiU2VjcmV0IjoiOTM5RkE5OUREQzNHMjlBRkNINzlISDhBQ0VCNSIsIkNvbXBhbnkiOiIxMDE1IiwiRGVwdCI6IjAiLCJleHAiOjE1OTQzNTEwNDIsImlzcyI6Imh0dHA6Ly9jaGVlci56aGlhbmRha2pzbHQuY29tOjgwMDEiLCJhdWQiOiJodHRwOi8vY2hlZXIuemhpYW5kYWtqc2x0LmNvbTo4MDAxIn0.bt1rm6LtHITfkf0ZLSkVAFIqJbS8xJW7M22uw6gMDEE
            System.out.println("executing request " + httppost.getURI());

            // 向POST请求中添加消息实体
            StringEntity se = new StringEntity(json, "UTF-8");
            httppost.setEntity(se);
            System.out.println("request parameters " + json);

            // 执行post请求
            CloseableHttpResponse response = httpclient.execute(httppost);
            try
            {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if(response.getStatusLine().getStatusCode() == 401){
                        // 创建httppost
                        HttpPost httppost1 = new HttpPost("http://interface.zhiandakjslt.com/api/Authorization/Token");
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("AppId","H79221FC9GBHD94332");
                        jsonObject.put("Secret","939FA99DDC3G29AFCH79HH8ACEB5");
                        //创建指定内容和编码的字符串实体类
                        StringEntity entity1=new StringEntity(jsonObject.toString(), Consts.UTF_8);
                        //设置请求参数
                        httppost1.setEntity(entity1);
                        // 执行post请求
                        CloseableHttpResponse response1 = httpclient.execute(httppost);
                        HttpEntity entity2 = response.getEntity();
                        String jsonB = EntityUtils.toString(entity, "UTF-8");
                        System.out.println(jsonB);
                        jsonB = "["+jsonB+"]";
                        JSONArray jsonArray3 = JSONArray.parseArray(jsonB);
                        System.out.println(jsonArray3);
                        //取出数组第一个元素
                        JSONObject jsa = (JSONObject)jsonArray3.get(0);
                        Object jsb = jsa.get("Message");
                        JSONArray jsonArray2 = JSONArray.parseArray(jsb.toString());
                        jedis.set("auth_key",jsonArray2.toJSONString());
                        httpPostWithJSON(url,json);
                }
                if (entity != null)
                {
                    // 打印响应内容
                    System.out.println("--------------------------------------");
                    //System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    String jsonA = EntityUtils.toString(entity, "UTF-8");
                    System.out.println(jsonA);
                    jsonA = "["+jsonA+"]";
                    JSONArray jsonArray = JSONArray.parseArray(jsonA);//转换String
                    System.out.println(jsonArray);
                    //取出数组第一个元素
                    JSONObject jsa = (JSONObject)jsonArray.get(0);
                    Object jsb = jsa.get("Data");
                    JSONArray jsonArray1 = JSONArray.parseArray(jsb.toString());
                    //JSONObject jUser = jsonArray.getJSONObject(0).getJSONObject("Data");
                    return jsonArray1;
                    //System.out.println("--------------------------------------");

                }
            }
            finally
            {
                response.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("executing httpPostWithJSON error: " + e.getMessage());
        }
        finally
        {
            // 关闭连接,释放资源
            try
            {
                httpclient.close();
            }
            catch (IOException e)
            {
                System.out.println("executing httpPostWithJSON error: " + e.getMessage());
            }
        }
        return null;
    }


}
