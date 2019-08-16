package com.day6;

//学生类
public class Student {
    private int chinese;
    private int math;
    private int english;
    public Student() {
    }

    public Student(int chinese, int math, int english) {
        this.chinese = chinese;
        this.math = math;
        this.english = english;
    }

    public int getChinese() {
        return chinese;
    }

    public void setChinese(int chinese) {
        this.chinese = chinese;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getSum(){
       return chinese+math+english;
    }
}
