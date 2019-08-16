<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap模板</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
</head>
<script>
    //声明一个删除联系人的方法
    function deleteContact(id) {
        //1.弹出一个确认框
        var flag = confirm("你真的要删除这个联系人吗?");
        if (flag) {
            //确定要删除
            //向服务器发送一个请求，删除当前联系人信息,并且将要删除的联系人的id携带到服务器
            location.href = "remove?id="+id
        }
    }
</script>
<body>
<div class="container">
    <h3 style="text-align: center">显示所有联系人</h3>
    <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <th>编号</th>
            <th>姓名</th>
            <th>年龄</th>
        </tr>
        <%--遍历PageBean对象中的联系人集合--%>
        <c:forEach items="${page.list}" var="contact">
            <tr>
                <td>${contact.id}</td>
                <td>${contact.name}</td>
                <td>${contact.age}</td>
                <td><a class="btn btn-default btn-sm" href="one?id=${contact.id}">修改</a>&nbsp;<a onclick="deleteContact('${contact.id}')" class="btn btn-default btn-sm" href="javascript:;">删除</a></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="8" align="center"><a class="btn btn-primary" href="add.jsp">添加联系人</a></td>
        </tr>
        <tr>
            <td colspan="8" align="center">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <%--上一页,如果当前不是第一页才显示:上一页--%>
                        <c:if test="${page.currentPage != 1}">
                            <li>
                                <a href="page?currentPage=${page.currentPage-1}" aria-label="Previous">
                                    <span aria-hidden="true">上一页</span>
                                </a>
                            </li>
                        </c:if>

                        <%--根据总页数遍历展示页码--%>
                        <c:forEach begin="1" end="${page.totalPage}" var="i">
                            <%--将当前页高亮显示--%>
                            <c:if test="${i == page.currentPage}">
                                <li class="active"><a href="javascript:;">${i}</a></li>
                            </c:if>
                            <c:if test="${i != page.currentPage}">
                                <li><a href="page?currentPage=${i}">${i}</a></li>
                            </c:if>
                        </c:forEach>
                        <%--下一页:如果当前页不是最后一个，才显示下一页--%>
                        <c:if test="${page.currentPage != page.totalPage}">
                            <li>
                                <a href="page?currentPage=${page.currentPage+1}" aria-label="Next">
                                    <span aria-hidden="true">下一页</span>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
