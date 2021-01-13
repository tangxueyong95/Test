package com.example.test.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
/**
 * 隐患日志表(AFault)实体类
 *
 * @author Pop
 * @since 2019-09-24 19:40:43
 */
public class AFault implements Serializable {


    private static final long serialVersionUID = -5763888893879388390L;

    @Id
    private Integer faultid;
        
    private Integer autostatus;

    private String imei;
        
    private Integer deviceid;
    //设备名称    
    private String devicename;
        
    private Integer detectorid;
    //部件名称    
    private String detectorname;
        
    private Integer reportuserid;
    //上报人名称    
    private String reportusername;
    //隐患内容    
    private String content;
    //设备状态id    
    private Integer statustypeid;
    //修复状态    
    private Integer status;
    //上报时间    
    private String starttime;
    //修复时间    
    private String repairtime;
    //维修响应时间    
    private Integer responsetime;
    //图片url    
    private String imageurl;
    //视频url    
    private String videourl;
    //音频url    
    private String audiourl;
    //故障流程处理状态:0 表示手动添加上报的故障,2 表示故障进入处理流程,100 表示审核回退状态(作用是用来标记)    
    private Integer processstatus;
        
    private Integer workflowid;
    //所属系统id    
    private Integer firesystemid;
    //所属单位id    
    private Integer companyid;
    //所属单位    
    private String companyname;
    //隐患类型id    
    private Integer latentdangertypeid;
    //现场处理内容    
    private String reason;
        
    private Integer eventid;
    //1误报且设备故障    
    private Integer isfault;
    //处理方式1现场解决    
    private Integer localsolution;
    //备注    
    private String remarks;
    //设备串号    
    private String devicecode;
    //通道号    
    private String channelno;
    //回路号    
    private String partunitloopcode;
    //系统地址    
    private String systemaddress;
        
    private String platformid;
        
    private String synchtime;
        
    private Integer alarmtypeid;

    private Integer istask;
}