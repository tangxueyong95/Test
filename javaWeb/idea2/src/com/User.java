package com;

public class User {
    private String NAME;
    private Integer age;

    public User() {
    }

    public User(String name, Integer age) {
        this.NAME = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + NAME + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return NAME;
    }

    public void setName(String name) {
        this.NAME = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
