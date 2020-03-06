package com.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysInterceptor2 implements HandlerInterceptor {

    /**
     * preHandle：表示在访问Controller类之前，进行拦截
     *   应用场景：权限控制（判断当前操作是否有权限）
     *          如果有权限放行，return ture；同时指定错误页面，此时拦截器的错误页面会响应
     *          如果没有权限不能放行，return false；
     *              * 使用转发或者重定向，指定错误页面
     *
     * */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("拦截器【2】在访问Controller类之前执行...");
        //request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request,response);
        return true; // 放心
    }

    /**
     * postHandle：表示在访问Controller类之后执行
     * */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("拦截器【2】在访问Controller类之后执行...，在访问success.jsp之前");
        //request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request,response); 访问Controller不会使用转发和重定向
    }


    /**
     * afterCompletion：访问success.jsp之后
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 @Nullable Exception ex) throws Exception {
        System.out.println("拦截器【2】在访问success.jsp之后");
    }
}
