package com.day_13.扩展题;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Work_01 {
    public static void main(String[] args) {
        List<String> one = new ArrayList<>();
        one.add("陈玄风");
        one.add("梅超风");
        one.add("陆乘风");
        one.add("曲灵风");
        one.add("武眠风");
        one.add("冯默风");
        one.add("罗玉风");
        List<String> two = new ArrayList<>();
        two.add("宋远桥");
        two.add("俞莲舟");
        two.add("俞岱岩");
        two.add("张松溪");
        two.add("张翠山");
        two.add("殷梨亭");
        two.add("莫声谷");
        Stream<String> o = one.stream().filter(s -> s.length() == 3).limit(6);
        Stream<String> t = two.stream().filter(s -> "张".equals(s)).skip(1);
        Stream<String> concat = Stream.concat(o, t);
        concat.map(Student::new).forEach(System.out::println);
        List<Student> list = concat.map(Student::new).collect(Collectors.toList());//将流转换为Student类型，并保存到一个新集合中
        list.forEach(System.out::println);
    }
}
 class Student {
    private String name;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return " Student {name='" + name + "'}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}