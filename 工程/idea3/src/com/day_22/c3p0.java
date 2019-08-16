package com.day_22;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class c3p0 {
    public static void main(String[] args) throws SQLException {
        ComboPooledDataSource cp = new ComboPooledDataSource();
        Connection conn = cp.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT student.name,chinese+english+math 总分,class.class FROM student,class where student.did=class.id;";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String name = rs.getString("name");
            int c = rs.getInt("总分");
            String cl = rs.getString("class");

            System.out.println(name + " \t" + c + "\t" + cl);
        }

        rs.close();
        stmt.close();
        conn.close();
    }
}
