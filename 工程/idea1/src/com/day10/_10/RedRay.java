package com.day10._10;

public interface RedRay {
    void controlTV(TV tv);
    default void connecting(){
        System.out.println("外接设备,连接成功, 可以使用红外线");
    }
}
