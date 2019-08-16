package com.day_04;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Work_07 {
    public static void main(String[] args) {
        String[] s1 = {"黑龙江省", "浙江省", "江西省", "广东省", "福建省"};
        String[] s2 = {"哈尔滨", "杭州", "南昌", "广州", "福州"};
        Map<String, String> map = new HashMap<String, String>();
        addMap(map,s1,s2);
        pantMap(map);
    }

    public static void pantMap(Map<String, String> map) {
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry<String, String> integerStringEntry : set) {
            System.out.print(integerStringEntry.getKey() + "=" + integerStringEntry.getValue() + " ");
        }
        System.out.println();
    }

    public static Map<String, String> addMap(Map<String, String> map, String[] s1, String[] s2) {
        for (int i = 0; i < s1.length; i++) {
            map.put(s1[i], s2[i]);
        }
        return map;
    }
}
