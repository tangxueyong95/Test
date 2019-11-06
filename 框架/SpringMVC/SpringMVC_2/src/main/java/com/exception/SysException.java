package com.exception;

// 1：定义异常处理类（页面上提示一些错误信息）
public class SysException extends Exception {
    // 错误信息
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    // 设置信息
    public void setMessage(String message) {
        this.message = message;
    }

    // 构造方法（设置信息）
    public SysException(String message) {
        this.message = message;
    }
}
