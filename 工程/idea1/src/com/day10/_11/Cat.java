package com.day10._11;

import java.util.Scanner;

public class Cat extends Animal implements Sports{
    public Cat() {
    }

    public Cat(String name) {
        super(name);
    }

    @Override
    public void speak(String str){
        System.out.println(getName()+"说："+str);
    }
    @Override
    public void action(){
        System.out.println(getName()+"抓老鼠中！");
    }
    public void goPlay(){
        String str;
        str = new Scanner(System.in).next();
        speak(str);
        action();
    }
}
