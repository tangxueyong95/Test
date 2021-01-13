package com.example.test.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface MqttDao {
    void add(Map map);

    void NBadd(Map map);

    void remove(String imei);

    void NBremove(String imei);

    void alarm(Map map);

    Map getDevice(String imei);

    void malfunction(Map<String, Object> hashMap);
}
