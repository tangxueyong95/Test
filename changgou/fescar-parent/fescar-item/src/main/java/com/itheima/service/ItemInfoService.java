package com.itheima.service;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima.service
 ****/
public interface ItemInfoService {

    /**
     * 库存递减
     * @param id
     * @param count
     */
    void decrCount(int id, int count);
}
