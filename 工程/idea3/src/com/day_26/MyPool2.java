package com.day_26;

import com.day_22.landMySQL;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;


public class MyPool2 implements DataSource {
    private int initCount = 5;
    private int maxCount = 10;
    private int curCount = 0;
    private LinkedList<Connection> list = new LinkedList<Connection>();

    public MyPool2() throws Exception {
        for (int i = 0; i < initCount; i++) {
            Connection conn = createConnection();
            list.add(conn);
        }
    }

    public Connection createConnection() throws Exception {
        Connection conn = (Connection) Proxy.newProxyInstance(MyPool2.class.getClassLoader(), new Class[]{Connection.class}, new MyInvocationHandler(landMySQL.con(), list,initCount,curCount));
        curCount++;
        return conn;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        if (list.size() > 0) {
            conn = list.remove();
        } else if (curCount < maxCount) {
            try {
                conn = createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("已经达到了最大的连接个数，请稍后");
        }
        return conn;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}

