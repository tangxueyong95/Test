package com;

import redis.clients.jedis.Jedis;

public class jedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379);
        System.out.println(jedis);
        String host = "localhost";
        int port = 6379;
        Jedis jedi = new Jedis(host, port);

        //调用set(key,value)方法，往redis服务器中存放一个数据
        jedi.set("nickname","周杰棍");

        //千万不要忘记关闭连接
        jedis.close();
    }
}
