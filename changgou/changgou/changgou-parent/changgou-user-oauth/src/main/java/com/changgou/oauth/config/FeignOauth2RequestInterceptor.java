package com.changgou.oauth.config;

import com.changgou.oauth.util.JwtToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.oauth.config
 ****/
@Configuration
public class FeignOauth2RequestInterceptor implements RequestInterceptor {

    /***
     *
     * 熔断：
     *      1)线程池隔离(屏蔽线程池隔离)
     *          用户请求线程:A线程( RequestContextHolder.getRequestAttributes(): 取用户请求的当前线程对应的容器对象)
     *          feign:额外的线程:B线程(能否获取到A线程的请求信息? 否)
     *
     *      2)信号量隔离(启用信号量隔离)
     *
     * 调用方法前，进行拦截处理
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        //使用RequestContextHolder工具获取request相关变量,获取用户请求的当前线程对应的容器对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            //取出request
            HttpServletRequest request = attributes.getRequest();
            //获取所有头文件信息的key
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    //头文件的key
                    String name = headerNames.nextElement();
                    //头文件的value
                    String values = request.getHeader(name);
                    //将令牌数据添加到头文件中
                    template.header(name, values);
                }
            }
        }

        //添加管理员令牌实现
        template.header("Authorization","Bearer "+ JwtToken.adminJwt());
    }
}
