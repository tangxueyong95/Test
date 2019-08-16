package com.day9._06;

public class Hat {
    private int color;
    private int price;
    private String type;

    public Hat() {

    }
    public Hat(int color, int price, String type) {
        this.color = color;
        this.price = price;
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
