package com.itheima.health.service;

import com.itheima.health.entity.Result;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Result submit(Map<String, Object> map) throws Exception;

    Map<String,Object> findById(Integer id) throws Exception;

    List<Map<String, Object>> getSetmealReport();

    Map<String, Object> getBusinessReportData();
}
