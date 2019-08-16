package com.day_04;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Work_06 {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "张三丰");
        map.put(2, "周芷若");
        map.put(3, "汪峰");
        map.put(4, "灭绝师太");
        pantMap(map);
        map.put(5,"李晓红");
        map.remove(1);
        map.put(2,"周林");
        pantMap(map);
    }

    public static void pantMap(Map<Integer, String> map) {
        /*for (Integer integer : map.keySet()) {
            System.out.print(integer+","+map.get(integer)+" ");
        }
        System.out.println();*/
        Set<Map.Entry<Integer, String>> set = map.entrySet();
        for (Map.Entry<Integer, String> integerStringEntry : set) {
            System.out.print(integerStringEntry.getKey() + "," + integerStringEntry.getValue() + " ");
        }
        System.out.println();
    }
}
