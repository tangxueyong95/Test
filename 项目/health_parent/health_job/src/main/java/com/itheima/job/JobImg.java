package com.itheima.job;

import com.itheima.health.constant.RedisConstant;
import com.itheima.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName JobImg
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/29 18:23
 * @Version V1.0
 */
public class JobImg {

    @Autowired
    JedisPool jedisPool;

    public void deleteFile(){
        // 使用redis，对比redis中key查找不同的值
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        // 查找不同的值存在set集合中
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()){
            String fileName = iterator.next(); // 读取值
            System.out.println("删除的文件是："+fileName);
            // 删除文件
            QiniuUtils.deleteFileFromQiniu(fileName);
            // 删除redis中的值
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        }
    }
}
