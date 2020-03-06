package com.java.fanout;

import com.java.util.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/***
 *  消息生产者,发送消息->RabbitMQ
 *  RabbitMQ->广播消息模式实现
 */
public class FanoutProducer {

    /***
     * 消息生产者,发送消息->RabbitMQ
     * RabbitMQ->广播消息模式实现
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
        channel.queueDeclare("fanout_queue1",true,false,false,null);
        channel.queueDeclare("fanout_queue2",true,false,false,null);

        /***
         * 创建交换机
         * 1:交换机的名字
         * 2:交换机的类型  DIRECT:定向交换机   FANOUT:广播交换机  TOPIC:通配符交换机
         */
        channel.exchangeDeclare("fanout_exchange", BuiltinExchangeType.FANOUT);

        /***
         * 队列绑定到指定的交换机上
         * 1:需要绑定的队列的名字
         * 2:绑定的交换机
         * 3:路由key->广播模式直接写空即可
         */
        channel.queueBind("fanout_queue1","fanout_exchange","");
        channel.queueBind("fanout_queue2","fanout_exchange","");

        //连续发送10条消息
        for (int i = 0; i <10 ; i++) {  //创建消息
            String message = "hello! shenzhen java! simple message type!Fanout:"+i;

            /**
             * 消息发送
             * 1:交换机->广播模式->需要给指定的交换机发送消息
             * 2:路由参数->使用的是广播消息模式，直接写空即可
             * 3:附加消息参数
             * 4:发送的消息信息
             */
            channel.basicPublish("fanout_exchange","",null,message.getBytes());
        }

        //关闭资源
        channel.close();
        connection.close();
    }

}
