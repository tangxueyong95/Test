package com.itheima.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * 包名:com.itheima.utils
 * 作者:Leevi
 * 日期2019-05-25  12:03
 */
public class JedisUtil {
    private static JedisPool jedisPool;
    private static String host;
    private static int port;
    private static int maxTotal;
    private static int maxIdle;
    private static int minIdle;
    private static int maxWaitMillis;
    static {
        //读取配置文件
        ResourceBundle bundle = ResourceBundle.getBundle("jedisconfig");
        host = bundle.getString("host");
        port = Integer.parseInt(bundle.getString("port"));
        maxTotal = Integer.parseInt(bundle.getString("maxTotal"));
        maxIdle = Integer.parseInt(bundle.getString("maxIdle"));
        minIdle = Integer.parseInt(bundle.getString("minIdle"));
        maxWaitMillis = Integer.parseInt(bundle.getString("maxWaitMillis"));

        JedisPoolConfig poolConfig = new JedisPoolConfig();//使用子类而不使用父类的原因:1.子类的方法、属性更多   2.子类已经默认实现了一些方法
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        //1.创建连接池对象
        jedisPool = new JedisPool(poolConfig,host,port);
    }
    //提供一个方法，让调用者获取连接对象
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
