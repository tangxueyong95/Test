package com.day_12;

public class A {
    String name = "张三";
    class B{
        String name = "李四";
        public void ScName(){
            String name = "王五";
            System.out.println(name);
            System.out.println(this.name);  //this.name 本类(B)的成员变量name
            System.out.println(A.this.name);    //A.this.name A类本身的成员变量name
        }
    }
}
