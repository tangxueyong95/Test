package com.changgou.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import javax.crypto.MacSpi;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.jwt
 ****/
public class JwtTest {


    /****
     * Base64测试
     */
    @Test
    public void testBase64(){
        String str = "itheima";
        byte[] stren = Base64.getEncoder().encode(str.getBytes());
        try {
            String enstr = new String(stren,"UTF-8");
            System.out.println(enstr);

            //解密
            byte[] decode = Base64.getDecoder().decode(enstr);
            System.out.println(new String(decode, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /****
     * 创建令牌数据
     */
    @Test
    public void testCreateJwt(){
        //JwtBuilder对象
        JwtBuilder builder = Jwts.builder();

        //通过JwtBuilder对象封装Jwt的载荷信息
        builder.setIssuer("黑马训练营");    //颁发者
        builder.setSubject("Jwt爱好者");   //主题设置
        builder.setIssuedAt(new Date());   //颁发时间
        builder.setId("No001");            //唯一标识
        builder.setExpiration(new Date(System.currentTimeMillis()+1000000));  //过期时间

        //自定义载荷数据
        Map<String,Object> map = new HashMap<>();
        map.put("author","小红");
        map.put("address","shanghai");
        builder.addClaims(map);

        /***
         * 添加私钥
         * 指定生成令牌的算法
         * 指定私钥
         */
        builder.signWith(SignatureAlgorithm.HS256,"itcast");


        //通过JwtBuilder生成Jwt令牌->  header . payload  . signature
        String token = builder.compact();
        System.out.println(token);
    }


    /***
     * 解密
     */
    @Test
    public void testParseJwt(){
        String str1 ="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiLpu5Hpqazorq3nu4PokKUiLCJzdWIiOiJKd3TniLHlpb3ogIUiLCJpYXQiOjE1NjQ5OTg3ODAsImp0aSI6Ik5vMDAxIiwiZXhwIjoxNTY0OTk5NzgwLCJhZGRyZXNzIjoic2hhbmdoYWkiLCJhdXRob3IiOiLlsI_nuqIifQ.3v9VG9BUS9_74jXfXstKs-tkwnoLJKynuzVAikfh8Sw";
        String str = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiLpu5Hpqazorq3nu4PokKUiLCJzdWIiOiJKd3TniLHlpb3ogIUiLCJpYXQiOjE1NjUwNDIxMjgsImp0aSI6Ik5vMDAxIiwiZXhwIjoxNTY1MDQzMTI4LCJhZGRyZXNzIjoic2hhbmdoYWkiLCJhdXRob3IiOiLlsI_nuqIifQ.80YdmLfaIvhPm8kGOoYzqs031e74nPtsRwbiZF9KmBc";

        //解密需要指定私钥
        Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(str).getBody();
        System.out.println(claims.toString());

        try {
            System.out.println(new String(Base64.getDecoder().decode("eyJhbGciOiJIUzI1NiJ9"),"UTF-8"));
            System.out.println(new String(Base64.getDecoder().decode("eyJpc3MiOiLpu5Hpqazorq3nu4PokKUiLCJzdWIiOiJKd3TniLHlpb3ogIUiLCJpYXQiOjE1NjQ5OTg3ODAsImp0aSI6Ik5vMDAxIiwiZXhwIjoxNTY0OTk5NzgwLCJhZGRyZXNzIjoic2hhbmdoYWkiLCJhdXRob3IiOiLlsI_nuqIifQ"),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
