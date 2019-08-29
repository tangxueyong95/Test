package com.changgou.service;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.service
 ****/
public interface PageService {

    /***
     * 生成静态页方法
     * @param spuid
     */
    void createHtml(Long spuid) throws Exception;
}
