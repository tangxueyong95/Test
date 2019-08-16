package com.day_20;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class day_17 {
    public static void main(String[] args) throws Exception {
      /*  Class.forName("com.mysql.jdbc.Driver");
        // 连接到MySQL        
        // url: 连接数据库的URL        
        // user: 数据库的账号        
        // password: 数据库的密码
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ day","root", "root");*/
        Connection conn = landMySQL.con();
        Statement stmt = conn.createStatement();
//        String sql = "SELECT *,chinese+english+math 总分 FROM student;";
        String sql = "SELECT student.name,chinese+english+math 总分,class.class FROM student,class where student.did=class.id;";
        ResultSet rs = stmt.executeQuery(sql);

// 内部有一个指针,只能取指针指向的那条记录        
        while (rs.next()) { // 指针移动一行,有数据才返回true        
// 取出数据            
//            int id = rs.getInt("id");
            String name = rs.getString("name");
           /* int chinese = rs.getInt("chinese");
            int english = rs.getInt("english");
            int math = rs.getInt("math");*/
            int c = rs.getInt("总分");
            String cl = rs.getString("class");

            System.out.println(name + " \t" + c + "\t" + cl);
        }

// 关闭资源        
        rs.close();
        stmt.close();
        conn.close();
//        System.out.println(conn);
    }
}
