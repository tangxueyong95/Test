package com.java.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {

    /***
     * 获取连接对象
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception{
        //创建链接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //设置RabbitMQ服务主机地址,默认localhost
        connectionFactory.setHost("localhost");

        //设置RabbitMQ服务端口,默认5672
        connectionFactory.setPort(5672);

        //设置虚拟主机名字，默认/
        connectionFactory.setVirtualHost("/szitheima");

        //设置用户连接名，默认guest
        connectionFactory.setUsername("admin");

        //设置链接密码，默认guest
        connectionFactory.setPassword("admin");

        //创建链接
        Connection connection = connectionFactory.newConnection();

        return connection;
    }
}
