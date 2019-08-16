package com.day6;

//动物类
public class Animal {
    private String name;
    private int age;

    public Animal() {
    }                    //无参的构造方法

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }      //有两个参数的构造方法


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void eat() {
        System.out.println("吃东西");
    }
}
