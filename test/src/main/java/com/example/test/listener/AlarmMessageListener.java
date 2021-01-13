//package com.example.test.listener;
//
//import com.alibaba.fastjson.JSON;
//import com.example.test.entity.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
////@RabbitListener(queues = {"topic_queue_alarm"})
//@Component
//public class AlarmMessageListener {
//
//    /****
//     * 消息监听
//     * @param msg
//     */
//    @RabbitHandler
//    public void alarmMessage(String msg){
//        //获取消息，并且将消息转成Message
//        Message message = JSON.parseObject(msg, Message.class);
//
//        //判断一下，是否是修改操作
//        if(message.getCode()==1){
//            Map content = (Map)message.getContent();
//            System.out.println("id="+content.get("id"));
//            System.out.println("type="+content.get("type"));
//        }
//    }
//}
