package com.itheima.travel.web.servlet;

import com.itheima.travel.constant.Constant;
import com.itheima.travel.domain.Favorite;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.IFavoriteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/favorite")
public class FavoriteServlet extends BaseServlet {
    private IFavoriteService service = (IFavoriteService) BeanFactory.getBean("favoriteService");
    /**
     * 判断是否已收藏
     * @param request
     * @param response
     * @return
     */
    private ResultInfo isFavorite(HttpServletRequest request,HttpServletResponse response){
        ResultInfo info = new ResultInfo(false);
        //1.获取客户端传入的rid
        String rid = request.getParameter("rid");
        //2.从session中获取user对象
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Constant.USER_KEY);
            //3.判断当前是否已登录
            if (user != null) {
                //已登录
                //调用业务层的方法，根据uid和rid查询收藏记录
                Favorite favorite = service.findFavoriteByUidAndRid(user,rid);
                //判断favorite是否为null
                if (favorite != null) {
                    //已收藏
                    info.setData(true);//data为true表示已收藏
                }else {
                    //未收藏
                    info.setData(false);//data为false表示未收藏
                }
            }else {
                //未登录，应该显示未收藏状态
                info.setData(false);
            }
            info.setFlag(true);
        } catch (Exception e) {
            info.setMessage(Constant.SERVSER_ERROR);
        }
        return info;
    }

    /**
     * 处理添加收藏的请求
     * @param request
     * @param response
     * @return
     */
    private ResultInfo addFavorite(HttpServletRequest request,HttpServletResponse response){
        ResultInfo info = new ResultInfo(true);
        //1.获取客户端的请求参数rid
        String rid = request.getParameter("rid");
        //2.判断当前用户是否已登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_KEY);
        if (user != null) {
            //已登录
            //调用业务层的方法添加收藏
            Integer count = service.addFavorite(user,rid);//获取到收藏总次数
            //count == null，就说明添加收藏失败
            info.setData(count);
        }else {
            //未登录
            info.setData(-1);
        }
        return info;
    }
    private ResultInfo romeFavorite(HttpServletRequest request,HttpServletResponse response){
        ResultInfo info = new ResultInfo(true);
        //1.获取客户端的请求参数rid
        String rid = request.getParameter("rid");
        //2.判断当前用户是否已登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_KEY);
        if (user != null) {
            //已登录
            //调用业务层的方法取消收藏
            Integer count = service.romeFavorite(user,rid);//获取到收藏总次数
            info.setData(count);
        }else {
            //未登录
            info.setData(-1);
        }
        return info;
    }
}
