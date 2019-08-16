package com.函数式接口;

public class num1MySupplier {
    public static void printMySupplier(MySupplier supplier){
        System.out.println(supplier.get());
    }

    public static void main(String[] args) {
        printMySupplier(() -> "呵呵");
    }
}
