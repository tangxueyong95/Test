package com.example.test.dao;

import com.example.test.entity.DeviceEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface DeviceDao {

    Map<String, Object> getDevicemessage(String imei);

    void registerNb(DeviceEntity var1);

    void register(DeviceEntity var1);

    void delete();

    void deleteNB();

    void upStatus(@Param("imei") String imei, @Param("status") String status);

    void upNBStatus(@Param("imei") String imei, @Param("status") String status);

    void deviceAdd(Map param);

    void deviceAddNB(Map param);
}
