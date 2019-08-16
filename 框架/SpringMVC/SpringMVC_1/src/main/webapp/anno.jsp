<%--
  Created by IntelliJ IDEA.
  User: administratorad
  Date: 2019/6/16
  Time: 9:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>RequestParam入门案例</h3>
    <%--href值为绝对路径--%>
    <a href="/SpringMVC_1/anno/testRequestParam?name=哈哈&age=22">RequestParam</a>
    <hr>
    <h3>@RequestBody</h3>
    post请求jsp代码： <br>
    <!-- request body注解 -->
    <%--href值为相对路径，和地址栏路径在同一子目录下--%>
    <form action="anno/testRequestBody" method="post">
        用户名称：<input type="text" name="username" ><br/>
        用户密码：<input type="password" name="password" ><br/>
        用户年龄：<input type="text" name="age" ><br/>
        <input type="submit" value="保存">
    </form>
    get请求jsp代码： <br>
    <a href="anno/testRequestBody?body=test">requestBody注解get请求</a>
    <hr>
    <h3>@PathVariable注解</h3>
    <!-- PathVariable注解 -->
    <a href="anno/testPathVariable/1008611/zhangsanabc1234">pathVariable注解</a>

    <hr>
    resfful风格：jsp中示例代码：
    <!-- 保存 -->
    <form action="anno/testPathVariable" method="post">
        用户名称：<input type="text" name="username"><br/>
        <!-- <input type="hidden" name="_method" value="POST"> -->
        <input type="submit" value="保存"></form>
    <hr/>
    <!-- 更新 -->
    <form action="anno/testPathVariable" method="post">
        用户名称：<input type="text" name="username"><br/>
        <input type="hidden" name="_method" value="PUT">
        <input type="submit" value="更新">
    </form>
    <hr/>
    <!-- 删除 -->
    <form action="anno/testPathVariable/1" method="post">
        <input type="hidden" name="_method" value="DELETE">
        <input type="submit" value="删除">
    </form>
    <hr/>
    <!-- 查询一个 -->
    <form action="anno/testPathVariable/1" method="post">
        <input type="hidden" name="_method" value="GET">
        <input type="submit" value="查询">
    </form>
    <hr/>
    <h3>测试@RequestHeader注解</h3>
    <!-- RequestHeader注解 -->
    <a href="anno/testRequestHeader">获取请求消息头</a>
    <hr>
    <h3>测试@CookieValue注解</h3>
    <!-- CookieValue注解 -->
    <a href="anno/testCookieValue">CookieValue注解</a>
    <hr>

    <h3>测试@ModelAttribute注解</h3>
    需求： 修改用户信息，要求用户的日期不能修改 jsp的代码：
    <!-- 修改用户信息 -->
    <form action="anno/testModelAttribute" method="post">
        用户名称：<input type="text" name="username" ><br/>
        用户年龄：<input type="text" name="age" ><br/>
        <input type="submit" value="保存">
    </form>
    <hr>
    <h3>测试@SessionAttributes注解</h3>
    <!-- SessionAttribute注解的使用 -->
    <a href="anno/sessionAttributePut?">存入SessionAttribute</a> <hr/>
    <a href="anno/sessionAttributeGet">取出SessionAttribute</a> <hr/>
    <a href="anno/sessionAttributeClean">清除SessionAttribute</a>
</body>
</html>
