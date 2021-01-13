package com.example.test.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceEntity implements Serializable {
    private String versionNo = "v1.0";
    private String inputCharset = "1";
    private String signType = "1";
    protected String remoteAddr;
    private String appId;
    private String unitCode;
    private String operationUnitCode;
    private String imei;
    private String status;
    private String nbDeviceName;
    private String nbDeviceModel;
    private String nbDeviceType;
    private String telecom;
    private String imsi;
    private String installAddress;
    private String lon;
    private String lat;
    private String installTime;
    private String signMsg;
    private Integer producerid;
    private Integer companyid;
    private Integer unitbaseid;
    private Integer deviceid;
}
