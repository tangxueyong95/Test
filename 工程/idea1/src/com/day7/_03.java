package com.day7;

import java.util.ArrayList;

public class _03 {
    public static void main(String[] args) {
        ArrayList<Worker> A = new ArrayList<>();
        Worker a1 = new Worker("凤姐", 18, 20000);
        Worker a2 = new Worker("欧阳锋", 60, 8000);
        Worker a3 = new Worker("刘德华", 40, 90000);
        Worker a4 = new Worker("刘德华", 41, 50000);
        Worker a5 = new Worker("刘德华", 30, 60000);//创建Worker类
        A.add(a1);
        A.add(a2);
        A.add(a3);
        A.add(a4);
        A.add(a5);//添加元素
        remove(A);
        prin(A);
    }

    //删除集合中的包含"刘德华"的元素
    public static void remove(ArrayList<Worker> a) {
        for (int i = 0; i < a.size(); i++) {
            Worker j = a.get(i);
            String b = j.getName();
            if (b.equalsIgnoreCase("刘德华")) {
                a.remove(i);
                i--;
            }
        }
    }

    public static void prin(ArrayList<Worker> a) {
        for (int i = 0; i < a.size(); i++) {
            Worker j = a.get(i);
            j.work();
        }
    }
}

//Worker类
class Worker {

    private String name;
    private int age;
    private double salary;

    public Worker() {

    }

    public Worker(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getage() {
        return age;
    }

    public void setage(int age) {
        this.age = age;
    }

    public double getsalary() {
        return salary;
    }

    public void setsalary(double salary) {
        this.salary = salary;
    }

    public void work() {
        System.out.println(name + " " + age + " " + salary);//输出名字，年龄，工资
    }

    /*@Override
    public String toString() {
        return "姓名:" + name + "\t年龄:" + age + "\t工资:" + salary;
    }*/
}
