package com.java.worker;

import com.java.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/***
 *  消息生产者,发送消息->RabbitMQ
 *  RabbitMQ->工作者消息模式实现
 ****/
public class WorkerProducer {

    /***
     * 消息生产者,发送消息->RabbitMQ
     * RabbitMQ->工作者消息模式实现
     * @param args
     */
    public static void main(String[] args) throws Exception{
        //获取连接对象
        Connection connection = ConnectionUtils.getConnection();

        //创建频道
        Channel channel = connection.createChannel();

        /***
         * 声明队列
         * 1:要发送数据的队列名字
         * 2:是否将消息持久化存储  true:持久化存储  false：不持久化存储
         * 3:当前连接对象是否独占当前队列
         * 4:消息消费后或者使用完毕后，是否自动删除
         * 5:附加数据
         */
        channel.queueDeclare("worker_queue",true,false,false,null);

        //连续发送10条消息
        for (int i = 0; i <10 ; i++) {  //创建消息
            String message = "hello! shenzhen java! simple message type!"+i;

            /**
             * 消息发送
             * 1:交换机->简单模式->默认使用Default Exchange
             * 2:路由参数->使用的是简单消息模式，路由值直接和队列名字保持一致即可
             * 3:附加消息参数
             * 4:发送的消息信息
             */
            channel.basicPublish("","worker_queue",null,message.getBytes());
        }

        //关闭资源
        channel.close();
        connection.close();
    }

}
