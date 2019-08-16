package com.day_04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Work_08 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("abc");
        list.add("bcd");
        HashMap<Character, Integer> map = new HashMap<>();
        for (String s : list) {
            char[] c = s.toCharArray();
            for (Character c1 : c) {
                Integer m = map.get(c1);
                if (m==null) {
                    map.put(c1,1);
                }else {
                    map.put(c1,m+1);
                }
            }
        }
        pantMap(map);
    }
    public static <T,K> void pantMap(Map<T,K> map) {
        Set<Map.Entry<T, K>> set = map.entrySet();
        for (Map.Entry<T, K> integerStringEntry : set) {
            System.out.print(integerStringEntry.getKey() + "=" + integerStringEntry.getValue() + " ");
        }
        System.out.println();
    }
}
