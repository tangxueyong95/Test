package com.day_25.Work_04;

import com.day_25.Work_01.Student;

import java.lang.reflect.Method;

public class MyAnnotationTest {

    @MyAnnotation(name = "张三", age =18 )
    public static void main(String[] args) throws NoSuchMethodException {
        Class c = MyAnnotationTest.class;
        Method method = c.getMethod("main", String[].class);
        MyAnnotation a= method.getAnnotation(MyAnnotation.class);
        Student student = new Student();
        student.setName(a.name());
        student.setAge(a.age());
        System.out.println(student);
    }
}
