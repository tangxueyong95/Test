<%--
  Created by IntelliJ IDEA.
  User: Fanyi Xiao
  Date: 2019/5/13
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
    <%--判断当前是否已登录:已登录的标示是session域对象里面存储了user--%>
    <c:if test="${empty user}">
        <%--未登录时候显示的内容--%>
        <a href="login.jsp">登录</a>
        <a href="register.jsp">注册</a>
    </c:if>

    <c:if test="${!empty user}">
        <%--已登录的时候显示的内容--%>
        <h1>欢迎登录,${user.name}</h1>
        <a href="logout">退出登录</a>
    </c:if>
</body>
</html>
