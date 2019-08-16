package com.day10._07;

public class Painter {
    private String name;

    public Painter() {
    }

    public Painter(String name) {
        this.name = name;
    }

    void draw(Pen p){
        System.out.print("画家"+name+"正在绘画,");
        p.write();
    }
}
