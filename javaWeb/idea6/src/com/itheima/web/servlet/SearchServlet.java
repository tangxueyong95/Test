package com.itheima.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.User;
import com.itheima.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo(true);

        try {
            //1.获取请求参数
            String name = request.getParameter("name");
            //2.调用业务层的方法处理请求
            UserService service = new UserService();
            List<User> list =  service.search(name);
            //3.将数据封装到ResultInfo中
            info.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }

        //将info对象转换成json字符串输出到客户端
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(info);
        response.getWriter().write(jsonStr);
    }
}
