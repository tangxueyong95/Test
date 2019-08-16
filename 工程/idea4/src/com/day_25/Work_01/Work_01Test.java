package com.day_25.Work_01;

public class Work_01Test {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Student a = Work_01.a(Student.class);
        a.setName("张三");
        a.setAge(18);
        System.out.println(a);
    }
}
