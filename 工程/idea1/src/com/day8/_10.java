package com.day8;

public class _10 {
    public static void main(String[] args) {
        String a = "abc";
        String b = "cdf";
        SL(a,b);
    }

    //如果第一个字符串的最后以为与第二个字符串的首位相同，去其一合并
    public static void SL(String a,String b) {
        if(a.charAt(a.length()-1)==b.charAt(0)){
            System.out.println(a+b.substring(1));
        }
    }
}
