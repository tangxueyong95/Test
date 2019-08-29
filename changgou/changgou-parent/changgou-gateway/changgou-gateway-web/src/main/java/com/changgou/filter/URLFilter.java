package com.changgou.filter;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.filter
 * 请求路径的过滤
 ****/
public class URLFilter {


    /*****
     * 控制需要放行的地址
     */
    private static final String userUrls="/api/user/add";

    /***
     * 判断用户请求某个地址是否允许访问
     * @return
     */
    public static boolean isAuthorizer(String path){
        //所有放行的地址
        String[] urls = userUrls.split(",");
        //如果用户请求的地址是userUrls中的任意一个地址，都放行
        for (String url : urls) {
            if(path.equals(url)){
                return false;
            }
        }
        return true;
    }


}
