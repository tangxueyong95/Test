<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/10
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String s = (String) request.getAttribute("s");
    if (s == null) {
        s = "";
    }
%>
<script>
    function changeImg(img) {
        //切换验证码其实就是重新设置这个img标签的src
        //因为，每次发送请求的路径都是一样的，所以获取的会是缓存中的数据，为了避免拿到缓存中的数据，我们每次请求都要携带一个不同的数据到服务器
        img.src = "/idea2/code?time=" + new Date()
    }
</script>
<%
    //从域对象中取出msg
    /*String msg = (String) application.getAttribute("msg");*/

    String msg = (String) request.getAttribute("msg");
    if (msg == null) {
        msg = "";
    }

    //声明一个字符串，用于勾上那个checkbox的钩
    String str = "";
    //如果没有记住了用户名str = "",如果记住了，str = "checked='checked'"

    //获取客户端的cookie，cookie的名字叫做"name"
    Cookie[] cookies = request.getCookies();
    String name = "";
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            if (cookieName.equals("name")) {
                //说明这个cookie正是我们需要的那个username
                name = cookie.getValue();

                //走到这里面，就说明记住了用户名
                str = "checked='checked'";
            }
        }
    }
%>
<html>
<head>
    <title> Title </title>
</head>
<body>
<form action="/idea2/3">
    姓名：<input type="text" name="name" value="<%=name%>"> <br>
    年龄：<input type="password" name="age"></br>
    记住用户名 <input type="checkbox" name="remember" <%=str%>> <br>
    验证码：<input type="text" name="checkCode"> <br>
    <img src="/idea2/code" onclick="changeImg(this)"> <br>
    <div><%=msg%><div><%=s%></div>
    </div>
    <input type="submit" value="登录">
</form>
</body>
</html>
