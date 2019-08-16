package com.day_03.work_13;

import java.util.Comparator;

public class Student implements Comparable<Student> {
    private String name;
    private int age;
    private float number;

    public Student() {
    }

    public Student(String name, int age, float number) {
        this.name = name;
        this.age = age;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return name + " " + age + " " + number;
    }

    @Override
    public int compareTo(Student o) {
        if (this.number == o.number) {
            if (this.age == o.age){
                return this.name.compareToIgnoreCase(o.name);
            }
            return this.age - o.age;
        }
        return (int) (o.number - this.number);
    }
}
