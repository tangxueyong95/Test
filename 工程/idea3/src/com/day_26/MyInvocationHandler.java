package com.day_26;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.LinkedList;

public class MyInvocationHandler implements InvocationHandler {
    private Connection con;
    private LinkedList<Connection> list;
    private int initCount;
    private int curCount;

    public MyInvocationHandler(Connection con, LinkedList<Connection> list, int initCount, int curCount) {
        this.con = con;
        this.list = list;
        this.initCount = initCount;
        this.curCount = curCount;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 先得到当前调用的方法的方法名
        Connection conn = (Connection) proxy;
        String methodName = method.getName();
        if ("close".equalsIgnoreCase(methodName)) {
            // 需要增强的方法，进行增强
            if (list.size() < initCount) {
                list.addLast(conn);
            }else {
                con.close();
                curCount--;
            }
        } else {
// 其他方法正常调用
            return method.invoke(con, args);
        }
        return null;
    }
}
