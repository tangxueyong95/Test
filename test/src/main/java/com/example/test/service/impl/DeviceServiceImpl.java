package com.example.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.test.dao.DeviceDao;
import com.example.test.entity.DeviceEntity;
import com.example.test.service.DeviceService;
import com.example.test.util.DateUtils;
import com.example.test.util.HttpWithJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("DeviceService")
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    DeviceDao deviceDao;

    public void deviceAdd(Integer number) {
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setUnitbaseid(1251); //单位fcm_unit_basic表fcm_unit_basic_id
        deviceEntity.setCompanyid(7); //单位d_company表companyid
        deviceEntity.setProducerid(43); //供应商fcm_device_suppiler表device_suppiler_id
        deviceEntity.setStatus("17"); //设备状态在线
        deviceEntity.setNbDeviceType("10026"); //设备类型烟感
        deviceEntity.setLon("116.481334958565"); //经度
        deviceEntity.setLat("39.9961702385308"); //纬度
        deviceEntity.setInstallTime("2020-04-26 17:29:18"); //设备安装时间
        this.setDeviceEntity(deviceEntity, number, 20001);
    }

    @Override
    public void deviceDelete() {
        deviceDao.delete();
        deviceDao.deleteNB();
    }

    /**
     *
     * @param deviceEntity 设备基本信息
     * @param n 添加设备数量
     * @param m 设备初始序号
     */
    public void setDeviceEntity(DeviceEntity deviceEntity, int n, int m) {
        for(int i = 0; i < n; ++i) {
            int t = m + i;
            deviceEntity.setImei("8677260342" + t + "9"); //设备编号
            this.deviceDao.register(deviceEntity);
            this.deviceDao.registerNb(deviceEntity);
        }

    }

    @Override
    public void deviceImport(Map<String, Object> map) {
        int companyid = (Integer)map.get("companyid");
        int unit_base_id = (Integer)map.get("unit_base_id");
        int producerid = (Integer)map.get("producerid");
        map.put("producer_id",producerid);
        String installperson = (String)map.get("installperson");
        map.put("installer",installperson);
        map.put("devicetypeid",212);
        map.put("nb_device_type",212);
        ZhiAnFireCocksynchronous(map);
    }

    public void ZhiAnFireCocksynchronous(Map param) {
        String url ="http://interface.zhiandakjslt.com/api/Device/QueryDevice";
        Map<String,Object> map = new HashMap<>();
        map.put("PageIndex",0);
        map.put("PageSize",100);
        List<Map<String,Object>> Query = new ArrayList<>();
//        Map<String,Object> map1 = new HashMap<>();
//        map1.put("Key","DeviceIMEI");
//        map1.put("Value",param.get("imei"));
//        map1.put("Opertion","Equal");
//        Query.add(map1);
        map.put("Query",Query);
        String json = JSON.toJSONString(map);
        JSONArray jsa = HttpWithJSON.httpPostWithJSON(url,json);
        System.err.println(jsa);
        for (Object o : jsa) {
            Map<String, Object> map2 = (Map<String, Object>) o;
            ZhiAnFireCockData(param,map2);
            this.deviceDao.deviceAdd(param);
            this.deviceDao.deviceAddNB(param);
        }
//        Map<String, Object> map2 = (Map<String, Object>) jsa.get(0);
    }

    public void ZhiAnFireCockData(Map param,Map map) {
        int status = (Integer)map.get("Status");
        if (status==0){  //正常
            param.put("device_status",17);
            param.put("statustypeid",17);
        }
        if (status==1){  //离线
            param.put("device_status",28);
            param.put("statustypeid",28);
        }
        if (status==2){  //警告
            param.put("device_status",1);
            param.put("statustypeid",1);
        }
        param.put("platformid",map.get("Id"));
        param.put("ext",map.get("Id"));
        param.put("systemAddress",map.get("DeviceAddress"));
        param.put("device_name",map.get("DeviceName"));
        param.put("devicename",map.get("DeviceName"));
        param.put("devicecode",map.get("DeviceIMEI"));
        param.put("sn_code",map.get("DeviceIMEI"));
        param.put("latitude",map.get("Latitude"));
        param.put("lat",map.get("Latitude"));
        param.put("longitude",map.get("Longitude"));
        param.put("lng",map.get("Longitude"));
        String dateCreated = DateUtils.setDate1((String) map.get("DateCreated"));
        param.put("install_time",dateCreated);
        param.put("registertime",dateCreated);
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyid",427);
        map.put("unit_base_id",1846);
        map.put("producerid",58);
        map.put("installperson","tang");
        new DeviceServiceImpl().deviceImport(map);
    }
}
