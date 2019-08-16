package com.itheima.topic;

import com.itheima.util.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/*****
 * @Author: shenzhen-itheima
 * @Date: 2019/7/24 9:53
 * @Description: com.itheima.simple
 *  消息生产者,发送消息->RabbitMQ
 *  RabbitMQ->通配符模式消息模式实现
 ****/
public class TopicProducer {

    /***
     * 消息生产者,发送消息->RabbitMQ
     * RabbitMQ->通配符模式消息模式实现
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
        channel.queueDeclare("topic_queue1",true,false,false,null);
        channel.queueDeclare("topic_queue2",true,false,false,null);

        /***
         * 创建交换机
         * 1:交换机的名字
         * 2:交换机的类型  DIRECT:定向交换机   FANOUT:广播交换机  TOPIC:通配符交换机
         */
        channel.exchangeDeclare("topic_exchange", BuiltinExchangeType.TOPIC);

        /***
         * 队列绑定到指定的交换机上
         * 1:需要绑定的队列的名字
         * 2:绑定的交换机
         * 3:路由key->旅游过滤(Routingkey)模式直接写空即可
         *
         * 所有发给routingkey_exchange的消息如果涉及到item，则给routingkey_queue1和routingkey_queue2队列都发消息
         * 所有发给routingkey_exchange的消息如果涉及到user，则给routingkey_queue2队列都发消息
         */
        channel.queueBind("topic_queue1","topic_exchange","item.add");
        channel.queueBind("topic_queue2","topic_exchange","item.add");
        channel.queueBind("topic_queue2","topic_exchange","user.#");

        //连续发送10条消息
        for (int i = 0; i <10 ; i++) {
            //创建消息
            String message = "hello! shenzhen itheima! simple message type!Fanout:"+i;

            //确认routingkey
            String routingkey = "user.add";  //默认给routingkey_queue2发

            //如果i%2==0  表示item操作   否则表示user操作
            if(i%2==0){
                //给routingkey_queue1和routingkey_queue2发
                message=message+"ITEM---666！";

                //更换routingkey
                routingkey="item.add";
            }else{
                //给routingkey_queue2发
                routingkey="user.delete";
            }

            /**
             * 消息发送
             * 1:交换机->通配符模式
             * 2:路由参数
             * 3:附加消息参数
             * 4:发送的消息信息
             */
            channel.basicPublish("topic_exchange",routingkey,null,message.getBytes());
        }

        //关闭资源
        channel.close();
        connection.close();
    }

}
