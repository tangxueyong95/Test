package com.day10._07;

public class Pencil extends Pen {
    public Pencil() {
    }

    public Pencil(Float price, String color) {
        super(price, color);
    }

    @Override
    public void write(){
        System.out.println("现在使用的是价格为"+super.getPrice()+"的"+super.getColor()+"颜色的铅笔");
    }
}
