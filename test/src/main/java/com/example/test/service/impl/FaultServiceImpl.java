package com.example.test.service.impl;

import com.example.test.dao.DeviceDao;
import com.example.test.dao.FaultDao;
import com.example.test.entity.AFault;
import com.example.test.service.FaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service("FaultService")
public class FaultServiceImpl implements FaultService {

    @Autowired
    FaultDao faultDao;
    @Autowired
    DeviceDao deviceDao;

    @Override
    public void insertFault(String imei,Integer type){
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
        AFault aFault = new AFault();
        aFault.setDeviceid((Integer) device.get("deviceid"));
        aFault.setCompanyid((Integer) device.get("companyid"));
        aFault.setDevicename((String) device.get("devicename"));
        aFault.setDetectorname((String) device.get("devicename"));
        aFault.setDevicecode(imei);
        aFault.setStatustypeid(type);
        aFault.setContent("故障");
        aFault.setStatus(0);
        aFault.setImei(imei);
        faultDao.insertFault(aFault);
    }
}
