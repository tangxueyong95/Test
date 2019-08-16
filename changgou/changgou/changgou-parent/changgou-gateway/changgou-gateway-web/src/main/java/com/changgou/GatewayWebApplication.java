package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou
 ****/
@SpringBootApplication
@EnableEurekaClient
public class GatewayWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayWebApplication.class,args);
    }


    /*****
     * 根据用户的IP来进行限流
     * 把用户的IP作为令牌桶的唯一标识符
     */
    @Bean(name = "ipKeyResolver")
    public KeyResolver keyResolver(){
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                //获取用户IP
                String hostName = exchange.getRequest().getRemoteAddress().getHostString();
                return Mono.just(hostName);
            }
        };
    }
}
