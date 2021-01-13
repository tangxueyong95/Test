package com.example.test.service;

public interface EventService {
    void insertEvent(String imei,String time);
    void insertEvent(String imei,String time,int type);
}
