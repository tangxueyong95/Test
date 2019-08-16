package com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Clock;
import java.util.Map;

@WebServlet("/1")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决请求参数的中文乱码问题
        request.setCharacterEncoding("UTF-8");
        Map<String, String[]> m = request.getParameterMap();
        User a = Work_01.a(m);
        if (a != null) {
            request.setAttribute("user",a);
            request.getRequestDispatcher("2.jsp").forward(request,response);
        }else {
            String s = "姓名或年龄错误";
            request.setAttribute("s",s);
            request.getRequestDispatcher("1.jsp").forward(request,response);
        }
    }
}