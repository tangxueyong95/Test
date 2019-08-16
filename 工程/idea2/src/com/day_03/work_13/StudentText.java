package com.day_03.work_13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StudentText {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();
        Student s1 = new Student("liusan",20,90.0F);
        Student s2 = new Student("lisi",22,90.0F);
        Student s3 = new Student("wangwu",20,99.0F);
        Student s4 = new Student("sunliu",22,100.0F);
        Collections.addAll(list,s1,s2,s3,s4);
       /* Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if (o1.getNumber() == o2.getNumber()) {
                    if (o1.getAge() == o2.getAge()){
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                    return o1.getAge() - o2.getAge();
                }
                return (int) (o2.getNumber() - o1.getNumber());
            }
        });*/
        Collections.sort(list);
        System.out.println(list);
    }
}
