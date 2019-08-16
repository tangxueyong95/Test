package com.day_03;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Work_10 {
    public static void main(String[] args) {
        String[] strs = {"12345", "67891", "12347809933", "98765432102", "67891", "12347809933"};
        LinkedList<String> list = new LinkedList<>();
        Collections.addAll(list, strs);
        getSingle(list);
        printList_Iterator(list);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~");
        printList_Forech(list);
    }

    public static void getSingle(LinkedList<String> list) {
        LinkedHashSet<String> set = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(set);
    }

    public static void printList_Iterator(LinkedList<String> list) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            System.out.println(s);
        }
    }
    public static void printList_Forech(LinkedList<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
    }
}
