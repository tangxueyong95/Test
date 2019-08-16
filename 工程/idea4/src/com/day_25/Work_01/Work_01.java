package com.day_25.Work_01;

public class Work_01{
    public static <T> T a(Class<T> t) throws IllegalAccessException, InstantiationException {
        return t.newInstance();
    }
}
