package com.day_02;

public class A {
    public static void main(String[] args) {

        StringBuilder string = new StringBuilder("789");
        print(string, "", string.length());

    }

    public static void print(StringBuilder src, String result, int num) {
        if (result.length() == num)
            System.out.print(result + " ");//第一次789
        else {
            for (int i = 0; i < src.length(); i++) {
                char ch = src.charAt(i);//第一次 i=1时src{8,9};
                print(src.deleteCharAt(i), result + ch, num);
                src.insert(i, ch);//7
            }
        }
    }
}
