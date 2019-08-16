package com.day_25.Work_03;

import com.day_25.Work_01.Student;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Work_03 {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Map<String,String> map = new HashMap<>();
        map.put("name","张三");
        map.put("age","18");
        Student student = new Student();
        BeanUtils.populate(student,map);
        System.out.println(student);
    }
}
