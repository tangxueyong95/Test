package com.day_02.work_09;

import java.util.ArrayList;
import java.util.Collections;

public class StudentText {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();
        Student s1 = new Student("张三", 20, "男");
        Student s2 = new Student("赵四", 30, "男");
        Student s3 = new Student("王五", 40, "男");
        Collections.addAll(list, s1, s2, s3);
        int max = 0;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(max).getAge() < list.get(i).getAge()) {
                max = i;
            }
        }
        list.get(max).setName("小猪佩奇");
        System.out.println(list);
    }
}
