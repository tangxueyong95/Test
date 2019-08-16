package com.day_07.work_11;

import java.util.Arrays;

public class Text {
    public static void main(String[] args) {
        Student[] students = {
                new Student("张三", 90.5F),
                new Student("李四", 70.0F),
                new Student("王五", 75.5F),
        };
        printString(students);
        Arrays.parallelSort(students, ((o1, o2) -> (int) (o2.getScore() - o1.getScore())));
        System.out.println("-------------------");
        printString(students);
    }

    public static void printString(Student[] students) {
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
