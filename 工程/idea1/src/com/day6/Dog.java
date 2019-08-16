package com.day6;

//狗类
public class Dog extends Animal {       //继承Animal的成员变量和eat()方法
    public Dog() {
    }

    public Dog(String name, int age) {
        super(name, age);
    }       //super调用父类Animal的构造方法

    public void lookHome() {
        System.out.println("看家");
    }
}
