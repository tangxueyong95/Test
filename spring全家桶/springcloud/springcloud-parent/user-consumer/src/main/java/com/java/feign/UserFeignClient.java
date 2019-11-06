package com.java.feign;

import com.java.domain.User;
import com.java.feign.fallback.UserFeignFallback;
import com.java.feign.util.LogConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/17 9:00
 * @Description: com.java.feign
 ****/
@FeignClient(
        value = "user-provider",            //指定Feign需要调的服务
        fallback = UserFeignFallback.class, //指定当前Feign接口处理的熔断降级对象
        configuration = LogConfig.class     //指定Feign的配置
)
public interface UserFeignClient {

    /****
     * 要调用的远程方法
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}")
    User findById(@PathVariable(value = "id")Integer id);
}
