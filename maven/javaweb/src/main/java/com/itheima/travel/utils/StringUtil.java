package com.itheima.travel.utils;

/**
 * 包名:com.itheima.travel.utils
 * 作者:Leevi
 * 日期2019-05-29  11:54
 */
public class StringUtil {
    /**
     * 判断一个字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str == null || "".equals(str) || "null".equals(str);
    }
}
