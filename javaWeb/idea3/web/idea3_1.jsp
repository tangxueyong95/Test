<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/10
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function changeImg(img) {
        //切换验证码其实就是重新设置这个img标签的src
        //因为，每次发送请求的路径都是一样的，所以获取的会是缓存中的数据，为了避免拿到缓存中的数据，我们每次请求都要携带一个不同的数据到服务器
        img.src = "/idea2/code?time=" + new Date()
    }
</script>
<html>
<head>
    <title> Title </title>
</head>
<body>
<form action="3">
    姓名：<input type="text" name="name" value="${cookie.name.value}"><br>
    年龄：<input type="password" name="age"></br>
    记住用户名<input type="checkbox" name="remember" ${empty cookie.name.value ? "" : "checked='checked'"}><br>
    验证码：<input type="text" name="checkCode"><br>
    <img src="code" onclick="changeImg(this)"> <br>
    <div>${msg}<div>${s}</div>
    </div>
    <input type="submit" value="登录">
</form>
</body>
</html>
