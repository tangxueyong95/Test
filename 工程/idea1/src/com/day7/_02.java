package com.day7;

import java.util.ArrayList;

public class _02 {
    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        a.add("def");
        a.add("defa");
        a.add("abc");
        a.add("adef");
        remove(a);
        prin1(a);
    }

    public static void remove(ArrayList<String> a) {
        for (int i = 0; i < a.size(); i++) {
            String j =a.get(i); //获取集合中的元素
            if(j.contains("def")){  //比较元素中是否有字符串def
                a.remove(i);    //删除该元素,a.size()-1
                i--;
            }
        }
    }
    //打印集合中的元素
    public static void prin1(ArrayList<String> a) {
        for (int i = 0; i < a.size(); i++) {
            System.out.print(a.get(i)+" ");
        }
        System.out.println();
    }
}
