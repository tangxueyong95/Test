package com.java.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/17 11:27
 * @Description: com.java.filter
 * 名字=Prefix+GatewayFilterFactory
 * 构造函数必须写：Prefix+GatewayFilterFactory
 * 内部内：Config
 * 当前处理字段排序方法:shortcutFieldOrder
 * 处理方法：apply
 ****/
@Component
public class MyParamGatewayFilterFactory extends AbstractGatewayFilterFactory<MyParamGatewayFilterFactory.Config> {

    /**
     * 定义需要处理的参数
     */
    public static final String PARAM_NAME = "name";

    /****
     * 处理参数排序方法
     */
    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                //需要处理的过程
                String name = exchange.getRequest().getQueryParams().getFirst("name");
                if(!StringUtils.isEmpty(name)){
                    System.out.println("Filter:"+name);
                }
                return chain.filter(exchange);
            }
        };
    }

    /***
     * 处理字段的排序
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(PARAM_NAME);
    }

    /****
     * 构造函数
     */
    public MyParamGatewayFilterFactory(){
        super(MyParamGatewayFilterFactory.Config.class);
    }

    /****
     * 需要处理的参数
     * name：和处理的参数名字保持一致
     */
    public static class Config {
        private String name;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
