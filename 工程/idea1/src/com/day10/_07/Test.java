package com.day10._07;

public class Test {
    public static void main(String[] args) {
        Brush b1 = new Brush(5.8f,"红色");
        Pencil p1 = new Pencil(2.5f,"黑色");
        Painter painter = new Painter("王流秋");
        painter.draw(b1);
        painter.draw(p1);
    }
}
