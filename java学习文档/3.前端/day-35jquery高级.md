# jQuery高级

## 学习目标：

1. 能够使用jQuery的3种动画效果(掌握)
2. 能够使用jQuery对象的遍历方法(掌握)
3. 能够使用jQuery全局的遍历方法(了解)
4. 能够使用jQuery3.0的for-of遍历方法(了解)
5. 能够使用jQuery的绑定与解绑方法(掌握)
6. 能够使用jQuery的完成案例-广告的显示与隐藏(掌握)
7. 能够使用jQuery的完成案例-抽奖程序(掌握)
7. 能够理解jQuery的插件机制及其相关API(了解)
8. 能够使用jQuery的表单校验插件进行表单校验(掌握)
9. 能够使用表单校验插件的方法进行自定义校验规则(了解)

# 第1章 jQuery动画效果

## 1.1 显示效果

### 1.1.1 方法

| 方法名称                          | 解释                     |
| ----------------------------- | ---------------------- |
| show([speed,[easing],[fn]])   | 显示元素方法                 |
| hide([speed,[easing],[fn]])   | 隐藏元素方法                 |
| toggle([speed],[easing],[fn]) | 切换元素方法，显示的使之隐藏，隐藏的使之显示 |

### 1.1.2 参数

| 参数名称 | 解释                                                         |
| -------- | ------------------------------------------------------------ |
| speed    | 三种预定速度之一的字符串("slow","normal", or "fast")或表示动画时长的毫秒数值(如：1000) |
| easing   | 用来指定切换效果，默认是"swing"，可用参数"linear"            |
| fn       | 回调函数，在动画完成时执行的函数，每个元素执行一次           |

### 1.1.3 示例

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="jquery-3.3.1.js"></script>
<script type="text/javascript">
	function hideFn(){
		$("#showDiv").hide("slow","swing");
	}
	function showFn(){
		$("#showDiv").show("normal","swing");
	}
	function toggleFn(){
		$("#showDiv").toggle(5000,"linear");
	}
</script>
</head>
<body>
	<input type="button" value="点击按钮隐藏div" onclick="hideFn()">
	<input type="button" value="点击按钮显示div" onclick="showFn()">
	<input type="button" value="点击按钮切换div显示和隐藏" onclick="toggleFn()">

	<div id="showDiv" style="width:300px;height:300px;background:red">
		div显示和隐藏
	</div>
</body>
</html>
```

### 1.1.4 示例效果图

![](image\1.png)

## 1.2 滑动效果

### 1.2.1 方法

| 方法名称                               | 解释                     |
| ---------------------------------- | ---------------------- |
| slideDown([speed,[easing],[fn]])   | 向下滑动方法                 |
| slideUp([speed,[easing],[fn]])     | 向上滑动方法                 |
| slideToggle([speed],[easing],[fn]) | 切换元素方法，显示的使之隐藏，隐藏的使之显示 |

### 1.2.2 参数

| 参数名称   | 解释                                       |
| ------ | ---------------------------------------- |
| speed  | 三种预定速度之一的字符串("slow","normal", or "fast")或表示动画时长的毫秒数值(如：1000) |
| easing | 用来指定切换效果，默认是"swing"，可用参数"linear"         |
| fn     | 在动画完成时执行的函数，每个元素执行一次                     |

### 1.2.3 示例

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="jquery-3.3.1.js"></script>
<script type="text/javascript">
	function hideFn(){
		$("#showDiv").slideUp("slow","swing");
	}
	function showFn(){
		$("#showDiv").slideDown("normal","swing");
	}
	function toggleFn(){
		$("#showDiv").slideToggle(5000,"linear");
	}
</script>
</head>
<body>
	<input type="button" value="点击按钮隐藏div" onclick="hideFn()">
	<input type="button" value="点击按钮显示div" onclick="showFn()">
	<input type="button" value="点击按钮切换div显示和隐藏" onclick="toggleFn()">

	<div id="showDiv" style="width:300px;height:300px;background:red">
		div显示和隐藏
	</div>
</body>
</html>
```

### 1.2.4 示例效果图

![](image\2.png)

## 1.3 淡入淡出效果

### 1.3.1 方法

| 方法名称                              | 解释                     |
| --------------------------------- | ---------------------- |
| fadeIn([speed,[easing],[fn]])     | 淡入显示方法                 |
| fadeOut([speed,[easing],[fn]])    | 淡出隐藏方法                 |
| fadeToggle([speed],[easing],[fn]) | 切换元素方法，显示的使之隐藏，隐藏的使之显示 |

### 1.3.2 参数

| 参数名称   | 解释                                       |
| ------ | ---------------------------------------- |
| speed  | 三种预定速度之一的字符串("slow","normal", or "fast")或表示动画时长的毫秒数值(如：1000) |
| easing | 用来指定切换效果，默认是"swing"，可用参数"linear"         |
| fn     | 在动画完成时执行的函数，每个元素执行一次                     |

### 1.3.3 示例

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="jquery-3.3.1.js"></script>
<script type="text/javascript">
	function hideFn(){
		$("#showDiv").fadeIn("slow","swing");
	}
	function showFn(){
		$("#showDiv").fadeOut("normal","swing");
	}
	function toggleFn(){
		$("#showDiv").fadeToggle(5000,"linear");
	}
</script>
</head>
<body>
	<input type="button" value="点击按钮隐藏div" onclick="hideFn()">
	<input type="button" value="点击按钮显示div" onclick="showFn()">
	<input type="button" value="点击按钮切换div显示和隐藏" onclick="toggleFn()">

	<div id="showDiv" style="width:300px;height:300px;background:red">
		div显示和隐藏
	</div>
</body>
</html>
```

### 1.3.4 示例效果图

![](image\3.png)



# 第2章 jQuery的遍历

## 2.1 原始方式遍历

### 2.1.1 语法

```js
for(var i=0;i<元素数组.length;i++){
  	元素数组[i];
}
```

### 2.1.2 代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="jquery-3.3.1.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function(){
				var $lis = $("#city li");
				//1、原始循环方式
				for(var i=0;i<$lis.length;i++){
					alert($($lis[i]).html());
				}
			});
		</script>
	</head>
	<body>
		<ul id="city">
			<li>北京</li>
			<li>上海</li>
			<li>天津</li>
			<li>重庆</li>
		</ul>
	</body>
</html>

```

### 2.1.3 代码效果

![](image\4.png)

## 2.2 jquery对象方法遍历

### 2.1.1 语法

```js
jquery对象.each(function(index,element){});

其中，
index:就是元素在集合中的索引
element：就是集合中的每一个元素对象
```

### 2.1.2 代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="jquery-3.3.1.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function(){
				var $lis = $("#city li");
				$lis.each(function(index,element){
					alert(index+"--"+$(element).html());
				});
			});
		</script>
	</head>
	<body>
		<ul id="city">
			<li>北京</li>
			<li>上海</li>
			<li>天津</li>
			<li>重庆</li>
		</ul>
	</body>
</html>


```

### 2.1.3 代码效果

![](image\5.png)

## 2.3 jquery的全局方法遍历

### 2.3.1 语法

```js
$.each(要遍历的对象,function(index,element){});

其中，
index:就是元素在集合中的索引
element：就是集合中的每一个元素对象
```

### 2.3.2 代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="jquery-3.3.1.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function(){
				var $lis = $("#city li");
				$.each($lis, function(index,element) {
					alert(index+"--"+$(element).html());
				});
			});
		</script>
	</head>
	<body>
		<ul id="city">
			<li>北京</li>
			<li>上海</li>
			<li>天津</li>
			<li>重庆</li>
		</ul>
	</body>
</html>

```

### 2.3.3 代码效果

![](image\6.png)

## 2.4 jQuery3.0新特性for  of语句遍历(了解)

### 2.4.1 语法

```js
for(变量 of 要遍历的对象){
  	变量；
}
其中，
变量:定义变量依次接受jquery数组中的每一个元素
要遍历的对象
```

### 2.4.2 代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
      	<!-- 版本是3以上 -->
		<script src="jquery-3.3.1.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function(){
				var $lis = $("#city li");
				for(li of $lis){
					alert($(li).html());
				}
			});
		</script>
	</head>
	<body>
		<ul id="city">
			<li>北京</li>
			<li>上海</li>
			<li>天津</li>
			<li>重庆</li>
		</ul>
	</body>
</html>


```

### 2.4.3 代码效果

![](image\7.png)



# 第3章 jQuery的事件绑定与解绑

## 3.1 on绑定事件

### 3.1.1 语法

```js
jQuery元素对象.on(事件名称,function(){
  	//逻辑代码
})

其中：事件名称是jQuery的事件方法的方法名称，例如：click、mouseover、mouseout、focus、blur等

```

### 3.1.2 代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="jquery-3.3.1.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function(){
				$("#btn").on("click",function(){
					alert("使用on绑定点击事件");
				})
			});
		</script>
	</head>
	<body>
		<input id="btn" type="button" value="使用on绑定点击事件">
	</body>
</html>
```

### 3.1.3 代码效果

![](image\8.png)

## 3.2 off解绑事件

### 3.2.1 语法

```js
jQuery元素对象.off(事件名称);

其中：参数事件名称如果省略不写，可以解绑该jQuery对象上的所有事件

```

### 3.2.2 代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="jquery-3.3.1.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function(){
				$("#btn-on").on("click",function(){
					alert("使用on绑定点击事件");
				});
				$("#btn-off").click(function(){
					$("#btn-on").off("click");
				});
			});
		</script>
	</head>
	<body>
		<input id="btn-on" type="button" value="使用on绑定点击事件">
		<input id="btn-off" type="button" value="使用off解绑点击事件">
	</body>
</html>

```

### 3.2.3 代码效果

![](image\9.png)



## 3.3 事件切换

### 3.3.1 普通写法

#### 3.3.1.1 代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="jquery-3.3.1.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function(){
				$("#myDiv").mouseover(function(){
					$(this).css("background","green");
				});
				$("#myDiv").mouseout(function(){
					$(this).css("background","red");
				});
			});
		</script>
	</head>
	<body>
		<div id="myDiv" style="width:300px;height:300px;background:red">鼠标移入变成绿色，移出回复红色</div>
	</body>
</html>

```

#### 3.3.1.2 效果

![](image\10.png)

### 3.3.2 链式写法

#### 3.3.2.1 代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="jquery-3.3.1.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function(){
				$("#myDiv").mouseover(function(){
					$(this).css("background","green");
				}).mouseout(function(){
					$(this).css("background","red");
				});
			});
		</script>
	</head>
	<body>
		<div id="myDiv" style="width:300px;height:300px;background:red">鼠标移入变成绿色，移出回复红色</div>
	</body>
</html>

```

#### 3.3.2.2 效果

![](image\10.png)

### 3.3.3 切换函数写法

#### 3.3.3.1 语法

```js
hover([over,]out)

其中，over代表鼠标移入事件触发的函数，out代表鼠标移出事件触发的函数

```

#### 3.3.3.2 代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="jquery-3.3.1.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function(){
				$("#myDiv").hover(function(){
					$(this).css("background","green");
				},function(){
					$(this).css("background","red");
				});
			});
		</script>
	</head>
	<body>
		<div id="myDiv" style="width:300px;height:300px;background:red">鼠标移入变成绿色，移出回复红色</div>
	</body>
</html>


```

#### 3.3.3.3 效果

![](image\10.png)



# 第4章 jQuery综合案例

## 4.1 广告的自动显示与隐藏

### 4.1.1 需求描述

页面打开，延迟几秒钟，广告从上向下慢慢滑下显示，停留几秒，再从下向上慢慢滑动隐藏。

案例的步骤:

1. 进入页面后，四秒钟开始显示广告
2. 广告从开始显示到显示完毕，总共花2秒
3. 广告在页面上显示了3秒后开始隐藏(隐藏的动画，花费2秒中)

### 4.1.2 代码实现

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>网站的首页</title>
		<style>
			#content{width:100%;height:500px;background:#999}
		</style>
		
		<!--引入jquery-->
		<script type="text/javascript" src="jquery-3.3.1.js"></script>
		
		<script>
			$(function(){
				adFn();
			});
			//定时广告的方法
			function adFn(){
				//5秒钟弹出广告
				setTimeout(
					function(){
						$("#ad").slideDown(3000);
						//$("#ad").fadeIn(3000
						setTimeout(
							function(){
								$("#ad").slideUp(3000);
								//$("#ad").fadeOut(3000);
							},
							8000
						);
					},
					5000
				);
			}
		</script>
	</head>
	<body>
		<!-- 整体的DIV -->
		<div>
			<!-- 广告DIV -->
			<div id="ad" style="display: none;">
				<img style="width:100%" src="ad.jpg" />
			</div>
			
			<!-- 下方正文部分 -->
			<div id="content">
				正文部分
			</div>
		</div>
	</body>
</html>

```

### 4.1.3 代码效果

![](image\15.png)

## 4.2 抽奖程序

### 4.2.1 需求描述

- 当用户点击开始按钮时，小像框中的像片快速切换。
- 当用户点击停止按钮时，小像框中的像片停止切换，大像框中也会显示与小像框相同的像片。

### 4.2.2 代码实现

```html
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>2017-12-29-JQuery案例之抽奖程序</title>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="jquery-3.3.1.js"></script>
</head>

<body>
	<!-- 小像框 -->
	<div style="border-style:dotted;width:50;height:50">
		<img id="img1ID" src="" style="width:50;height:50"/>
	</div>
	<!-- 大像框 -->
	<div
		style="border-style:double;width:500;height:500;position:absolute;left:500px;top:10px">
		<img id="img2ID" src="" style="width:500;height:500"/>
	</div>
	<!-- 开始按钮 -->
	<input 
		id="startID"
		type="button" 
		value="点击开始" 
		style="width:150px;height:150px;font-size:22px"
		onclick="imgStart()">
		
	<!-- 停止按钮 -->	
	<input 
		id="stopID"
		type="button" 
		value="点击停止" 
		style="width:150px;height:150px;font-size:22px"
		onclick="imgStop()">

	<script language='javascript' type='text/javascript'>
		/*
		1.delay：延时  
		2.hide：隐藏，函数里必须放一个0，不然延时不起作用，无参的话，延时不起作用。
		3.代码：JQuery对象.delay(4000).hide(0) 
		*/
		//准备一个一维数组，装用户的像片路径
		var imgs = [
			"img/man00.jpg",
			"img/man01.jpg",
			"img/man02.jpg",
			"img/man03.jpg",
			"img/man04.jpg",
			"img/man05.jpg",
			"img/man06.jpg"
		];
		
		//计数器
		var num = 0;
		//像片总数
		var total = imgs.length;
		//定时器
		var timeID = null;
		//切换图片
		function imgChange(){
		 	timeID = window.setInterval(function(){
				var i = num % total;
				var img = imgs[i];
				$("#img1ID").attr("src",img).show();
				num++;
			},10);	
		}
		//游戏开始
		function imgStart(){
			imgChange();
			$("#startID").prop("disabled",true);
			$("#stopID").prop("disabled",false);	
			$("#img2ID").attr("src",null).show();		
		}

		//游戏停止
		function imgStop(){
			window.clearInterval(timeID);
			$("#startID").prop("disabled",false);
			$("#stopID").prop("disabled",true);	
			
			//获取选中图片的路径
			var selectedImg = imgs[--num % total];
			//计算数器清0
			num = 0;
			//将选中的图片设置到大像框中
			$("#img2ID").attr("src",selectedImg).hide();
			//隐藏2秒后，再被选中像片
			$("#img2ID").delay(2000).show(0);
		}
		
		//当浏览器加载页面时，将停止按钮失效
		$(function(){
			$("#stopID").prop("disabled",true);
		});
		
	</script>
</body>
</html>

```

### 4.2.3 代码效果

![](image\16.png)

# 第5章 jQuery的插件(难点)

## 5.1 jQuery的插件机制

### 5.1.1 jQuery插件机制概述

jQuery插件的机制很简单，就是利用jQuery提供的jQuery.fn.extend()和jQuery.extend()方法，扩展jQuery的功能。

### 5.1.2 jQuery插件机制语法

| 语法                | 解释                     |
| ------------------- | ------------------------ |
| $.fn.extend(object) | 对jQuery对象进行方法扩展 |
| $.extend(object)    | 对jQuery全局进行方法扩展 |

json数据的格式{key:value,key:value}

作用:在服务器和客户端之间传递数据

### 5.1.3 对jQuery对象进行方法扩展

#### 5.1.3.1 代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="jquery-3.3.1.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			//对jQuery对象的函数进行扩展
			$.fn.extend({
				  //定义js对象的check属性，值是一个函数
				  check: function() {
					//当前调用check方法的元素的checked属性设置为true
				    return this.each(function() { this.checked = true; });
				  },
				  //定义js对象的uncheck属性，值是一个函数
				  uncheck: function() {
					//当前调用uncheck方法的元素的checked属性设置为false
				    return this.each(function() { this.checked = false; });
				  }
			});
			
			function checkFn(){
				//调用check方法，使被选择的元素处于选中状态
				$("input[type=checkbox]").check();
			}
			function uncheckFn(){
				//调用uncheck方法，使被选择的元素处于不选中状态
				$("input[type=checkbox]").uncheck();
			}
		</script>
	</head>
	<body>
		<input id="btn-check" type="button" value="点击选中复选框" onclick="checkFn()">
		<input id="btn-uncheck" type="button" value="点击取消复选框选中" onclick="uncheckFn()">
		<br/>
		<input type="checkbox" value="football">足球
		<input type="checkbox" value="basketball">篮球
		<input type="checkbox" value="volleyball">排球
	
	</body>
</html>

```

#### 5.1.3.2 效果

![](image\11.png)

### 5.1.4 对jQuery全局进行方法扩展

#### 5.1.4.1 代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="jquery-3.3.1.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$.extend({
				  //定义js对象的min属性，值是一个函数，返回a与b比较的最小值
				  min: function(a, b) { return a < b ? a : b; },
				  //定义js对象的max属性，值是一个函数，返回a与b比较的最大值
				  max: function(a, b) { return a > b ? a : b; }
			});
			
			alert($.max(3,5));
		</script>
	</head>
	<body>
		
	</body>
</html>
```

#### 5.1.4.2 效果

![](image\12.png)



## 5.2 表单校验插件validator

### 5.2.1 表单校验插件概述

网络上有许多成熟的插件供使用者参考，插件就是将jquery实现的常用操作进行封装，封装后的“小框架“就可以叫做插件，按照插件的语法去操作可以实现很多复杂的功能，而我们需要做的是学会该插件的使用语法即可。表单校验插件是按照一定的语法编写代码，可以使用简单的代码完成复杂的表单校验工作。

### 5.2.2 表单校验插件的基本语法

#### 5.2.2.1 开发步骤

- 下载jquery-validation插件

- 将该插件（也就是一个js文件）导入到我们的工程中

- 在要使用校验插件的html中引入该js文件

- 编写表单校验的代码（语法）

  ```js
  $("form表单的选择器").validate({
      rules:{
        表单项name值:校验规则，
        表单项name值:校验规则... ...
      },
      messages:{
        表单项name值:错误提示信息，
        表单项name值:错误提示信息... ...
      }
  });
  ```

  其中，rules是对表单项校验的规则，messages是对应的表单项校验失败后的错误提示信息

  注意，当错误提示信息不按照我们预想的位置显示时，我们可以按照如下方式进行设置自定义错误显示标签放在我们需要显示的位置，当此表单项验证不通过时会将此信息自动显示出来，jQuery验证插件会自动帮助我们控制它的显示与隐藏

  ```html
  <lable for="html元素name值" class="error" style="display:none">错误信息</lable>
  ```

  如果设置了错误lable标签就不必在messages中设置此表单项错误提示信息了

#### 5.2.2.2 常用的校验规则

![](image\13.png)

### 5.2.3 表单校验插件自定义校验方法

如果预定义的校验规则尚不能满足需求的话可以进行自定义校验规则：

自定义校验规则步骤如下：

- 使用$.validator.addMethod("校验规则名称",function(value,element,params){},messages)

-  在rules中通过校验规则名称使用校验规则

-  在messages中定义该规则对应的错误提示信息

  其中，	value是校验组件的value值

  ​		element是校验组件的节点对象

  ​		params是校验规则的参数

### 5.2.4 代码实现

```html
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>我的jquery表单校验页面</title>
        
		<style type="text/css">
			p{text-align: center;font-size:24px;}
			table{margin: 0 auto;border: 0;}
			table tr{height:40px;border:0;}
			table tr td{padding:0 14px;border:1px solid #999}
			.error{color:red}
		</style>
		
        <script type="text/javascript" src="jquery-3.3.1.js"></script>
        <script type="text/javascript" src="jquery.validate.js"></script>
       	<script type="text/javascript">
       		
       		$(function(){
       			//自定义校验规则
       			/*
       			 * 参数：
       			 * 	第一个 规则名称
       			 * 	第二个 规则的实现逻辑 匿名函数
       			 */
       			$.validator.addMethod("cardlength",function(value,element,param){
       				//校验输入的长度是否满足15或18
       				if(value.trim().length!=param[0]&&value.trim().length!=param[1]){
       					return false; //代表该校验器不通过  显示该校验规则对应的错误信息
       				}
       				return true;
       			});
       			
       			
       			$.validator.addMethod("card15",function(value,element,param){
       				//校验15位的规则是否正确  --- 都是数字
       				var regExp15 = /^\d{15}$/;
       				if(value.trim().length==15){
       					//校验格式
       					return regExp15.test(value.trim());
       				}
       				return true;//放行 不归该校验器校验 
       			});
       			
       			$.validator.addMethod("card18",function(value,element,param){
       				if(param){
       					//校验15位的规则是否正确  --- 都是数字
	       				var regExp18 = /^\d{18}|\d{17}X$/;
	       				if(value.trim().length==18){
	       					//校验格式
	       					return regExp18.test(value.trim());
	       				}
       				}
       				return true;//放行 不归该校验器校验 
       			});
       			
       			$("#empForm").validate({
	       			rules:{
	       				realname:"required",
	       				username:{
	       					required:true,
	       					rangelength:[5,8]
	       				},
	       				psw:{
	       					required:true,
	       					minlength:6,
	       					maxlength:12
	       				},
	       				psw2:{
	       					required:true,
	       					minlength:6,
	       					maxlength:12,
	       					equalTo:"#psw"
	       				},
	       				gender:{
	       					required:true
	       				},
	       				age:{
	       					required:true,
	       					range:[26,50]
	       				},
	       				edu:{
	       					required:true
	       				},
	       				birthday:{
	       					required:true,
	       					dateISO:true,
	       					date:true
	       				},
	       				checkbox1:{
	       					required:true
	       				},
	       				email:{
	       					email:true
	       				},
	       				card:{
	       					required:true,
	       					cardlength:[15,18],
	       					card15:true,
	       					card18:true
	       				}
	       			},
	       			messages:{
	       				realname:"真实姓名必须写",
	       				username:{
	       					required:"用户名必须写",
	       					rangelength:"长度在5-8之间"
	       				},
	       				psw:{
	       					required:"密码必须写",
	       					minlength:"最小是6位",
	       					maxlength:"最大是12位"
	       				},
	       				psw2:{
	       					required:"密码必须写",
	       					minlength:"最小是6位",
	       					maxlength:"最大是12位",
	       					equalTo:"密码输入不一致"
	       				},
	       				age:{
	       					required:"年龄必须写",
	       					range:"年龄必须在26-50之间"
	       				},
	       				edu:{
	       					required:"必须选择一个"
	       				},
	       				birthday:{
	       					required:"生日必须填写",
	       					dateISO:"日期格式不正确",
	       					date:"日期非法"
	       				},
	       				email:{
	       					email:"邮箱格式不正确"
	       				},
	       				card:{
	       					required:"身份证必须填写",
	       					cardlength:"身份证长度是15或18位",
	       					card15:"15位的身份证必须都是数字",
	       					card18:"18位的身份证必须都是数字或末尾是X"
	       				}
	       			}
	       		});
       		});
       		
       		
       		
       	</script>

</head>
<body>
    <p>员工信息录入</p>
	<form name="empForm" id="empForm" method="post" action="test.html">
		<table border=1>
			<tr>
				<td>真实姓名(不能为空 ,没有其他要求)</td>
				<td><input type="text" id="realname" name="realname" />
				</td>
			</tr>
			<tr>
				<td>登录名(登录名不能为空,长度应该在5-8之间,可以包含中文字符(一个汉字算一个字符)):</td>
				<td><input type="text" id="username" name="username" /></td>
			</tr>
			 <tr> 
		      <td>密码(不能为空,长度6-12字符或数字,不能包含中文字符):</td>
		      <td><input type="password" id="psw"  name="psw" /></td>
		    </tr>
		    <tr> 
		      <td>重复密码密码(不能为空,长度6-12字符或数字,不能包含中文字符):</td>
		      <td><input type="password" id="psw2" name="psw2" /></td>
		    </tr>
		    <tr>
				<td>性别(必选其一)</td>
				<td>
				    <input  type="radio" id="gender_male" value="m" name="gender"/>&nbsp;男
				    <input  type="radio" id="gender_female" value="f" name="gender"/>&nbsp;女
				    <label class="error" for="gender" style="display: none;">必须选择一个性别						</label>
				</td>
			</tr>
			<tr>
				<td>年龄(必填26-50):</td>
				<td><input type="text" id="age" name="age" /></td>
          	</tr>
		    <tr> 
		      <td>你的学历:</td>
		      <td> <select name="edu" id="edu">
			          <option value="">－请选择你的学历－</option>
			          <option value="a">专科</option>
			          <option value="b">本科</option>
			          <option value="c">研究生</option>
			          <option value="e">硕士</option>
			          <option value="d">博士</option>
		          </select>
			  </td>
		    </tr>
			<tr> 
              <td>出生日期(1982/09/21):</td>
               <td><input type="text" id="birthday"  name="birthday" value="" /></td>
            </tr>
		   	<tr> 
		      <td>兴趣爱好:</td>
		      <td colspan="2"> 
			      <input type="checkbox" name="checkbox1" id="qq1"/>&nbsp;乒乓球 &nbsp;
		          <input type="checkbox" name="checkbox1" id="qq2" value="1" />&nbsp;羽毛球 
		          <input type="checkbox" name="checkbox1" id="qq3" value="2" />&nbsp;上网
		          <input type="checkbox" name="checkbox1" id="qq4" value="3" />&nbsp;旅游
		          <input type="checkbox" name="checkbox1" id="qq5" value="4" />&nbsp;购物
		          <label class="error" for="checkbox1" style="display: none;">必须选一个</label>
			  </td>
		    </tr>
			<tr> 
			      <td align="left">电子邮箱:</td>
			      <td><input type="text" id="email" name="email" /></td>
			</tr>
			<tr> 
			      <td align="left">身份证(15-18):</td>
			      <td><input type="text" id="card" name="card" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit"  name="firstname"  id="firstname" value="保存"></td>
			</tr>
		</table>
</form>
</body>
</html>
```

### 5.2.5 代码效果

![](image\14.png)