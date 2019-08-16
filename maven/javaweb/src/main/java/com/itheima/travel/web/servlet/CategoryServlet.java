package com.itheima.travel.web.servlet;

import com.itheima.travel.constant.Constant;
import com.itheima.travel.domain.Category;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.ICategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet {
    private ICategoryService service = (ICategoryService) BeanFactory.getBean("categoryService");
    /**
     * 处理查看所有分类信息的请求
     * @param request
     * @param response
     * @return
     */
    private ResultInfo findAll(HttpServletRequest request,HttpServletResponse response){
        ResultInfo info = new ResultInfo(false);
        try {
            //调用业务层的方法，查看所有的分类信息
            /*List<Category> list = service.findAll();*/
            String list = service.findAll();
            //将list封装到ResultInfo对象中
            info.setFlag(true);
            //将list设置到info对象的data属性中
            info.setData(list);
        } catch (Exception e) {
            info.setMessage(Constant.SERVSER_ERROR);
        }
        return info;
    }
}
