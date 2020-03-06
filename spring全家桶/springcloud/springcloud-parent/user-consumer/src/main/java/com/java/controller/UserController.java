package com.java.controller;

import com.java.domain.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/15 10:26
 * @Description: com.java.controller
 ****/
@RestController
@DefaultProperties(defaultFallback = "defaultFailBack")
@RequestMapping(value = "/consumer")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    //发现服务,注入所有拉取的服务列表信息
    @Autowired
    private DiscoveryClient discoveryClient;

    /****
     * 所有方法都会使用该方法处理异常信息
     * 默认处理方法
     */
    public User defaultFailBack(){
        User user = new User();
        user.setUsername("熔断线流处理了!!!！");
        return user;
    }

    /***
     * http://localhost:18082/consumer/2
     * @return
     */
    //@HystrixCommand(fallbackMethod = "failBack")
    @HystrixCommand
    @RequestMapping(value = "/{id}")
    public User findById(@PathVariable(value = "id")Integer id){
        //拼接组装一个地址
        //String url = "http://localhost:18081/user/"+id;
        //使用RestTemplate调用user-provider   http://localhost:18081/user/2
        //return restTemplate.getForObject(url,User.class);

        //获取指定服务的信息
        //List<ServiceInstance> instances = discoveryClient.getInstances("user-provider");
        //只有1个服务
        //ServiceInstance serviceInstance = instances.get(0);

        //拼接组装一个地址
        //String url = "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/user/"+id;
        //使用RestTemplate调用user-provider   http://localhost:18081/user/2
        //return restTemplate.getForObject(url,User.class);


        //拼接组装一个地址,通过服务名字访问:user-provider
        String url = "http://user-provider/user/"+id;
        //使用RestTemplate调用user-provider
        return restTemplate.getForObject(url,User.class);
    }


    /****
     * 默认处理方法
     */
    public User failBack(Integer id){
        User user = new User();
        user.setId(id);
        user.setUsername("熔断线流处理了！");
        return user;
    }

}
