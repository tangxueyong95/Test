package com.itheima.domain;

/**
 * 包名:com.itheima.domain
 * 作者:Leevi
 * 日期2019-05-25  12:11
 */
public class User {
    private Integer age;
    private String username;
    private String address;
    private String nickname;

    public User() {
    }

    public User(Integer age, String username, String address, String nickname) {
        this.age = age;
        this.username = username;
        this.address = address;
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
