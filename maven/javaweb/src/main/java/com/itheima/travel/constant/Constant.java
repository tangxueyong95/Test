package com.itheima.travel.constant;

/**
 * 包名:com.itheima.travel.constant
 * 作者:Leevi
 * 日期2019-05-28  10:35
 */
public class Constant {
    public static final String UNACTIVE = "N";
    public static final String USERNAME_ENABLE = "用户名可用";
    public static final String USERNAME_EXIST = "用户名已存在";
    public static final String SERVSER_ERROR = "服务器异常";

    public static final String REGISTER_FAILED = "注册失败";
    public static final String ACTIVED = "Y";
    public static final String ALREADY_ACTIVE = "请勿重复激活";
    public static final String ERROR_CODE = "激活码错误";
    public static final String CHECKCODE_KEY = "checkCode";
    public static final String LOGINFAILED_UNACTIVE = "用户未激活";
    public static final String LOGINFAILED_ERROR_PASSWORD = "密码错误";
    public static final String LOGINFAILED_ERROR_USERNAME = "用户名错误";
    public static final String USER_KEY = "user";
    public static final String LOGINFAILED_ERROR_VCODE = "验证码错误";
    /*redis中的key的规范命名方式   项目名_模块名_功能名*/
    public static final String ALL_CATEGORY_KEY = "travel_category_all";
    public static final String POPKEY = "popList";
    public static final String NEWESTKEY = "newestList";
    public static final String THEMEKEY = "themeList";
    public static final Integer ROUTE_PAGESIZE = 8;
}