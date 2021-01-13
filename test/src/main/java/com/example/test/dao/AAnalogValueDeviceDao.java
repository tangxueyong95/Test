package com.example.test.dao;


import com.example.test.entity.AAnalogValueDevice;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface AAnalogValueDeviceDao {
    Map<String, Object> findByImei(String imei);

    void save(AAnalogValueDevice aAnalogValueDevice);
}