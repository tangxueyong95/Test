package com.domain;

public class User {
    private Integer id;
    private String name;
    private String password;
    private Integer number;
    private String time;
    private Integer suo;
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", number=" + number +
                ", time='" + time + '\'' +
                ", suo=" + suo +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getSuo() {
        return suo;
    }

    public void setSuo(Integer suo) {
        this.suo = suo;
    }
}
