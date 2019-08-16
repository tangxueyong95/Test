package com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/3")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        String remember = request.getParameter("remember");
        String checkCode = request.getParameter("checkCode");
        String code = (String) session.getAttribute("code");
        String name = request.getParameter("name");
        Map<String, String[]> m = request.getParameterMap();
        User a = Work_01.a(m);
        if (code.equalsIgnoreCase(checkCode)) {
            Cookie c = new Cookie("name", name);
            c.setPath(request.getContextPath());
            if ("on".equals(remember)) {
                c.setMaxAge(7 * 24 * 60 * 60);
            } else {
                c.setMaxAge(0);
            }
            response.addCookie(c);
            if (a != null) {
                request.setAttribute("user", a);
                request.getRequestDispatcher("4.jsp").forward(request, response);
            } else {
                String s = "姓名或年龄错误";
                request.setAttribute("s", s);
                request.getRequestDispatcher("3.jsp").forward(request, response);
            }
        } else {
            String errorMsg = "验证码错误";
            //使用request域对象存数据
            request.setAttribute("msg", errorMsg);
            //request域对象存值，只能和请求转发一起使用
            request.getRequestDispatcher("3.jsp").forward(request, response);
        }
    }
}