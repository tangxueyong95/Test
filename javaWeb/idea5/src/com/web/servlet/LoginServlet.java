package com.web.servlet;

import com.domain.User;
import com.service.ContactService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        String checkCode = request.getParameter("checkCode");
        String code = (String) session.getAttribute("code");
        ContactService service = new ContactService();
        Cookie c = new Cookie("name", name);
        Cookie p = new Cookie("password",password);
        c.setPath(request.getContextPath());
        if ("on".equals(remember)) {
            c.setMaxAge(7 * 24 * 60 * 60);
            p.setMaxAge(7 * 24 * 60 * 60);
        } else {
            c.setMaxAge(0);
            p.setMaxAge(0);
        }
        response.addCookie(c);
        response.addCookie(p);
        if (code.equalsIgnoreCase(checkCode)) {
            try {
                User user = service.longinContact(name, password);
                session.setAttribute("user", user);
                response.sendRedirect("index.jsp");
            } catch (RuntimeException e) {
                //登录失败
                String msg = e.getMessage();
                //使用request域对象存数据
                request.setAttribute("msg", msg);
                //request域对象存值，只能和请求转发一起使用
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            String errorMsg = "验证码错误";
            //使用request域对象存数据
            request.setAttribute("msg", errorMsg);
            //request域对象存值，只能和请求转发一起使用
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}