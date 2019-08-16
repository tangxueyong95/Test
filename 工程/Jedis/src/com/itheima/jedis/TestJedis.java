package com.itheima.jedis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.domain.User;
import com.itheima.utils.JedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名:com.itheima.jedis
 * 作者:Leevi
 * 日期2019-05-25  11:47
 */
public class TestJedis {
    @Test //非静态，无参无返回才能使用
    public void test01() {
        //使用jedis操作redis服务器，往服务器中存放数据
        //1.创建连接
        String host = "localhost";
        int port = 6379;
        Jedis jedis = new Jedis(host, port);

        //调用set(key,value)方法，往redis服务器中存放一个数据
        jedis.set("nickname", "周杰棍");

        //千万不要忘记关闭连接
        jedis.close();
    }

    @Test
    public void test02() {
        //使用jedis获取redis中存放的nickname
        //1.创建连接
        Jedis jedis = new Jedis("localhost", 6379);
        //2.调用get(key)获取数据
        String nickname = jedis.get("nickname");
        System.out.println(nickname);
        //3.关闭连接
        jedis.close();
    }

    @Test
    public void test03() {
        //使用jedis的连接池
        String host = "localhost";
        int port = 6379;
        JedisPoolConfig poolConfig = new JedisPoolConfig();//使用子类而不使用父类的原因:1.子类的方法、属性更多   2.子类已经默认实现了一些方法
        poolConfig.setMaxTotal(15);
        poolConfig.setMaxIdle(15);
        poolConfig.setMinIdle(5);
        poolConfig.setMaxWaitMillis(3000);
        //1.创建连接池对象
        JedisPool jedisPool = new JedisPool(poolConfig, host, port);
        //2.从连接池中获取连接对象
        Jedis jedis = jedisPool.getResource();


        //调用jedis的方法获取nickname的值
        String nickname = jedis.get("nickname");
        System.out.println(nickname);

        //将连接归还到连接池
        jedis.close();
    }

    @Test
    public void test04() {
        //使用连接池的工具类获取连接
        Jedis jedis = JedisUtil.getJedis();
        String nickname = jedis.get("nickname");
        System.out.println(nickname);

        //用完要归还
        jedis.close();
    }

    @Test
    public void test05() {
        User user = new User(18, "lucy", "深圳", "翠花");
        //将user对象存放到redis服务器中
        //redis能够存放的数据类型:string,hash,list,set,sortedset
        //user ---> String,将对象转换成json
        Gson gson = new Gson();
        String jsonStr = gson.toJson(user);

        Jedis jedis = JedisUtil.getJedis();
        jedis.set("user", jsonStr);

        //关闭连接
        jedis.close();
    }

    @Test
    public void test06() {
        //将redis中的json字符串取出，然后转换成User对象
        Jedis jedis = JedisUtil.getJedis();
        String jsonStr = jedis.get("user");

        //使用Gson框架将json字符串转换成User
        Gson gson = new Gson();
        User user = gson.fromJson(jsonStr, User.class);
        System.out.println(user.getNickname());

        //关闭连接
        jedis.close();
    }

    @Test
    public void test07() {
        //如果是一个User集合怎么存放到redis，一样的转换成json
        List<User> users = new ArrayList<>();
        users.add(new User(18, "lucy", "深圳", "露西"));
        users.add(new User(28, "lily", "广州", "莉莉"));
        users.add(new User(38, "tom", "长沙", "汤姆"));

        //将users集合转换成json字符串
        Gson gson = new Gson();
        String jsonStr = gson.toJson(users);

        Jedis jedis = JedisUtil.getJedis();
        jedis.set("list", jsonStr);

        //关闭连接
        jedis.close();
    }

    @Test
    public void test08() {
        //获取redis中存放的json数组，然后将其转成User的集合
        Jedis jedis = JedisUtil.getJedis();
        String jsonStr = jedis.get("list");
        jedis.close();

        //使用Json将json数组变成List集合
        Gson gson = new Gson();
        List<User> users = gson.fromJson(jsonStr, new TypeToken<List<User>>() {
        }.getType());

        //遍历users
        for (User user : users) {
            System.out.println(user.getNickname());
        }
    }
}
