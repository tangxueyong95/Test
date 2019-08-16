package com.day_25.Work_02;

import com.day_25.Work_01.Student;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.Set;

public class Work_02 {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = Work_02.class.getClassLoader().getResourceAsStream("a.properties");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
        Properties properties = new Properties();
        properties.load(inputStream);
        String className = properties.getProperty("className");
        Class c = Class.forName(className);
        Object o = c.newInstance();
        Set<String> set = properties.stringPropertyNames();
        for (String s : set) {
            if (s.equals("className")) {
                continue;
            }
            String s1 = properties.getProperty(s);
            Field f = c.getDeclaredField(s);
            f.setAccessible(true);
            Class type = f.getType();
            if (type == int.class) {  // 判断成员变量的数据类型是否是int类型
                f.setInt(o, Integer.parseInt(s1));
            } else {
                // 给f对象的赋值
                f.set(o, s1);
            }
        }
        Student student = (Student) o;
        System.out.println(student);
    }
}
