package com.itheima.travel.web.servlet;

import com.itheima.travel.constant.Constant;
import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.Route;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.IRouteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/route")
public class RouteServlet extends BaseServlet {
    private IRouteService service = (IRouteService) BeanFactory.getBean("routeService");

    /**
     * 处理查询路线详情的请求
     * @param request
     * @param response
     * @return
     */
    private ResultInfo findByRid(HttpServletRequest request,HttpServletResponse response){
        ResultInfo info = new ResultInfo(false);
        //1.获取请求参数
        String rid = request.getParameter("rid");
        try {
            //2.调用业务层的方法，根据rid查询路线详情
            Route route = service.findByRid(rid);
            info.setFlag(true);
            info.setData(route);
        } catch (Exception e) {
            info.setMessage(Constant.SERVSER_ERROR);
        }
        return info;
    }
    /**
     * 处理黑马精选的请求
     * @param request
     * @param response
     * @return
     */
    private ResultInfo careChoose(HttpServletRequest request,HttpServletResponse response){
        ResultInfo info = new ResultInfo(false);
        try {
            //调用service层的方法，获取黑马精选的信息
            Map<String,List<Route>> map = service.careChoose();
            info.setFlag(true);
            info.setData(map);
        } catch (Exception e) {
            info.setMessage(Constant.SERVSER_ERROR);
        }
        return info;
    }

    /**
     * 分页查看某类路线
     * @param request
     * @param response
     * @return
     */
    private ResultInfo findPageByCid(HttpServletRequest request,HttpServletResponse response){
        //1.获取要查询的路线所属的分类的cid
        String cid = request.getParameter("cid");
        //2.获取要查询的当前页数
        Integer currentPage = Integer.valueOf(request.getParameter("currentPage"));
        //3.获取搜索条件
        String keyword = request.getParameter("keyword");

        ResultInfo info = new ResultInfo(false);
        try {
            //3.调用业务层的方法，查询当前页的数据
            PageBean<Route> pageBean = service.findPage(cid,currentPage,keyword);
            info.setFlag(true);
            info.setData(pageBean);
        } catch (Exception e) {
            info.setMessage(Constant.SERVSER_ERROR);
        }
        return info;
    }
}
