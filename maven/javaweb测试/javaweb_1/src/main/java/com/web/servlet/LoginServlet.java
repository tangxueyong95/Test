package com.web.servlet;

import com.domain.User;
import com.service.RegisterService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        RegisterService service = new RegisterService();
        User user = service.loginService(name,password);
        System.out.println();
        if(user!=null) {
            request.getSession().setAttribute("user",user);
            /*response.sendRedirect("welcome.html");*/
            response.sendRedirect("welcome.html");
        }
    }
}