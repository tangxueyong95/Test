package com.day_20;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PreparedSatementText {
    public static void main(String[] args) throws Exception {
        String sql = "INSERT iNTO student VALUES (null,?,?,?,?,null)";
        Connection con = landMySQL.con();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1,"李四");
        pst.setInt(2,54);
        pst.setInt(3,60);
        pst.setInt(4,80);
        pst.executeUpdate();
        pst.close();
        con.close();
    }
}
