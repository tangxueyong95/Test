package com.day10._09;

public class Man extends People implements Movement {
    public Man() {
    }

    public Man(String name, String country) {
        super(name, country);
    }

    @Override
    public void speak(String str){
        System.out.println("姓名为"+getName()+"的"+getCountry()+"人在说着"+str);
    }
    @Override
    public void exercise(){
        System.out.println(getName()+"正在锻炼中！");
    }
    public void life(){
       speak("中国话");
       exercise();
    }
}
