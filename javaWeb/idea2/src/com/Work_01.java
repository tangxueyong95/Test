package com;

import java.util.Map;
import java.util.Set;

public class Work_01 {
    public static User a(Map<String, String[]> map) {
        String name=null;
        String age=null;
        Set<String> set = map.keySet();
        for (String s : set) {
            if ("name".equals(s)) {
                for (String s1 : map.get(s)) {
                    name=s1;
                }
            }
            if("age".equals(s)){
                for (String s1 : map.get(s)) {
                    age=s1;
                }
            }
        }
        return JDBC.b(name,age);
    }
}
