package com.itheima.travel.domain;

import java.io.Serializable;

/**
 * 用于封装后端返回前端数据对象
 */
public class ResultInfo implements Serializable {
    private boolean flag;//后端响应的数据是否正常
    private Object data;//后端返回结果数据对象
    private String message;//响应的提示信息

    //无参构造方法
    public ResultInfo() {
    }
    public ResultInfo(boolean flag) {
        this.flag = flag;
    }
    /**
     * 有参构造方法
     * @param flag
     * @param message
     */
    public ResultInfo(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }
    /**
     * 有参构造方法
     * @param flag
     * @param data
     * @param message
     */
    public ResultInfo(boolean flag, Object data, String message) {
        this.flag = flag;
        this.data = data;
        this.message = message;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
