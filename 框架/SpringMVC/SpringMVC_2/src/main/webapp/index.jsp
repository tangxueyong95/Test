<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="js/jquery.min.js"></script>
<%--<script src="js/jquery-3.3.1.js"/>--%>
<script>
    $(function () {
        $("#btn").click(function () {
            $.ajax({
                url: "user/testAjax", // 请求
                data: '{"username":"张三","password":"123","age":22}',// 传递参数（json的字符串--->javabean）
                type: "post",  //请求方式
                dataType: "json", //返回 JSON 数据
                contentType: "application/json;charset=utf-8",// 请求类型以json的形式传递
                success: function (data) {
                    alert(data);
                    alert(JSON.stringify(data)); // json对象转换成json字符串
                    console.log(data);
                    alert(data.username + "          " + data.password + "       " + data.age);

                }
            })
        })
    })
</script>
<head>
    <title>Title</title>
</head>
<body>

<h1>响应入门案例</h1>
<a id="an" href="user/testResponse">springmvc的响应</a>
<hr>
<a href="user/testUpdate">跳转到更新页面</a>
<hr>
<h3>无返回值void</h3>
<a href="user/testVoid">无返回值</a>
<hr>
<h3>返回ModelAndView对象</h3>
<a href="user/testModelAndView">ModelAndView</a>
<hr>
<h3>转发和重定向</h3>
<a href="user/testForwardOrRedirect">ForwardOrRedirect</a>

<hr>
<h3>ajax访问</h3>
<input type="button" value="提交" id="btn"/>

<h1>文件上传入门案例</h1>
<a href="fileUpload/testFileUpload">springmvc的响应</a>
<hr>
<%--文件上传的三要素--%><%--文件上传的三要素--%>
<form action="fileUpload/testFileUpload1" enctype="multipart/form-data" method="post">
    <input type="file" name="upload"/><br>
    <input type="submit" value="提交"/>
</form>
<hr>
<form action="fileUpload/testFileUpload2" enctype="multipart/form-data" method="post">
    <input type="file" name="upload"/><br>
    <input type="submit" value="提交"/>
</form>
<hr>
<form action="fileUpload/testFileUpload3" enctype="multipart/form-data" method="post">
    <input type="file" name="upload"/><br>
    <input type="submit" value="提交"/>
</form>
<h1>异常处理入门案例</h1>
<a href="exception/testException">异常处理测试</a>
<hr>
<h1>拦截器入门案例</h1>
<a href="interceptor/testInterceptor">拦截器测试</a>
<hr>
</body>
</html>
