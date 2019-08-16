package com.day10._10;

public class Test {
    public static void main(String[] args) {
        TV tv = new TV("TCG");
        RemoteControl rec = new RemoteControl();
        rec.controlTV(tv);
        Phone phone =new Phone();
        System.out.println("===============");
        phone.controlTV(tv);
    }
}
