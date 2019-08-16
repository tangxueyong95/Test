package com.day_23;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class Druid {
    private static DataSource ds;

    static {
        InputStream is = Druid.class.getClassLoader().getResourceAsStream("druidconfig.properties");
        Properties p = new Properties();
        try {
            p.load(is);
             ds = DruidDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }
}
