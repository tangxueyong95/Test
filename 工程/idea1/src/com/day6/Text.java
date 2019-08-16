package com.day6;

//测试类
public class Text {
    public static void main(String[] args) {
        Phone ph = new Phone("华为荣耀v10", 3500);
        Person pe = new Person("张三", 30, "男");
        Animal An = new Animal("泰迪", 3);
        Dog dog = new Dog("藏獒", 2);
        Cat cat = new Cat("小小", 1);
        Student st = new Student(80, 110, 70);      //创建对象
        ph.call();
        ph.sendMessage();
        ph.playGame();
        pe.study();
        pe.sleep();
        An.eat();
        dog.eat();
        dog.lookHome();
        cat.eat();
        System.out.println(st.getSum());
    }
}
