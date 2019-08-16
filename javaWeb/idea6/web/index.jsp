<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.content{
		width:643px;
		margin:200px auto;
		text-align: center;
	}
	input[type='text']{
		width:530px;
		height:40px;
		font-size: 14px;
	}
	input[type='button']{
		width:100px;
		height:46px;
		background: #38f;
		border: 0;
		color: #fff;
		font-size: 15px
	}
	.show{
		position: absolute;
		width: 535px;
		height:100px;
		border: 1px solid #999;
		border-top: 0;
	}
</style>
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript">
	function search(obj) {
	    //判断输入框中的内容是空字符串，则不发请求
        var value = obj.value.trim();
        if (value != "") {
            //向服务器的SearchServlet发送一个异步请求，并且将输入框的内容携带过去
            $.ajax({
                url:"search",
                data:"name="+value,
                type:"post",
                dataType:"json",
                success:function (result) {
                    var data = result.data;//data是一个json的数组
                    //遍历data
					//遍历之前，要清空table中所有tr
					$(".show table").empty()
                    $.each(data,function (index, element) {
                        //每一个element就代表一条用户信息
                        var name = element.name;
                        $(".show").show()
                        $(".show table").append("<tr><td>"+name+"</td></tr>")
                    })
                }
            })
        }else {
            $(".show").hide()
		}
    }
</script>
</head>
<body>
	<div class="content">
		<img alt="" src="logo.png"><br/><br/>
		<input type="text" name="word" onkeyup="search(this)">
		<input type="button" value="搜索一下">
		<div class="show" style="display: none">
			<table width="100%">
			</table>
		</div>
	</div>
</body>
</html>