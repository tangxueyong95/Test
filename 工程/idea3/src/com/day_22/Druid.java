package com.day_22;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Druid {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = Druid.class.getClassLoader().getResourceAsStream("druidconfig.properties");
        Properties p = new Properties();
        p.load(inputStream);
        DataSource ds = DruidDataSourceFactory.createDataSource(p);
        Connection conn = ds.getConnection();
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
