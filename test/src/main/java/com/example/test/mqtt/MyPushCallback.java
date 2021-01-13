package com.example.test.mqtt;

import com.alibaba.fastjson.JSON;
import com.example.test.dao.MqttDao;
import com.example.test.util.DateUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 发布消息的回调类
 *
 * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。
 * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。
 * 在回调中，将它用来标识已经启动了该回调的哪个实例。
 * 必须在回调类中实现三个方法：
 *
 *  public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。
 *
 *  public void connectionLost(Throwable cause)在断开连接时调用。
 *
 *  public void deliveryComplete(MqttDeliveryToken token))
 *  接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。
 *  由 MqttClient.connect 激活此回调。
 *
 */
@Repository
public class MyPushCallback implements MqttCallback {

    @Autowired
    MqttDao mqttDao;

    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        System.out.println("连接断开，可以做重连");
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }

    @Transactional(rollbackFor = {Exception.class})
    public void messageArrived(String topic, MqttMessage message) throws Exception {
//         subscribe后得到的消息会执行到这里面
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息Qos : " + message.getQos());
        System.out.println("接收消息内容 : " + new String(message.getPayload()));
        String[] split = topic.split("/");
        String topicEvent = split[0];
        String josnString = new String(message.getPayload());
        Map<String,Object> map = JSON.parseObject(josnString, Map.class);
        //设备故障
        if("malfunction".equalsIgnoreCase(topicEvent)){
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("imei",map.get("sn"));
            Date date = (Date)map.get("createTime");
            String createTime = DateUtils.getFormatTime(date, "yyyy-MM-dd HH:mm:ss");
            hashMap.put("createtime",createTime);
            List malfunctions = (ArrayList) map.get("malfunctions");
            for (Object malfunction : malfunctions) {
                Map malfunction1 = (Map) malfunction;
                boolean isMalfunction = (boolean) malfunction1.get("isMalfunction");
                if (isMalfunction){
                    Map<String, Object> device = null;
                    try {
                        device = mqttDao.getDevice((String) map.get("sn"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("数据出错，该设备号:"+(String)map.get("sn")+"具有2以上个设备");
                    }
                    if(StringUtils.isEmpty(device)){
                        // todo 抛出异常，未知设备号
                        throw new RuntimeException("没有找到该设备 imei :" + (String)map.get("sn"));
                    }
                    hashMap.put("deviceid",device.get("deviceid"));
                    hashMap.put("devicename",device.get("devicename"));
                    hashMap.put("companyid",device.get("companyid"));
                    mqttDao.malfunction(hashMap);
                }
            }
        }
        //设备预警
        if("alarm".equalsIgnoreCase(topicEvent)){
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("imei",map.get("sn"));
            Date date = (Date)map.get("createTime");
            String createTime = DateUtils.getFormatTime(date, "yyyy-MM-dd HH:mm:ss");
            hashMap.put("createtime",createTime);
            List alarms = (ArrayList) map.get("alarms");
            for (Object alarm : alarms) {
                Map alarm1 = (Map) alarm;
                boolean isAlarm = (boolean)alarm1.get("isAlarm");
                if (isAlarm){
                    Map<String, Object> device = null;
                    try {
                        device = mqttDao.getDevice((String) map.get("sn"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("数据出错，该设备号:"+(String)map.get("sn")+"具有2以上个设备");
                    }
                    if(StringUtils.isEmpty(device)){
                        // todo 抛出异常，未知设备号
                        throw new RuntimeException("没有找到该设备 imei :" + (String)map.get("sn"));
                    }
                    hashMap.put("deviceid",device.get("deviceid"));
                    hashMap.put("devicename",device.get("devicename"));
                    hashMap.put("companyid",device.get("companyid"));
                    mqttDao.alarm(hashMap);
                }
            }
        }
        //设备部署
        if("deploy".equalsIgnoreCase(topicEvent)){
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("imei",map.get("sn"));
            hashMap.put("devicename",map.get("name"));
            List lonlat = (ArrayList) map.get("lonlat");
            hashMap.put("lon",lonlat.get(0));
            hashMap.put("lat",lonlat.get(1));
            int status = (int)map.get("status");
            setStatus(hashMap,status);
            Date date = (Date)map.get("createTime");
            String install_time = DateUtils.getFormatTime(date, "yyyy-MM-dd HH:mm:ss");
            hashMap.put("install_time",install_time);
            hashMap.put("devicetypeid",10026);
            hashMap.put("producerid",43);
            hashMap.put("companyid",510);
            hashMap.put("unit_base_id",0);
            mqttDao.add(hashMap);
            mqttDao.NBadd(hashMap);
        }
        //删除设备
        if("remove".equalsIgnoreCase(topicEvent)){
            String imei = (String) map.get("sn");
            mqttDao.remove(imei);
            mqttDao.NBremove(imei);
        }
        //设备上报数据
        if("report".equalsIgnoreCase(topicEvent)){

        }
    }

    //设备状态处理
    public void setStatus(Map hashMap,int status){
        if (status==0){//预警
            hashMap.put("statustypeid",1);
        }
        if (status==1){//正常
            hashMap.put("statustypeid",17);
        }
        if (status==2){//失联
            hashMap.put("statustypeid",28);
        }
        if (status==3){//未激活
            hashMap.put("statustypeid",28);
        }
        if (status==4){//故障
            hashMap.put("statustypeid",2);
        }
        if (status==5){//故障隔离
            hashMap.put("statustypeid",2);
        }
    }

    public static void main(String[] args) {
        Date date = new Date();
        String createTime = DateUtils.getFormatTime(date, "yyyy-MM-dd HH:mm:ss");
        System.out.println(createTime);
    }
}
