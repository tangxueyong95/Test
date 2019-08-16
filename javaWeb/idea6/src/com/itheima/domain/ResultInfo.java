package com.itheima.domain;

/**
 * 包名:com.itheima.domain
 * 作者:Leevi
 * 日期2019-05-21  11:00
 */
public class ResultInfo {
    //data就是响应的主体内容
    private Object data;
    //flag表示服务器是否出现异常
    private boolean flag;
    //errorMsg表示服务器出现的异常信息
    private String errorMsg;

    public ResultInfo() {
    }

    public ResultInfo(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
