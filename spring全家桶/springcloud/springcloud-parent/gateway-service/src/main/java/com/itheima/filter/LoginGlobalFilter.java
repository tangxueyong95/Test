package com.itheima.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/17 11:18
 * @Description: com.itheima.filter
 * 全局过滤器定义，需要实现GlobalFilter、Ordered 接口
 ****/
@Component
public class LoginGlobalFilter implements GlobalFilter,Ordered {

    /****
     * 拦截过滤器实现
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取头文件信息
//        String host = exchange.getRequest().getHeaders().getFirst("Host");
//        System.out.println(host);
//
//        //获取请求参数
//        String token = exchange.getRequest().getQueryParams().getFirst("token");
//        if(StringUtils.isEmpty(token)){
//            //响应状态设置
//            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
//            //拦截用户请求
//            return exchange.getResponse().setComplete();
//        }
//
//        System.out.println("token:"+token);
        //放行
        return chain.filter(exchange);
    }

    /****
     * 执行顺序
     * 返回值越小，越靠前执行
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
