package com.day_26;



import java.sql.Connection;

public class 动态代理连接池Test {
    public static void main(String[] args) throws Exception {
        MyPool2 p= new MyPool2();
        Connection conn1 = p.getConnection();
        Connection conn2 = p.getConnection();
        Connection conn3 = p.getConnection();
        Connection conn4 = p.getConnection();
        Connection conn5 = p.getConnection();
        Connection conn6 = p.getConnection();
        Connection conn7 = p.getConnection();
        Connection conn8 = p.getConnection();
        Connection conn9 = p.getConnection();
        Connection conn10 = p.getConnection();
    }

}
