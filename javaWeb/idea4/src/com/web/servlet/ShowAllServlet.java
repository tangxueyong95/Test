package com.web.servlet;


import com.domain.Contact;
import com.service.ContactService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/showAll")
public class ShowAllServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //调用业务层的方法，处理查看所有联系人的请求
        ContactService service = new ContactService();
        List<Contact> contacts = service.findAllContacts();
        //将contacts存放到域对象中
        //request域对象?session域对象
        request.setAttribute("list", contacts);//如果业务需求中能够存放到request中，就存放到request
        //跳转到list.jsp
        request.getRequestDispatcher("show.jsp").forward(request, response);
    }
}
