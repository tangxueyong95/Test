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

    <h1>请求参数</h1>
    <h3>3.6.2基本数据类型和字符串类型</h3>
    <a href="param/testParam?username=张三&age=18">传递基本数据类型</a>
    <hr>
    <a href="param/testParamBean?username=张三&age=18">传递实体类型</a>
    <hr>
    <form action="param/testParamSaveAccount" method="post">
        账号名：<input type="text" name="name"/><br>
        账号密码：<input type="text" name="password"/><br>
        账号金额：<input type="text" name="money"/><br>
        用户名：<input type="text" name="user.username"/><br>
        年龄：<input type="text" name="user.age"/><br>
        <%--<h3>3.6.5给集合属性数据封装（了解）</h3>--%>
        <%--（1）JSP页面编写方式：list[0].属性--%>
        <%--（2）JSP页面编写方式：map[‘one’].属性--%>
        <%--<br>--%>
        用户姓名（list)：<input type="text" name="list[0].username"/><br>
        用户年龄（list)：<input type="text" name="list[0].age"/><br>
        用户姓名（list)：<input type="text" name="list[1].username"/><br>
        用户年龄（list)：<input type="text" name="list[1].age"/><br>

        用户姓名（map)：<input type="text" name="map['one'].username"/><br>
        用户年龄（map)：<input type="text" name="map['one'].age"/><br>
        用户姓名（map)：<input type="text" name="map['two'].username"/><br>
        用户年龄（map)：<input type="text" name="map['two'].age"/><br>
        <input type="submit" value="提交"/>
    </form>
    <hr>
    <form action="param/saveUser" method="post">
        姓名：<input type="text" name="username"/><br>
        年龄：<input type="text" name="age"/><br>
        生日：<input type="text" name="birthday"/><br>
        <input type="submit" value="提交"/>
    </form>

    <hr>
    <br>
    <a href="param/testServlet?name=张三">测试ServletAPI</a>


</body>
</html>
