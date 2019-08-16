<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="${pageContext.request.contextPath}/bootstrap2.3.2/css/bootstrap.min.css" rel="stylesheet" />
    <title>客户关系管理系统-用户首页</title>
    <link href="${pageContext.request.contextPath}/css/Common.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/Index.css" rel="stylesheet" />
</head>
<body>
    <div class="header">

        <img class="logo" src="${pageContext.request.contextPath}/images/logo.png" />
        <label class="logo-title">客户关系管理系统</label>
        <ul class="inline">
            <li>
                <img src="${pageContext.request.contextPath}/images/32/avatar5.png" style="height: 32px;width: 32px" />&nbsp;&nbsp;欢迎您,<em id="name"></em>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle mymsg" data-toggle="dropdown" href="#">
                	<img src="${pageContext.request.contextPath}/images/32/166.png" />&nbsp;&nbsp;修改个人信息<b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="#">修改密码</a></li>
                </ul>
            </li>
            <li>
                <a class="loginout" href="${pageContext.request.contextPath}/user/sessionAttributeClean"> <img src="${pageContext.request.contextPath}/images/32/19.png" />退出</a>
            </li>
        </ul>
    </div>

    <div class="nav">
        <ul class="breadcrumb" style="margin: 0px">
            <li style="line-height: 36px"><a href="${pageContext.request.contextPath}/jsp/index.jsp">首页</a></li>
        </ul>
    </div>

    <div class="container-fluid content">
        <div class="row-fluid">
            <div class="span2 content-left">
                <div class="accordion">
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">
                                <img class="left-icon" src="${pageContext.request.contextPath}/images/32/14.png" /><span class="left-title">系统设置</span>
                            </a>
                        </div>

                        <div id="collapseOne" class="accordion-body collapse in">
                            <div class="accordion-inner">
                                <a href="${pageContext.request.contextPath}/jsp/index.jsp">
                                    <img class="left-icon-child" src="${pageContext.request.contextPath}/images/32/11.png" /><span class="left-body">客户管理</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
              </div>

            <div class="span10 content-right">
                <iframe src="${pageContext.request.contextPath}/jsp/center.jsp" class="iframe"></iframe>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="foot"></div>
    <script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap2.3.2/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/Index.js"></script>
	<div style="text-align:center;">
</div>
<script type="text/javascript">
    $(function () {
     $.post("/user/sessionAttributeGet",function (data){
         // console.log(data)
         $("#name").text(data)
     })
    })
</script>
</body>
</html>