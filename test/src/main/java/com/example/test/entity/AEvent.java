package com.example.test.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
/**
 * 报警日志表(AEvent)实体类
 *
 * @author Pop
 * @since 2019-09-24 19:40:55
 */
public class AEvent implements Serializable {


    private static final long serialVersionUID = 7601293273673125067L;

    @Id
    private Integer eventid;
    //是否设备自动上传    
    private String autostatus;
    //设备ID
    private String imei;
    
    private Integer deviceid;
    //设备名称    
    private String devicename;
    //探测器ID

    
    private Integer detectorid;
        
    private String detectorname;
        
    private Integer reportuserid;
        
    private String reportusername;
        
    private String eventcontent;
    //事件类别的具体类型：火警/误报    
    private Integer eventtypeid;
    //事件是否结束    
    private Integer eventstatus;
    //报警时间    
    private String starttime;
    //确认时间    
    private String endtime;
    //响应时间    
    private Integer responsetime;
    //确认人id    
    private Integer cofirmuserid;
    //确认人名字    
    private String confirmusername;
        
    private String imageurl;
        
    private String videourl;
        
    private String audiourl;
    //处理意见    
    private String handlingsuggestion;
    //报警地址    
    private String eventaddress;
    //模拟值    
    private String analogvalue;
        
    private Integer analogvaluetypeid;
    //视频报警--视频通道编号    
    private String channelnum;
    //复位时间    
    private String resettime;
    //所属设备id    
    private Integer firesystemid;
    //报警类型    
    private Integer alarmtypeid;
        
    private Integer buildingid;
    //复位状态    
    private Integer resetstatus;
        
    private Integer workflowid;
        
    private String systemtime;
    //是否已处理    
    private String ishandled;
        
    private String exteriorevnetid;
        
    private String remarks;
    //单位id    
    private Integer companyid;
        
    private String confirmsource;
    //非设备故障类型 1油烟误报 2吸烟 3水蒸气 4粉尘 5其他    
    private Integer falsealarmid;
        
    private String platformid;
        
    private String synchtime;
        
    private String updatetime;
        
    private String createtime;
    //是否为故障    
    private Integer isfault;
        
    private String erasure;
        
    private String dealuserid;
        
    private String associatedid;
        
    private String filepath;
        
    private String send;

    private Integer istask;

}