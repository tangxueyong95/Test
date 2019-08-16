package com.day9._06;

import java.util.ArrayList;

public class _06 {
    public static void main(String[] args) {
        FactoryImp fa = new FactoryImp();
        ArrayList<Hat> a = fa.piliang(5);
        for (Hat hat : a) {//遍历集合
            fa.describe(hat);
        }
    }
}