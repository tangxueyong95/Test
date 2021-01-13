package com.example.test.service.impl;

import com.example.test.dao.DeviceDao;
import com.example.test.dao.EventDao;
import com.example.test.entity.AEvent;
import com.example.test.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service("EventService")
public class EventServiceImpl implements EventService {

    @Autowired
    EventDao eventDao;

    @Autowired
    DeviceDao deviceDao;

    @Override
    public void insertEvent(String imei,String time){
        //根据imei号获取设备d_device中的deviceid,companyid
        Map<String, Object> device = null;
        try {
            device = deviceDao.getDevicemessage(imei);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据出错，该设备号:" + imei + "具有2以上个设备");
        }
        if (StringUtils.isEmpty(device)) {
            // todo 抛出异常，未知设备号
            throw new RuntimeException("没有找到该设备 imei :" + imei);
        }
        AEvent event = new AEvent();
        event.setDeviceid((Integer) device.get("deviceid"));
        event.setCompanyid((Integer) device.get("companyid"));
        event.setDevicename((String) device.get("devicename"));
        event.setDetectorname((String) device.get("devicename"));
        event.setEventcontent("火警");
        event.setStarttime(time);
        event.setAlarmtypeid(1);
        event.setEventstatus(0);
        event.setImei(imei);
        eventDao.insertEvent(event);
    }

    @Override
    public void insertEvent(String imei,String time,int type){
        //根据imei号获取设备d_device中的deviceid,companyid
        Map<String, Object> device = null;
        try {
            device = deviceDao.getDevicemessage(imei);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据出错，该设备号:" + imei + "具有2以上个设备");
        }
        if (StringUtils.isEmpty(device)) {
            // todo 抛出异常，未知设备号
            throw new RuntimeException("没有找到该设备 imei :" + imei);
        }
        AEvent event = new AEvent();
        event.setDeviceid((Integer) device.get("deviceid"));
        event.setCompanyid((Integer) device.get("companyid"));
        event.setDevicename((String) device.get("devicename"));
        event.setDetectorname((String) device.get("devicename"));
        if (type==9){
            event.setEventcontent("电压过低");
        }
        if (type==4){
            event.setEventcontent("阀门被打开");
        }
        if (type==2012){
            event.setEventcontent("设备被拆除");
        }
        event.setStarttime(time);
        event.setAlarmtypeid(type);
        event.setEventstatus(0);
        event.setImei(imei);
        eventDao.insertEvent(event);
    }
}
