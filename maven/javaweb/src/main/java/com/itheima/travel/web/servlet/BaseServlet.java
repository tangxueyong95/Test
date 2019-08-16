package com.itheima.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.domain.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户传入的请求参数"action"的值
        String action = request.getParameter("action");
        //2.根据action的值，获取Servlet中的方法，并且调用
        //2.1 获取当前执行的Servlet对象的字节码文件对象
        Class clazz = this.getClass();
        try {
            Method method = clazz.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            //方法可能是私有的,暴力反射
            method.setAccessible(true);
            ResultInfo info = (ResultInfo) method.invoke(this, request, response);
            if (info != null) {
                writeJson(response,info);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    private void writeJson(HttpServletResponse response, ResultInfo info) {
        //将info对象转换成json字符串，响应给客户端
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(info);
            response.getWriter().write(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
