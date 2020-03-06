package com;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class A {
    public static void main(String[] args) {
        Map<Object, Object> map = new HashMap<>();
        String o = (String)map.get("2");
        boolean empty = StringUtils.isEmpty(o);
        map.put("3",!empty?o:null);
        System.out.println(o+empty);
        int a=2^1;
        System.out.println(a);
    }
}
