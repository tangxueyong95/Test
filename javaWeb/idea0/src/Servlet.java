import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/2")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("你好中国");*/
        //response.sendRedirect("http://www.baidu.com");
        String a = request.getParameter("a");
        //在下载之前，通过"Content-disposition"的响应头让客户端弹出一个下载提示框
        response.setHeader("Content-Disposition", "attachment;filename=" + a);
        ServletContext servletContext = getServletContext();
        //F:\我的代码\javaWeb\idea\web\img
        InputStream is = servletContext.getResourceAsStream("img/0.jpg");
        ServletOutputStream out = response.getOutputStream();
        //边读边写
        //准备一个字节数组
       /* byte[] buffer = new byte[1024];

        //接下来就要进行循环读写了
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            //使用输出流写出去
            out.write(buffer, 0, len);
        }*/

        //将字节输入流中的字节，使用输出流输出出去
        IOUtils.copy(is, out);
        is.close();
        out.close();
    }
}
