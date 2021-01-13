package com.example.test.service;

import java.util.Map;

public interface DeviceService {
    void deviceAdd(Integer number);

    void deviceDelete();

    void deviceImport(Map<String, Object> map);
}
