package com.itheima.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName Md5Test
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/7/5 17:49
 * @Version V1.0
 */
public class BcryptTest {
    /**
     * spring security中的BCryptPasswordEncoder方法采用SHA-256 +随机盐+密钥对密码进行加密。SHA系列是Hash算法，不是加密算法，使用加密算法意味着可以解密（这个与编码/解码一样），但是采用Hash处理，其过程是不可逆的。
     （1）加密(encode)：注册用户时，使用SHA-256+随机盐+密钥把用户输入的密码进行hash处理，得到密码的hash值，然后将其存入数据库中。
     （2）密码匹配(matches)：用户登录时，密码匹配阶段并没有进行密码解密（因为密码经过Hash处理，是不可逆的），而是使用相同的算法把用户输入的密码进行hash处理，得到密码的hash值，然后将其与从数据库中查询到的密码hash值进行比较。如果两者相同，说明用户输入的密码正确。
     */
    @Test
    public void md5(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String s1 = bCryptPasswordEncoder.encode("admin"); // $2a$10$enPQZAqldDTdu/ENWbFHt.XXvhXSM4hraIM.EQAXGqeGMnnKsybQ.
        String s2 = bCryptPasswordEncoder.encode("123"); // $2a$10$BA2RUeviZjWOh3i9Yi9s7.nlWSddj5J1s6k/FbeGoRPnzAAlDfmSy
        System.out.println(s1);
        System.out.println(s2);

        boolean flag = bCryptPasswordEncoder.matches("123", "$2a$10$enPQZAqldDTdu/ENWbFHt.XXvhXSM4hraIM.EQAXGqeGMnnKsybQ2");
        System.out.println(flag);
    }
}
