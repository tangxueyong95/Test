package com.domain;

/**
 * 包名:com.itheima.domain
 * 作者:Leevi
 * 日期2019-05-13  11:55
 */
public class Contact {
    private Integer id;
    private String name;
    private Integer age;

    public Contact() {
    }

    public Contact(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
