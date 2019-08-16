package com.day6;

//猫类
public class Cat extends Animal {           //继承Animal的成员变量和eat()方法
    public Cat() {
    }

    public Cat(String name, int age) {
        super(name, age);
    }       //super调用父类Animal的构造方法
}