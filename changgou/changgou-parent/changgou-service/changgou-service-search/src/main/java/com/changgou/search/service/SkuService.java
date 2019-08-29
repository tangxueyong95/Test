package com.changgou.search.service;

import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.search.service
 ****/
public interface SkuService {

    /***
     * 搜索
     * @param searchMap:用户的搜索条件
     * @return resultMap:响应数据
     */
    Map searhc(Map<String,String> searchMap);

    /***
     * 将数据导入到ES中
     */
    void importSku();
}
