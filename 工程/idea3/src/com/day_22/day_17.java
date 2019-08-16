package com.day_22;

import java.sql.Connection;

public class day_17 {
    public static void main(String[] args) throws Exception {
        MyPool mp = new MyPool();
        Connection conn = mp.getConnection();
        Connection conn1 = mp.getConnection();
        Connection conn2 = mp.getConnection();
        Connection conn3 = mp.getConnection();
        Connection conn4= mp.getConnection();
        Connection conn5 = mp.getConnection();
        Connection conn6 = mp.getConnection();
        Connection conn7 = mp.getConnection();
        Connection conn8 = mp.getConnection();
        Connection conn9 = mp.getConnection();
        conn.close();
        conn1.close();
        conn2.close();
        conn3.close();
        conn4.close();
        conn5.close();
        conn6.close();
        conn7.close();
        conn8.close();
        conn9.close();
       /* Statement stmt = conn.createStatement();
        String sql = "SELECT student.name,chinese+english+math 总分,class.class FROM student,class where student.did=class.id;";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String name = rs.getString("name");
            int c = rs.getInt("总分");
            String cl = rs.getString("class");

            System.out.println(name + " \t" + c + "\t" + cl);
        }

        rs.close();
        stmt.close();*/
    }
}
