<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link href="${pageContext.request.contextPath}/bootstrap2.3.2/css/bootstrap.min.css" rel="stylesheet"/>
    <title></title>
    <link href="${pageContext.request.contextPath}/css/Common.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/Index2.css" rel="stylesheet"/>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <h4>数据列表</h4>
        <div>
            <form action="#" method="post" onsubmit="false">
                <div class="uinArea" id="uinArea">
                    客户名称：
                    <input id="name" type="text" value="${customer.custName}" name="custName" class="inputstyle"/>
                    客户类型：
                    <input id="type" type="text" value="${customer.custType}" name="custType" class="inputstyle"/>
                    <button onclick="a()" type="button" style="width:60px;" class="button_blue">查询</button>
                </div>
            </form>
        </div>
        <div class="add"><a class="btn btn-success" onclick="openadd();">新增</a></div>
        <div class="w">
            <div class="span12">
                <table class="table table-condensed table-bordered table-hover tab">
                    <thead>
                    <tr>
                        <th>客户名称</th>
                        <th>客户类型</th>
                        <th>客户电话</th>
                        <th>客户地址</th>
                        <th>所属用户角色</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody">

                    </tbody>
                </table>
                <div id="page" class="tr"></div>
            </div>
        </div>

        <div id="addModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 id="myModalLabel">添加客户</h3>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="add_form" action="#" method="post">
                    <div class="control-group">
                        <label class="control-label" for="cust_name">客户名称</label>
                        <div class="controls">
                            <input type="text" id="cust_name" name="custName" placeholder="客户名称">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="cust_type">客户类型</label>
                        <div class="controls">
                            <input type="text" id="cust_type" name="custType" placeholder="客户类型">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="cust_phone">客户电话</label>
                        <div class="controls">
                            <input type="text" id="cust_phone" name="custPhone" placeholder="客户电话">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="cust_address">客户地址</label>
                        <div class="controls">
                            <input type="text" id="cust_address" name="custAddress" placeholder="客户地址">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="rolename">所属用户角色</label>
                        <div class="controls">
                            <select name="User.uid" id="rolename" placeholder="所属用户角色">
                                <c:forEach items="${sessionScope.userlist}" var="user">
                                    <option value="${user.uid}">${user.rolename}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                <button class="btn btn-primary" id="add">保存</button>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap2.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
    function openadd() {
        $("#myModalLabel").text("添加用户");
        $("#userName").attr("readonly", false);
        $("input").val("");
        $("#addModal").modal("show");
        $("#add").show();
        $("#edt").hide();
    }
    function a() {
        $("#tbody").html(" ");
        $.post("/cust/fandall?custName="+$("#name").val()+"&custType="+$("#type").val(), function (data) {
            $.each(data, function (index, element) {
                $("#tbody").append(
                    "<tr>\n" +
                    "                          <td>" + element.cust_name + "</td>\n" +
                    "                          <td>" + element.cust_type + "</td>\n" +
                    "                          <td>" + element.cust_phone + "</td>\n" +
                    "                          <td>" + element.cust_address + "</td>\n" +
                    "                          <td>" + element.rolename + "</td>\n" +
                    "                          <td><a href=\"/cust/delete?cid="+element.cid+"\">删除</a></td>\n" +
                    "                      </tr>"
                )
            })
        })
    }
    $(a())
    function add() {
        //"" false "sss" true
        //0 false, true
        //null false
        //undefined false
        //
        if(!$("#cust_name").val()) {
            alert("客户名称不能为空")
            return;
        }
        if(!$("#cust_type").val()) {
            alert("客户类型不能为空")
            return;
        }
        if(!$("#cust_phone").val()) {
            alert("客户电话不能为空")
            return;
        }
        if(!$("#cust_address").val()) {
            alert("客户地址不能为空")
            return;
        }

        $("#add_form").submit();
    }
</script>
</body>
</html>