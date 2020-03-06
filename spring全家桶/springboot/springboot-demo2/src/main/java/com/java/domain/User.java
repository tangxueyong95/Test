package com.java.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 10:23
 * @Description: com.java.domain
 ****/
@Component
@ConfigurationProperties(prefix = "user")   //前缀和application.yml中的key保持一致
public class User {

    private Integer id;
    private String address;
    private String name;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
