package com.day_20;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class landMySQL {
    private static String name;
    private static String url;
    private static String user;
    private static String password;

    static {
        InputStream a = landMySQL.class.getClassLoader().getResourceAsStream("abc");
        Properties properties = new Properties();
        try {
            properties.load(a);
            name = properties.getProperty("name");
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            Class.forName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection con() throws Exception {
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
}
