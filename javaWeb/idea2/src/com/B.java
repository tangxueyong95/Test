package com;

import java.util.ArrayList;

public class B {
    public static void main(String[] args) {
        triangles();
    }

    public static void triangles() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int n=0;
        list.add(1);
        while (n < 10) {
            System.out.println(list);
            int size = list.size();
            list.add(1);
            for (int i = size-1; i >0; i--) {
                list.set(i,list.get(i)+list.get(i-1));
            }
            n++;
        }
    }
}
