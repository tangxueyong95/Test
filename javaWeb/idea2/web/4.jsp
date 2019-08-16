<%@ page import="com.User" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/12
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) request.getAttribute("user");
    String s = user.getName();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h6><%=s%></h6>
</body>
</html>
