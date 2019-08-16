package com.day_04;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Work_09_10 {
    public static void main(String[] args) {
        Map<Integer, String> m = new HashMap<>();

        m.put(1930, "乌拉圭");
        m.put(1934, "意大利");
        m.put(1938, "意大利");
        m.put(1950, "乌拉圭");
        m.put(1954, "西德");
        m.put(1958, "巴西");
        m.put(1962, "巴西");
        m.put(1966, "英格兰");
        m.put(1970, "巴西");
        m.put(1974, "西德");
        m.put(1978, "阿根廷");
        m.put(1982, "意大利");
        m.put(1986, "阿根廷");
        m.put(1990, "西德");
        m.put(1994, "巴西");
        m.put(1998, "法国");
        m.put(2002, "巴西");
        m.put(2006, "意大利");
        m.put(2010, "西班牙");
        m.put(2014, "德国");
        entryInquiry(m);
    }

    public static <T, K> void entryInquiry(Map<T, K> map) {
        System.out.println("请输入一个年份");
        while (true) {
            try {
                Integer k = new Scanner(System.in).nextInt();
                if (null == map.get(k)) {
                    System.out.println("该年没有举办世界杯！");
                } else {
                    System.out.println(k + "年，获得世界杯冠军的是：" + map.get(k));
                }
                break;
            } catch (Exception e) {
                System.out.println("请输入正确的年份");
            }
        }
        System.out.println("请输入一个国家名称");
        String s = new Scanner(System.in).next();
        Set<Map.Entry<T, K>> set = map.entrySet();
        if (map.containsValue(s)) {
            for (Map.Entry<T, K> entry : set) {
                if (s.equals(entry.getValue()))
                    System.out.println(entry.getKey() + "、");
            }
        } else {
            System.out.println("该国家没有获得世界杯冠军");
        }
    }
}
