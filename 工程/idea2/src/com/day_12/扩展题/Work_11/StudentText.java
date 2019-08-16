package com.day_12.扩展题.Work_11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StudentText {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("谢霆锋", 85));
        list.add(new Student("章子怡", 63));
        list.add(new Student("刘亦菲", 77));
        list.add(new Student("黄晓明", 33));
        list.add(new Student("岑小村", 92));
        /*Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getScore() - o2.getScore();
            }
        });*/
//        Collections.sort(list,(a,b)->a.getScore()-b.getScore());
        Collections.sort(list, Comparator.comparingInt(Student::getScore));
        System.out.println(list);
    }
}
