package com.java.feign.fallback;

import com.java.domain.User;
import com.java.feign.UserFeignClient;
import org.springframework.stereotype.Component;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/17 9:44
 * @Description: com.java.feign.fallback
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
