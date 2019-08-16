package com.day_07.work_11;

public class Student {
    private String name;
    private Float score;

    public Student() {
    }

    public Student(String name, Float score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return name + ' ' + score;
    }
}
