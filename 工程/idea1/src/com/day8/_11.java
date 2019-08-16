package com.day8;

import java.util.ArrayList;

public class _11 {
    public static void main(String[] args) {
        String[] a = {"0af3s2sf", "s6ds1", "jjww", "lailai"};
        HH(a);
    }

    //将无数字的字符串添加到集合
    public static void HH(String[] a) {
        ArrayList<String> list = new ArrayList<>();
        for (String anA : a) {
            if (pD(anA)) {
                list.add(anA);
            }
        }
        dY(list);
    }

    //判断字符串中是否有数字
    public static boolean pD(String n) {
        for (int i = 0; i < n.length(); i++) {
            if (Character.isDigit(n.charAt(i))) {
                i = n.length();
                return false;
            }
        }
        return true;
    }

    //打印出集合
    public static void dY(ArrayList list) {
        System.out.println("删除有数字的字符串后的集合元素有：");
        for (Object aList : list) {
            System.out.println(aList);
        }
    }
}
