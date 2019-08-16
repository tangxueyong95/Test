package com.itheima.health.pojo;

import java.io.Serializable;
import java.util.Date;

public class OrderSettingList implements Serializable {
    private Integer id;
    private String name;
    private Date orderDate;
    private String orderType;
    private String orderStatus;
    private String setmealName;

    @Override
    public String toString() {
        return "OrderSettingList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orderDate=" + orderDate +
                ", orderType='" + orderType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", setmealName='" + setmealName + '\'' +
                '}';
    }

    public OrderSettingList() {
    }

    public OrderSettingList(Integer id, String name, Date orderDate, String orderType, String orderStatus, String setmealName) {
        this.id = id;
        this.name = name;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.setmealName = setmealName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSetmealName() {
        return setmealName;
    }

    public void setSetmealName(String setmealName) {
        this.setmealName = setmealName;
    }
}
