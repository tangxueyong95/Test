package com.习题;

public class CS3 {
    public static void main(String[] args) {
        Cat a = new Cat();
        Dog b = new Dog();
        Animal c = new Animal();
        method(a);
        method(b);
        method(c);
    }

    public static void method(Cat A) {
        A.eat();
    }

    public static void method(Dog A) {
        A.eat();
    }

    public static void method(Animal A) {
        A.eat();
    }
}

class Animal {
    public void eat() {
        System.out.println("动物在吃饭");
    }
}

class Cat extends Animal {
    @Override
    public void eat() {
        System.out.println("猫在吃鱼");
    }
}

class Dog extends Animal {
    @Override
    public void eat() {
        System.out.println("狗在吃肉");
    }
}