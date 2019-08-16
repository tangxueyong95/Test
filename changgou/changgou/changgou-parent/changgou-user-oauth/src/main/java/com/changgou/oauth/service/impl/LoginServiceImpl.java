package com.changgou.oauth.service.impl;

import com.changgou.oauth.service.LoginService;
import com.changgou.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.oauth.service.impl
 ****/
@Service
public class LoginServiceImpl implements LoginService {

    //远程发送请求
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /*****
     * 登录
     * @param username:用户名
     * @param password：密码
     * @param clientid：客户端ID
     * @param secret：秘钥
     * @param granttype：授权模式
     * @return
     */
    @Override
    public AuthToken login(String username, String password, String clientid, String secret, String granttype) throws Exception{
        //封装表单对象，封装用户名、密码、授权模式
        MultiValueMap<String,Object> parameters = new LinkedMultiValueMap<String,Object>();
        parameters.add("username",username);
        parameters.add("password",password);
        parameters.add("grant_type",granttype);

        //秘钥和客户端要封装到头文件中,并且要Base64加密
        MultiValueMap<String,Object> headers = new LinkedMultiValueMap<String,Object>();
        byte[] encode = Base64.getEncoder().encode((clientid + ":" + secret).getBytes());
        String Authorization = "Basic "+new String(encode,"UTF-8");
        headers.add("Authorization",Authorization);

        //封装请求头和请求体
        HttpEntity httpEntity = new HttpEntity(parameters,headers);

        //提交地址： http://localhost:9001/oauth/token
        //String path = "http://localhost:9001/oauth/token";
        ServiceInstance serviceInstance = loadBalancerClient.choose("user-auth");
        String path =  serviceInstance.getUri().toString()+"/oauth/token";

        /***
         * 执行登录
         * 1:登录地址
         * 2:提交方式
         * 3:提交的数据：参数+heders
         * 4:返回数据需要转换的类型的字节码对象
         */
        ResponseEntity<Map> response = restTemplate.exchange(path, HttpMethod.POST, httpEntity, Map.class);
        Map<String,String> result = response.getBody();
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken(result.get("access_token"));
        authToken.setRefreshToken(result.get("refresh_token"));
        authToken.setJti(result.get("jti"));
        return authToken;
    }
}
