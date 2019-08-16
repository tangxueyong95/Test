package com.web.servlet;


import com.domain.PageBean;
import com.service.ContactService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/page")
public class PageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数currentPage(代表客户端当前需要查询的是第几页数据)
        Integer currentPage = Integer.valueOf(request.getParameter("currentPage"));
        //2.调用业务层的方法，获取PageBean对象
        ContactService service = new ContactService();
        PageBean pageBean = service.findPageBean(currentPage);
        //3.将PageBean存放到域对象
        request.setAttribute("page", pageBean);
        //4.跳转到页面展示
        request.getRequestDispatcher("page.jsp").forward(request, response);
    }
}
