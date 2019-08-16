package com.itheima.worker;

import com.itheima.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/*****
 * @Author: shenzhen-itheima
 * @Date: 2019/7/24 10:14
 * @Description: com.itheima.simple
 * 消息消费者:消息消费实现
 ****/
public class WorkerConsumerTwo {

    /***
     * 消息消费实现
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        //获取连接对象
        Connection connection = ConnectionUtils.getConnection();

        //创建频道
        Channel channel = connection.createChannel();

        //消息监听
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            /***
             * @param consumerTag
             * @param envelope:消息信息封装
             * @param properties:自定义的消息
             * @param body:发送的消息内容
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Two:routekey:"+envelope.getRoutingKey());
                System.out.println("Two:id:"+envelope.getDeliveryTag());
                System.out.println("Two:exchange:"+envelope.getExchange());
                System.out.println("Two:消息内容："+new String(body,"UTF-8"));
            }
        };

        /***
         * 创建消费者,并设置消息处理
         * 1:要监听读取的消息队列名字
         * 2:应答模式：true，自动应答，消息读取后，立即设置成已消费状态  false:手动应答
         * 3:消息监听处理实现
         */
        channel.basicConsume("worker_queue",true,defaultConsumer);

        //关闭资源(不建议关闭，建议一直监听消息)
        //channel.close();
        //connection.close();
    }
}
