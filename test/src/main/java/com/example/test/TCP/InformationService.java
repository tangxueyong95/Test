package com.example.test.TCP;

import com.example.test.dao.AAnalogValueDeviceDao;
import com.example.test.dao.DeviceDao;
import com.example.test.dao.EventDao;
import com.example.test.dao.FaultDao;
import com.example.test.entity.AAnalogValueDevice;
import com.example.test.entity.AEvent;
import com.example.test.entity.AFault;
import com.example.test.service.EventService;
import com.example.test.service.FaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service("InformationService")
public class InformationService {

    @Autowired
    EventService eventService;

    @Autowired
    FaultService faultService;

    @Autowired
    DeviceDao deviceDao;

    @Autowired
    EventDao eventDao;

    @Autowired
    FaultDao faultDao;

    /**
     * 信息入库
     *
     * @param type 类型
     * @param time 时间
     */
    public void dataProcess(String imei, int type, String time) {
        int statustypeid = 0;
        // type：1正常，2火警，4故障，8主电故障，16备电故障，32通信信道故障，64连接线路故障
        if (type == 1) {
            return;
        }
        if (type == 3) {  //1+2
            eventService.insertEvent(imei, time);
            return;
        }
        if (type == 5) {   //1+4
            statustypeid = 2;
        }
        if (type == 13 || type == 257) { //1+4+8 256+1 建筑消防设施系统主电故障
            statustypeid = 6;
        }
        if (type == 21 || type == 513) { //1+4+16 512+1 建筑消防设施系统备电故障
            statustypeid = 7;
        }
        if (type == 37 || type == 1025) { //1+4+32 1024+1 建筑消防设施系统总线故障
            statustypeid = 18;
        }
        if (type == 69) { //1+4+64
            statustypeid = 29;
        }
        if (statustypeid == 0) {
            return;
        }
        faultService.insertFault(imei, statustypeid);
    }

    @Autowired
    AAnalogValueDeviceDao valueDeviceDao;

//    public void dataProcess(Map<String,String> map) {
//        String imei = map.get("imei");
//        String time = map.get("time");
//        String binStr = map.get("binStr"); //二进制数据 告警
//        String A0 = map.get("A0"); //信号强度
//        String A1 = map.get("A1"); //电池电压
//        String A2 = map.get("A2"); //温度
//        String A3 = map.get("A3"); //水压、水位值
//        AAnalogValueDevice valueDevice = new AAnalogValueDevice();
//        valueDevice.setImei(imei);
//        valueDevice.setAnalogStatusId(17);
//        valueDevice.setValue(A0);
//        valueDevice.setAnalogValueTypeId(130);//信号强度(ASU)
//        //根据imei号获取设备d_device中的deviceid,companyid
//        Map<String, Object> device = null;
//        try {
//            device = valueDeviceDao.findByImei(imei);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("数据出错，该设备号:" + imei + "具有2以上个设备");
//        }
//        if (StringUtils.isEmpty(device)) {
//            // todo 抛出异常，未知设备号
//            throw new RuntimeException("没有找到该设备 imei :" + imei);
//        }
//        valueDevice.setDeviceid((Integer) device.get("deviceid"));
//        valueDevice.setCompanyid((Integer) device.get("companyid"));
//        //上传数据
//        valueDeviceDao.save(valueDevice);
//        valueDevice.setValue(A1);
//        valueDevice.setAnalogValueTypeId(8);//电池电压(V)
//        valueDeviceDao.save(valueDevice);
//        valueDevice.setValue(A2);
//        valueDevice.setAnalogValueTypeId(3);//温度(°C)
//        valueDeviceDao.save(valueDevice);
//        valueDevice.setValue(A3);
//        int devicetypeid = (Integer)device.get("devicetypeid");
//        if (devicetypeid==206){ //液压
//            valueDevice.setAnalogValueTypeId(4);//水压(MPa)
//        }
//        if (devicetypeid==204){ //液位
//            valueDevice.setAnalogValueTypeId(2);//水压(m)
//        }
//        valueDeviceDao.save(valueDevice);
//        int type=0;
//        char[] chars = binStr.toCharArray();
//        for (int i = 0; i < chars.length; i++) {
//            if (i==0){
//                if (chars[i]=='1'){
//                    type=9;
//                    eventService.insertEvent(imei,time,type);
//                }
//            }
//            if (i==1){
//                if (chars[i]=='1'){
//                    type=4;
//                    eventService.insertEvent(imei,time,type);
//                }
//            }
//            if (i==2){
//                if (chars[i]=='1'){
//                    type=2012;
//                    eventService.insertEvent(imei,time,type);
//                }
//            }
//        }
//    }

    public void dataProcess(Map<String, Object> map) {
        String imei = (String) map.get("imei");
        String time = (String) map.get("time");
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("mapList");
        Integer electricity = (Integer) map.get("electricity"); //电池电量
        String signalStrength = String.valueOf(map.get("signalStrength")); //信号强度
        AAnalogValueDevice valueDevice = new AAnalogValueDevice();
        valueDevice.setImei(imei);
        valueDevice.setAnalogStatusId(17);
        //根据imei号获取设备d_device中的deviceid,companyid
        Map<String, Object> device = null;
        try {
            device = valueDeviceDao.findByImei(imei);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据出错，该设备号:" + imei + "具有2以上个设备");
        }
        if (StringUtils.isEmpty(device)) {
            // todo 抛出异常，未知设备号
            throw new RuntimeException("没有找到该设备 imei :" + imei);
        }
        valueDevice.setDeviceid((Integer) device.get("deviceid"));
        valueDevice.setCompanyid((Integer) device.get("companyid"));
        valueDevice.setValue(String.valueOf(electricity));
        valueDevice.setAnalogValueTypeId(135);//电池电量(%)
        valueDeviceDao.save(valueDevice);
        valueDevice.setValue(signalStrength);
        valueDevice.setAnalogValueTypeId(130);//信号强度(ASU)
        valueDeviceDao.save(valueDevice);
        for (Map<String, Object> objectMap : mapList) {
            String deviceStatus = (String) objectMap.get("deviceStatus");
            int status = getStatus(deviceStatus);
            int valueType = (Integer) objectMap.get("valueType");
            int valueType1 = getValueType(valueType);
            double value = (Double) objectMap.get("value");
            if (valueType1==0){
                continue;
            }
            valueDevice.setAnalogStatusId(status);
            valueDevice.setValue(String.valueOf(value));
            valueDevice.setAnalogValueTypeId(valueType1);
            valueDeviceDao.save(valueDevice);
            if (status==1||status==26||status==44){
                AEvent event = new AEvent();
                event.setDeviceid((Integer) device.get("deviceid"));
                event.setCompanyid((Integer) device.get("companyid"));
                event.setDevicename((String) device.get("devicename"));
                event.setDetectorname((String) device.get("devicename"));
                if (status==1){
                    event.setEventcontent("火警");
                    event.setAlarmtypeid(1);
                }
                if (status==26){
                    event.setEventcontent("水压过低");
                    event.setAlarmtypeid(16);
                }
                if (status==44){
                    event.setEventcontent("水压过高");
                    event.setAlarmtypeid(15);
                }
                event.setStarttime(time);
                event.setEventstatus(0);
                event.setImei(imei);
                eventDao.insertEvent(event);
            }
            if (status==2||status==68){
                AFault aFault = new AFault();
                aFault.setDeviceid((Integer) device.get("deviceid"));
                aFault.setCompanyid((Integer) device.get("companyid"));
                aFault.setDevicename((String) device.get("devicename"));
                aFault.setDetectorname((String) device.get("devicename"));
                aFault.setDevicecode(imei);
                aFault.setStatustypeid(status);
                if (status==2){
                    aFault.setContent("故障");
                }
                if (status==68){
                    aFault.setContent("被撞到");
                }
                aFault.setStatus(0);
                aFault.setImei(imei);
                faultDao.insertFault(aFault);
            }
        }
    }

    //更新设备状态为在线
    public void loginStatus(String imei) {
        deviceDao.upStatus(imei, "17");
        deviceDao.upNBStatus(imei, "17");
    }

    //替换设备状态
    public int getStatus(String deviceStatus) {
        //正常
        if ("0".equals(deviceStatus)) {
            return 17;
        }
        //低压
        if ("1".equals(deviceStatus)) {
            return 26;
        }
        //高压
        if ("2".equals(deviceStatus)) {
            return 44;
        }
        //故障
        if ("3".equals(deviceStatus)) {
            return 2;
        }
        //报警
        if ("4".equals(deviceStatus)) {
            return 1;
        }
        //碰撞
        if ("7".equals(deviceStatus)) {
            return 68;
        }
        return 17;
    }

    //替换模拟量的类型
    public int getValueType(int valueType) {
        //压力MPa
        if (valueType==1) {
            return 4;
        }
        //压力Bar(数据以转换为MPa)
        if (valueType==2) {
            return 4;
        }
        //压力KPa(数据以转换为MPa)
        if (valueType==3) {
            return 4;
        }
        //温度℃
        if (valueType==4) {
            return 3;
        }
        //液位M
        if (valueType==5) {
            return 2;
        }
        //流量m³/h
        if (valueType==6) {
            return 15;
        }
        return 0;
    }
}