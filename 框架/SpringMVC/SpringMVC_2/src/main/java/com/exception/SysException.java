package com.exception;

/**
 * @ClassName SysException
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/17 11:56
 * @Version V1.0
 */
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
