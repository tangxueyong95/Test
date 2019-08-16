package com.day_09.扩展题;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Work_03 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {


        FileOutputStream fos1 = new FileOutputStream("F:\\我的代码\\idea2\\src\\student.txt");
        ObjectOutputStream fos = new ObjectOutputStream(fos1);
        ObjectInputStream fis = new ObjectInputStream(new FileInputStream("F:\\我的代码\\idea2\\src\\student.txt"));
        ArrayList<Student> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            System.out.println("姓名：");
            String s = sc.next();
            student.setName(s);
            System.out.println("年龄：");
            int a = sc.nextInt();
            student.setAge(a);
            list.add(student);
        }
        fos.writeObject(list);
        System.out.println(fis.readObject());
        fos.close();
        fis.close();
    }
}

class Student implements Serializable {
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
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

    @Override
    public String toString() {
        return "姓名：" + name + ",年龄：" + age;
    }
}
