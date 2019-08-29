package com.changgou.filter;

import com.changgou.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.filter
 ****/
@Component
public class AuthorizeFilter implements GlobalFilter,Ordered {

    //用户请求参数|头文件|Cookie中令牌参数名字
    private static final String AUTHORIZATION_TOKEN="Authorization";

    /***
     * 全局拦截
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //Request
        ServerHttpRequest request = exchange.getRequest();
        //Response
        ServerHttpResponse response = exchange.getResponse();

        //获取uri
        String path = request.getURI().getPath();

        //①  用户登录，放行   /api/user/login   URLFilter.isAuthorizer(path):false->不需要校验，允许访问  true:需要权限校验
        if(path.startsWith("/api/user/login") || !URLFilter.isAuthorizer(path)){
            return chain.filter(exchange);
        }

        //②其他请求拦截，获取令牌数据
        //a.从参数中获取令牌数据  getFirst("")表示获取某参数中的第一个该参数
        String token = request.getQueryParams().getFirst(AUTHORIZATION_TOKEN);

        //b.参数中如果没有令牌数据，则从header中获取
        boolean hasToken = false;
        if(StringUtils.isEmpty(token)){
            //从header中获取令牌数据
            token = request.getHeaders().getFirst(AUTHORIZATION_TOKEN);
            if(!StringUtils.isEmpty(token)){
                hasToken=true;
            }
        }

        //c.header中没有令牌数据，则从cookie获取
        if(StringUtils.isEmpty(token)){
            HttpCookie cookie = request.getCookies().getFirst(AUTHORIZATION_TOKEN);
            if(cookie!=null){
                token = cookie.getValue();
            }
        }

        //d.没有令牌数据，则直接拦截跳转登录
        if(StringUtils.isEmpty(token)){
            //设置错误状态码
            //response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
            //return response.setComplete();

            //跳转到登录页
            response.setStatusCode(HttpStatus.SEE_OTHER);

            response.getHeaders().set("Location","http://localhost:9001/oauth/login?FROM="+request.getURI());
            return response.setComplete();
        }

        //e.有令牌数据，令牌解码->解码失败->抛出异常
        try {
            //JwtUtil.parseJWT(token);
            //判断头文件中是否有令牌，如果没有，则手动添加令牌
            if(!hasToken){
                //解析成功，将令牌加入到头文件中传入到下一个微服务
                request.mutate().header(AUTHORIZATION_TOKEN,token);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //设置错误状态码
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //f.解码成功，放行
        return chain.filter(exchange);
    }

    /****
     * 当前拦截器执行顺序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
