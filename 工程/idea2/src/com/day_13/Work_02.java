package com.day_13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Work_02 {
    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        map.put("岑小村", 59);
        map.put("谷天洛", 82);
        map.put("渣渣辉", 98);
        map.put("蓝小月", 65);
        map.put("皮几万", 70);
        Function<ArrayList<Integer>, Integer> f1 = (list1) -> {
            int s = 0;
            for (Integer i : list1) {
                s += i;
            }
            return s / list1.size();
        };
        Function<Map<String, Integer>, ArrayList<Integer>> f2 = (map1) -> {
            ArrayList<Integer> list1 = new ArrayList<>();
            /*for (String s : map1.keySet()) {
                list1.add(map1.get(s));
            }*/
            list1.addAll(map1.values());
            return list1;
        };
        int a = f1.apply(f2.apply(map));
        System.out.println(a);
        int b =f2.andThen(f1).apply(map);
        System.out.println(b);
    }
}
