package com.day10._08;

public class Line {
    private String emplymentClass;
    private int money;

    public Line(String 黑马班, String s) {
    }

    public Line(String emplymentClass, int money) {
        this.emplymentClass = emplymentClass;
        this.money = money;
    }

    public String getEmplymentClass() {
        return emplymentClass;
    }

    public void setEmplymentClass(String emplymentClass) {
        this.emplymentClass = emplymentClass;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
