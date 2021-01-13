package com.example.test.entity;

import lombok.Data;

import java.util.Date;

@Data
/**
 * (AAnalogValueDevice)实体类
 *
 * @author Pop
 * @since 2019-09-24 19:40:33
 */
public class AAnalogValueDevice {

    //设备运行日志表    
    private Integer id;
        
    private Integer deviceid;
        
    private String value;

    private String imei;
        
    private Date createtime;
        
    private String channelno;
        
    private String partunitloopcode;
        
    private Integer analogValueTypeId;
        
    private Integer analogStatusId;
        
    private Integer companyid;
        
    private Integer projectid;


}