package com.web.servlet;


import com.domain.Contact;
import com.service.ContactService;
import org.apache.commons.beanutils.BeanUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决请求参数的乱码问题
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //1.获取客户端提交的所有请求参数
        Map<String, String[]> map = request.getParameterMap();
        //2.将请求参数封装到contact对象中
        Contact contact = new Contact();
        try {
            BeanUtils.populate(contact,map);
            //要修改的信息全部都在contact对象中
            //3.调用业务层的方法，对联系人的信息进行修改
            ContactService service = new ContactService();
            boolean flag = service.updateContact(contact);
            //4.判断是否修改成功
            if (flag) {
                //修改成功。，查询所有联系人信息
                response.sendRedirect("showAll");
            } else {
                response.getWriter().write("修改失败!");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
