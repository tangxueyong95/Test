package com.example.test.util;

import java.security.MessageDigest;

public class MD5 {
    /**
     * 账号密码加密
     * @param username
     * @param password
     * @return
     */
    public final static String encodePasswordMD5(String username, String password)
    {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try
        {
            byte[] btInput = password.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest(username.getBytes());
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public final static String encodeSignatureMD5(String content)
    {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try
        {
            byte[] btInput = content.getBytes("utf-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 生成一个由md5加密的签名
     * @param content 需要加密的内容
     * @param accessKey 访问key
     * @return
     */
    public final static String encodeSignatureMD5(String content, String accessKey)
    {
        String result = content+":"+accessKey;
        System.out.println("进入一段拼接的字符串:"+content+":"+accessKey );
        return encodeSignatureMD5(result);
    }

    public static void main(String[] args) {
        String s="appId={supplier-13}&imei={123456}&imsi={000000000000000}&inputCharset={1}&installAddress={中国}&installTime={2019-12-05 10:57:00}&lat={1}&lon={1}&nbDeviceModel={SM-7}&nbDeviceName={注册测试}&nbDeviceType={10026}&operationUnitCode={1}&signType={1}&status={10}&telecom={CMCC}&unitCode={20171102130812557}&versionNo={v1.0}";
        String s1 = MD5.encodeSignatureMD5(s,"XbLr+R7siTLIxepEUaiJkg==");
        System.out.println(s1);
    }
}
