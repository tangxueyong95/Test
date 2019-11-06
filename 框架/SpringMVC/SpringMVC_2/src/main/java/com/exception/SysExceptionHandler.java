package com.exception;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// 2：编写异常处理器（springmvc，必须实现异常处理接口）
public class SysExceptionHandler implements HandlerExceptionResolver {

    // 只要服务器端抛出异常，在SysExceptionHandler类中resolveException方法上进行解析
    @Nullable
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {

        // 错误信息
        String message = "";
        if(ex instanceof SysException ){
            SysException sysException = (SysException)ex;
            message = sysException.getMessage();
        }else{
            message = "请联系管理员...";
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message",message);
        // 错误页面
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
