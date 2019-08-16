package com.itheima.feign.fallback;

import com.itheima.domain.User;
import com.itheima.feign.UserFeignClient;
import org.springframework.stereotype.Component;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/17 9:44
 * @Description: com.itheima.feign.fallback
 ****/
@Component  //将对象交给SpringIOC容器管理
public class UserFeignFallback implements UserFeignClient {

    /****
     * Feign接口中对应的方法的降级处理方法
     * @param id
     * @return
     */
    @Override
    public User findById(Integer id) {
        User user = new User();
        user.setName("暂停服务！");
        return user;
    }
}
