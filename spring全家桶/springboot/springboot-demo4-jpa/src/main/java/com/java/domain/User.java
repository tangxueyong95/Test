package com.java.domain;

import javax.persistence.*;
import java.io.Serializable;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 11:01
 * @Description: com.java.domain
 ****/
@Entity //指定当前对象为实体Bean->指定之后，会将它与对应的表进行解析
@Table(name = "user")   //表示当前User对象是属于user表的JavaBean
public class User implements Serializable{

    @Id //表示主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键策略：自增
    private Integer id;

//    @Column(name = "username")
    private String username;
    private String password;
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
