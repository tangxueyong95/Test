package com.changgou.listener;

import com.alibaba.fastjson.JSON;
import com.changgou.item.feign.PageFeign;
import entity.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.listener
 ****/
@RabbitListener(queues = {"topic_queue_spu"})
@Component
public class SpuMessageListener {

    @Autowired
    private PageFeign pageFeign;

    /****
     * 消息监听
     * @param msg
     */
    @RabbitHandler
    public void spuMessage(String msg){
        //获取消息，并且将消息转成Message
        Message message = JSON.parseObject(msg,Message.class);

        //判断一下，是否是修改操作
        if(message.getCode()==2){
            try {
                //修改操作，则生成静态页-PageFeing
                pageFeign.createPage(Long.parseLong(message.getContent().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
