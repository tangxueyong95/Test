package com.itheima.travel.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtil {
    private static DataSource dataSource;

    static {
        ClassLoader classLoader = JDBCUtil.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("druidconfig.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
            //使用工厂类创建
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    public static void close(Connection conn, Statement stm) throws SQLException {
        close(conn, stm, null);
    }

    public static void close(Connection conn, Statement stm, ResultSet rst) throws SQLException {
        if (rst != null) {
            rst.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
