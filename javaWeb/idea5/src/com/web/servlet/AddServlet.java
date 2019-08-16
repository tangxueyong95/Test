package com.web.servlet;

import com.service.ContactService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add")
public class AddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        ContactService service = new ContactService();
        boolean b = service.addUser(name,password);
        if(!b){
            String errorMsg = "添加失败";
            //使用request域对象存数据
            request.setAttribute("msg", errorMsg);
            //request域对象存值，只能和请求转发一起使用
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        response.sendRedirect("login.jsp");
    }
}