---
typora-copy-images-to: assets
---

# day03-即时通讯和接口加密

# 学习目标

- [ ] 了解即时通讯业务场景和需求；
- [ ] 了解短连接和长连接
- [ ] 了解websocket协议
- [ ] 使用环信im云实现十次方即时通讯功能
- [ ] 了解接口加密业务需求
- [ ] 掌握常用加密算法和密钥格式
- [ ] 实现十次方的接口加密微服务

# 第一章-即时通讯

## 知识点-即时通讯的业务场景

### 1.目标

- [ ] 了解即时通讯业务场景和需求

### 2.路径

1. 即时通讯介绍
2. 即时通讯的业务场景

### 3.讲解

#### 3.1什么是即时通信

​	即时通信（Instant Messaging，简称IM）是一个允许两人或多人使用网络==实时==的传递文字消息、文件、语音与视频交流。 即时通讯技术应用于需要**实时**收发消息的业务场景。

#### 3.2即时通讯的业务场景

- 客服系统

  [招商银行客服中心](https://xiaozhao.wx.cmbchina.com/PCS2012/Service.aspx?hdType=Normal&hdToken=%3C%3fxml+version%3d%221.0%22+encoding%3d%22UTF-8%22%3f%3E%3CToken%3E%3CIDType%3E%3C%2fIDType%3E%3CID%3E%3C%2fID%3E%3CCardIDList%3E%3C%2fCardIDList%3E%3CName%3E%3C%2fName%3E%3CFrom%3EA%3C%2fFrom%3E%3CSystime%3E2019%2f5%2f15+10%3a10%3a44%3C%2fSystime%3E%3CVerify_Type%3EHMAC_SHA1%3C%2fVerify_Type%3E%3CVerify%3EYbUzLZrDUAy2hKQvTNIY%2b0cQgyI%3d%3C%2fVerify%3E%3CsvcType%3E%3C%2fsvcType%3E%3C%2fToken%3E)


- 直播互动

  <img src="assets/抖音logo.jpg" width="80"/><img src="assets/全民直播logo.jpg" width="80"/><img src="assets/斗鱼tv-logo.jpg" width="80"/>

  ​    抖音         全民直播       斗鱼



- 社交APP

  <img src="assets/微信logo.jpg" width="80"/><img src="assets/陌陌logo.jpg" width="80"/>

  ​      微信           陌陌

- 智能硬件，物联网

  <img src="assets/摩拜单车logo.jpg" width="80"/><img src="assets/ofo的logo.jpg" width="80"/>

   摩拜单车      小黄车



## 知识点-短连接和长连接

### 1.目标

​	  即时通讯使用的是长连接，这里我们介绍一下短连接和长连接。

- [ ] 了解短连接和长连接

### 2.路径

1.  短连接
2. 长连接

### 3.讲解

#### 3.1 短连接

​     			

​	客户端和服务器每进行一次通讯，就建立一次连接，通讯结束就中断连接。

![1563089166702](assets/1563089166702.png)

​	

​	HTTP是一个简单的请求-响应协议，它通常运行在TCP之上。HTTP/1.0使用的TCP默认是短连接。



#### 3.2 长连接

​	是指在建立连接后可以连续多次发送数据，直到双方断开连接。

![1563089363824](assets/1563089363824.png)



​	HTTP从1.1版本起，底层的TCP使用的长连接。

​	使用长连接的HTTP协议，会在响应头加入代码：`Connection:keep-alive`



### 4.小结

#### 	4.1 通讯流程

​		短连接：创建连接 -> 传输数据 -> 关闭连接
​	
​		长连接：创建连接 -> 传输数据 -> 保持连接 -> 传输数据 -> …… -> 关闭连接

#### 	4.2 适用场景

​		短连接：并发量大，数据交互不频繁情况

​		长连接：数据交互频繁，点对点的通讯

#### 	4.3 通讯方式

| 方式   | 说明                                                         |
| ------ | :----------------------------------------------------------- |
| 短连接 | 我跟你发信息，必须等到你回复我或者等了一会等不下去了，就结束通讯了 |
| 长连接 | 我跟你发信息，一直保持通讯，在保持通讯这个时段，我去做其他事情的当中你回复我了，我能立刻你回复了我什么，然后可以回应或者不回应，继续做事 |

### 



## 知识点-websocket协议

### 1.目标

- [ ] 了解websocket协议

### 2.路径

1. websocket协议概述
2. websocket常用事件方法

### 3.讲解

#### 3.1 websocket协议概述

- WebSocket 是 HTML5 开始提供的一种在单个 TCP 连接上进行==全双工通讯==的协议。

  - 何谓全双工：全双工（Full Duplex）是通讯传输的一个术语。双方在通信时允许数据在两个方向上同时传输，它在能力上相当于两个单工通信方式的结合。全双工指可以同时进行信号的双向传输。指A→B的同时B→A，就像是双向车道。
  - 单工就就像是汽车的单行道，是在只允许甲方向乙方传送信息，而乙方不能向甲方传送 。

  参考资料：https://baike.baidu.com/item/%E5%85%A8%E5%8F%8C%E5%B7%A5/310007?fr=aladdin

- 在 WebSocket中，浏览器和服务器只需要完成一次握手，就可以创建持久性的连接，并进行双向数据传输。

- 在推送功能的实现技术上，相比使用Ajax 定时轮询的方式(setInterval)，WebSocket 更节省服务器资源和带宽。

- 服务器向客户端发送数据的功能是websocket协议的典型使用场景

  ![ws](assets\ws.png)



#### 3.2 websocket常用事件方法

以下 API 用于创建 WebSocket 对象。

```javascript
var Socket = new WebSocket(url, [protocol] );
```



**WebSocket 事件**

以下是 WebSocket 对象的相关事件。假定我们使用了以上代码创建了 Socket 对象：

| 事件    | 事件处理程序     | 描述                       |
| ------- | ---------------- | -------------------------- |
| open    | Socket.onopen    | 连接建立时触发             |
| message | Socket.onmessage | 客户端接收服务端数据时触发 |
| error   | Socket.onerror   | 通信发生错误时触发         |
| close   | Socket.onclose   | 连接关闭时触发             |

**WebSocket 方法**

| 方法           | 描述             |
| -------------- | ---------------- |
| Socket.send()  | 使用连接发送数据 |
| Socket.close() | 关闭连接         |

使用资料中的案例Spring-websocket演示WebSocket

输入: http://localhost:8080/Spring-websocket/login

### 4.小结

1. websocket协议: 基于TCP的全双工的协议 是长连接, 适合做及时通讯





## 知识点-十次方的im功能介绍  

### 1.目标

- [ ] 了解环信的基本使用

### 2.路径

### 3.讲解

1.  系统分析
2. 环信

#### 3.1 系统分析

前端页面使用十次方用户微服务认证用户身份，使用环信im云进行即时消息通信。

![](assets\十次方im功能架构图.jpg)

#### 3.2 环信im云介绍

环信im云是即时通讯云 PaaS 平台，开发者可以通过简单的SDK和REST API对接。

- 支持安卓，iOS，Web等客户端SDK对接  
- 提供单聊，群聊，聊天室等即时通讯功能
- 支持富媒体消息，实时音视频和各种自定义的扩展消息

##### 3.2.1 注册账号

网址：<https://console.easemob.com/user/register>

 ![1558171856354](assets/1558171856354.png)

##### 3.2.2 创建应用

1. 登录环信im云，按照下图进行操作

 ![1558172299424](assets/1558172299424.png)

 ![1558172515551](assets/1558172515551.png)

输入appname后，**appkey**会自动生成



2. 进入刚才创建的应用，获取appkey，orgname，client id，client secret等字段

   ![1558172675763](assets/1558172675763.png)

##### 3.2.3 接口测试-获取token

1. 使用环信提供的swagger接口调试页面测试接口

   页面网址：<http://api-docs.easemob.com/#/%E8%8E%B7%E5%8F%96token>

   ![1558172887029](assets/1558172887029.png)

2. 使用postman测试接口

   ![1558172939905](assets/1558172939905.png)

注意：请求方式选择**POST**



##### 3.2.4 im系统架构

![](assets\im系统架构图.jpg)



### 4.小结

1. 环信im云是即时通讯云 PaaS 平台
2. 环信im
   + 注册
   + 登录
   + 创建应用



## 知识点-十次方im功能

### 1.目标

- [ ] 使用环信im云实现十次方即时通讯功能

### 2.路径

1. 用户微服务实现
2. 即时通讯前端准备
3. 发送和接收消息

### 3.讲解

#### 3.1 用户微服务实现

1） 创建tensquare_user子模块

创建Maven工程

2） 在pom.xml中添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.46</version>
    </dependency>
    <dependency>
        <groupId>com.tensquare</groupId>
        <artifactId>tensquare_common</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>

    <!-- mybatis-plus begin -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatisplus-spring-boot-starter</artifactId>
        <version>${mybatisplus-spring-boot-starter.version}</version>
    </dependency>
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus</artifactId>
        <version>${mybatisplus.version}</version>
    </dependency>
    <!-- mybatis-plus end -->
</dependencies>
```



3） 编写application.yml配置文件

```yaml
server:
  port: 9008
spring:
  application:
    name: tensquare-user
  datasource: # 数据库连接四大属性
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.200.128:3306/tensquare_user?characterEncoding=utf-8
    username: root
    password: root
# Mybatis-Plus 配置
mybatis-plus:
#  mapper-locations: classpath:/mapper/*Mapper.xml
  #实体扫描，多个package用逗号或者分号分隔
  typeAliasesPackage: com.tensquare.article.pojo
  global-config:
    id-type: 1  #0:数据库ID自增   1:用户输入id
    db-column-underline: false
    refresh-mapper: true
    configuration:
      map-underscore-to-camel-case: true
      cache-enabled: true #配置的缓存的全局开关
      lazyLoadingEnabled: true #延时加载的开关
      multipleResultSetsEnabled: true #开启延时加载，否则按需加载属性
      log-impl: org.apache.ibatis.logging.stdout.StdOutImpl #打印sql语句,调试用
```



4）编写MyBatis配置Bean

```java
@Configuration
@MapperScan("com.tensquare.user.dao")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
```



5） 编写引导类

```java
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
```



6） 编写pojo

```java
@TableName("tb_user")
public class User implements Serializable {

    @TableId(type = IdType.INPUT)
    private String id;

    private String mobile;
    private String password;
    private String nickname;
    private String sex;
    private Date birthday;
    private String avatar;
    private String email;
    private Date regdate;
    private Date updatedate;
    private Date lastdate;
    private Long online;
    private String interest;
    private String personality;
    private Integer fanscount;
    private Integer followcount;
 
    //get set...
}
```



7） 编写dao

```java
public interface UserDao extends BaseMapper<User> {
}
```



8） 编写service

```java
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User login(User user) {
        return userDao.selectOne(user);
    }
}
```



9） 编写controller

```java
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(@RequestBody User user) {
        User result = userService.login(user);

        if (result != null) {
            return new Result(true, StatusCode.OK, "登录成功", result);
        }

        return new Result(false, StatusCode.OK, "登录失败");
    }
}
```



#### 3.2 即时通讯前端准备

访问环信IM开发文档/Web客户端 / SDK集成介绍 /Web IM 集成介绍

或者直接访问<http://docs-im.easemob.com/im/web/intro/integration>



1. 按照文档，使用git下载集成案例：

```bash
$ git clone https://github.com/easemob/webim.git
```

2. 复制案例中的\webim\sdk目录下的所有js文件到项目resources\static\js中

3. 复制webim\simpleDemo中的资料到resources\static中

效果如下：

![1566876831912](assets/1566876831912.png) 



测试demo.html，确认即时通讯的用户登录，发文本消息，效果如下

![1566876855802](assets/1566876855802.png) 



#### 3.3 发送和接收消息

复制Spring-websocket项目中的chatroom.jsp改造为chatroom.html，根据demo.html案例实现用户注册和登录和即时消息功能。最终效果：

```html
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>聊天室</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="edge"/>

    <script src="js/jquery-1.12.3.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <style>
        body {
            margin-top: 5px;
        }
    </style>

    <script src="WebIMConfig.js"></script>
    <script src="../js/webimSDK3.0.4.js"></script>
    <script src="../js/EMedia_x1v1.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-3">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">登录和注册</h3>
                </div>
                <div class="panel-body">
                    <div class="list-group">
                        <input type="text" class="form-control" id="userId" placeholder="用户id"/><br>
                        <button id="reg" type="button" class="btn btn-primary">注册</button>
                        <button id="login" type="button" class="btn btn-primary">登录</button>
                    </div>
                </div>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">消息接收者</h3>
                </div>
                <div class="panel-body">
                    <div class="list-group">
                        <input type="text" class="form-control" id="toUserId" placeholder="接收消息用户id"/><br>
                    </div>
                </div>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">群发系统广播</h3>
                </div>
                <div class="panel-body">
                    <input type="text" class="form-control" id="msg"/><br>
                    <button id="broadcast" type="button" class="btn btn-primary">发送</button>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title" id="talktitle"></h3>
                </div>
                <div class="panel-body">
                    <div class="well" id="log-container" style="height:400px;overflow-y:scroll">

                    </div>
                    <input type="text" id="myinfo" class="form-control col-md-12"/> <br>
                    <button id="send" type="button" class="btn btn-primary">发送</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var conn = {};
    console.log(WebIM, window.WebIM);
    WebIM.config = config;
    conn = WebIM.conn = new WebIM.default.connection({
        appKey: WebIM.config.appkey,
        isHttpDNS: WebIM.config.isHttpDNS,
        isMultiLoginSessions: WebIM.config.isMultiLoginSessions,
        host: WebIM.config.Host,
        https: WebIM.config.https,
        url: WebIM.config.xmppURL,
        apiUrl: WebIM.config.apiURL,
        isAutoLogin: false,
        heartBeatWait: WebIM.config.heartBeatWait,
        autoReconnectNumMax: WebIM.config.autoReconnectNumMax,
        autoReconnectInterval: WebIM.config.autoReconnectInterval,
        isStropheLog: WebIM.config.isStropheLog,
        delivery: WebIM.config.delivery
    })

    conn.listen({
        onOpened: function (message) {          //连接成功回调
            var myDate = new Date().toLocaleString();
            console.log("%c [opened] 连接已成功建立", "color: green");
            console.log(myDate);
            // rek();
            // alert(myDate + "登陆成功")

        },
        onClosed: function (message) {
            console.log("onclose:" + message);
            console.log(error);
        },         //连接关闭回调
        onTextMessage: function (message) {
            console.log('onTextMessage: ', message);
            alert(message.from)
            // $("#log-container").append(message.data);
            $("#log-container").append("<div class='bg-info'><label class='text-danger'>接收到id为"+message.from+"的消息:</label><div class='text-success'>"+message.data+"</div></div><br>");
            alert("end")
        }
    });

    var userId;
    var nickname;
    var password;
    //注册
    document.getElementById('reg').onclick = function () {
        userId = document.getElementById("userId").value;
        $.ajaxSettings.async = false
        $.get("/user/"+userId,function (data) {
            nickname = data.data.nickname;
            password = data.data.password;
        });

        var option = {
            username: userId,
            nickname: nickname,
            password: password,
            appKey: WebIM.config.appkey,
            success: function () {
                console.log('注册成功');
            },
            error: function () {
                console.log('注册失败');
            },
            apiUrl: WebIM.config.apiURL
        };
        conn.signup(option);
    };

    //登录
    document.getElementById('login').onclick = function () {
        userId = document.getElementById("userId").value;
        $.ajaxSettings.async = false
        $.get("/user/"+userId,function (data) {
            password = data.data.password;
        });

        // console.log(WebIM, window.WebIM);
        options = {
            apiUrl: WebIM.config.apiURL,
            user: userId,
            pwd: password,
            appKey: WebIM.config.appkey
        };
        conn.open(options);
        console.log(options)
    };

    //文本消息
    var conf = WebIM.config
    //var WebIM = WebIM.default
    WebIM.config = conf
    WebIM.message = WebIM.default.message
    WebIM.utils = WebIM.default.utils
    WebIM.debug = WebIM.default.debug
    WebIM.statusCode = WebIM.default.statusCode

    var myDate = new Date().toLocaleString();
    document.getElementById('send').onclick = function () {
        var tname = document.getElementById("toUserId").value;
        var tmsg = document.getElementById("myinfo").value;
        var id = conn.getUniqueId();                 // 生成本地消息id
        var msg = new WebIM.default.message('txt', id);      // 创建文本消息

        msg.set({
            msg: tmsg,                  // 消息内容
            to: tname,
            ext: {
                'time': myDate
            },                       // 接收消息对象（用户id）
            success: function (id, serverMsgId) {
                console.log('send private text Success');
                msgText = msg.body.msg;
            },
            fail: function (e) {
                console.log("Send private text error");
            }
        });
        msg.body.chatType = 'singleChat';
        conn.send(msg.body);
        $("#log-container").append("<div class='bg-info'><label class='text-danger'>发送给id为"+tname+"的消息:</label><div class='text-success'>"+tmsg+"</div></div><br>");
        console.log(msg);

    };
    
</script>

</body>
</html>
```

### 4.小结







# 第二章-接口加密    

## 知识点-接口加密方式

### 1.目标

- [ ] 了解接口加密业务需求

### 2.路径

1. 为什么要对接口加密
2. 加密方式

### 3.讲解

#### 3.1 业务场景介绍

数据安全性 - 抓包工具

​	 <img src="assets/wireshark-logo.jpg" width="80"/>  			<img src="assets/fiddler-logo.jpg" width="80"/>            		 <img src="assets/charles-logo.jpg" width="80"/>
​         wireshark			    fiddler 			    charles


系统明文传输的数据会被不明身份的人用抓包工具抓取，从而威胁系统和数据的**安全性**

#### 3.2 加密方式

##### 3.2.1 摘要算法

​	消息摘要是==把任意长度的输入揉和而产生长度固定的信息==。

​	消息摘要算法的主要特征是加密过程不需要密钥，并且经过加密的数据无法被解密，只有输入相同的明文数据经过相同的消息摘要算法才能得到相同的密文。消息摘要算法不存在密钥的管理与分发问题，适合于分布式网络上使用。

消息摘要的主要特点有：

* 无论输入的消息有多长，计算出来的消息摘要的长度总是固定的。
* 消息摘要看起来是“随机的”。这些数据看上去是胡乱的杂凑在一起的。
* 只要输入的消息不同，对其进行摘要后产生的摘要消息也必不相同；但相同的输入必会产生相同的输出。
* 只能进行正向的消息摘要，而无法从摘要中恢复出任何消息，甚至根本就找不到任何与原信息相关的信息。
* 虽然“碰撞”是肯定存在的，但好的摘要算法很难能从中找到“碰撞”。即无法找到两条不同消息，但是它们的摘要相同。

常见的摘要算法：CRC、==MD5==、SHA等



##### 3.2.2 对称加密

加密和解密使用相同[密钥](https://baike.baidu.com/item/%E5%AF%86%E9%92%A5)的[加密算法](https://baike.baidu.com/item/%E5%8A%A0%E5%AF%86%E7%AE%97%E6%B3%95)。

对称加密的特点：

- 速度快，通常在消息发送方需要加密大量数据时使用。
- 密钥是控制加密及解密过程的指令。
- 算法是一组规则，规定如何进行加密和解密。

==典型应用场景：离线的大量数据加密（用于存储的）==

常用的加密算法：**DES**、3DES、**AES**、TDEA、Blowfish、RC2、RC4、RC5、IDEA、SKIPJACK等。

对称加密的工作过程如下图所示

![](assets\对称加密示意图.png)

​	加密的安全性不仅取决于加密算法本身，密钥管理的安全性更是重要。如何把密钥安全地传递到解密者手上就成了必须要解决的问题。



##### 3.2.3 非对称加密

​	非对称加密算法是一种**密钥**的保密方法，加密和解密使用两个不同的密钥，公开密钥（publickey:简称公钥）和私有密钥（privatekey:简称私钥）。公钥与私钥是一对，如果用公钥对数据进行加密，只有用对应的私钥才能解密。

​	非对称加密算法的特点：

- 算法强度复杂
- 加密解密速度没有对称密钥算法的速度快

经典应用场景：数字签名（私钥加密，公钥验证）

常用的算法：**RSA**、Elgamal、背包算法、Rabin、D-H、ECC（椭圆曲线加密算法）。

非对称加密算法示意图如下

![](assets\非对称加密示意图.jpg)

#### 3.3应用-数字签名

​	数字签名（又称[公钥](https://baike.baidu.com/item/%E5%85%AC%E9%92%A5)数字签名）是一种类似写在纸上的普通的物理签名，是使用了公钥加密领域的技术实现，用于鉴别数字信息的方法。

​	数字签名通常使用私钥生成签名，使用公钥验证签名。

签名及验证过程：

1. 发送方用一个哈希函数（例如MD5）从报文文本中生成[报文摘要](https://baike.baidu.com/item/%E6%8A%A5%E6%96%87%E6%91%98%E8%A6%81),然后用自己的私钥对这个摘要进行加密
2. 将加密后的摘要作为报文的数字签名和报文一起发送给接收方
3. 接收方用与发送方一样的哈希函数从接收到的原始报文中计算出报文摘要，
4. 接收方再用发送方的公用密钥来对报文附加的数字签名进行解密
5. 如果这两个摘要相同、接收方就能确认该数字签名是发送方的。

![](assets/数字签名及验证过程.jpg)

数字签名验证的两个作用：

- 确定消息确实是由发送方签名并发出来的
- 确定消息的完整性

### 4.小结

1. 为什么接口要加密?

   ​	保证安全. 前端携带的参数需要先加密, 我们在后台再解密

2. 加密方式 

   + 摘要算法: 相同的数据加密后的数据是固定的 MD5
   + 对称加密: 加密和解密使用的是同一个秘钥和算法,  DES AES
   + 非对称加密: 私共钥, 加密和解密的是不同的秘钥.  RSA

## 知识点-OpenSSL生成rsa密钥对

### 1.目标

- [ ] 掌握常用加密算法和密钥格式

### 2.路径

1. RSA算法的密钥格式
2. openssl生成rsa密钥对的命令

### 3.讲解

#### 3.1 RSA算法的密钥格式
密钥长度介于 512 - 65536 之间（JDK 中默认长度是1024），且必须是64 的倍数。密钥的常用文件格式有pem（文本存储）或者der（二进制存储）。

当使用Java API生成RSA密钥对时,公钥以X.509格式编码,私钥以PKCS#8格式编码

RSA使用pkcs协议定义密钥的存储结构等内容

| 协议       | 说明                                                         |
| ---------- | ------------------------------------------------------------ |
| PKCS#1     | 定义了RSA公钥函数的基本格式标准，特别是数字签名。            |
| PKCS#2     | 涉及了RSA的消息摘要加密，已被并入PKCS#1中。                  |
| PKCS#3     | Diffie-Hellman密钥协议标准。                                 |
| PKCS#4     | 最初是规定RSA密钥语法的，现已经被包含进PKCS#1中。            |
| PKCS#5     | 基于口令的加密标准，描述了使用由口令生成的密钥来加密8位位组串并产生一个加密的8位位组串的方法。PKCS#5可以用于加密私钥，以便于密钥的安全传输（这在PKCS#8中描述）。 |
| PKCS#6     | 扩展证书语法标准，定义了提供附加实体信息的X.509证书属性扩展的语法。 |
| PKCS#7     | 密码消息语法标准。为使用密码算法的数据规定了通用语法，比如数字签名和数字信封。 |
| **PKCS#8** | 私钥信息语法标准。定义了私钥信息语法和加密私钥语法，其中私钥加密使用了PKCS#5标准。 |
| PKCS#9     | 可选属性类型。                                               |
| PKCS#10    | 证书请求语法标准。                                           |
| PKCS#11    | 密码令牌接口标准。                                           |
| PKCS#12    | 个人信息交换语法标准。                                       |
| PKCS#13    | 椭圆曲线密码标准。                                           |
| PKCS#14    | 伪随机数产生标准。                                           |
| PKCS#15    | 密码令牌信息语法标准。                                       |

* pkcs标准详细说明：https://www.rfc-editor.org/search/rfc_search_detail.php?title=pkcs&pubstatus%5B%5D=Any&pub_date_type=any
* RSA官方网站：https://www.rsa.com

#### 3.2 openssl生成rsa密钥对的命令

1. `openssl genrsa -out ../mycerts/rsa_private_key.pem 2048` 

   生成rsa私钥，文本存储格式，长度2048

   ![1558277615761](assets\1558277615761.png)

2. `openssl rsa -in ../mycerts/rsa_private_key.pem -pubout -out ../mycerts/rsa_public_key_2048.pub`

   根据私钥生成对应的公钥

   ![1558277803115](assets/1558277803115.png)

3. `openssl pkcs8 -topk8 -inform PEM -in ../mycerts/rsa_private_key.pem -outform PEM -nocrypt > ../mycerts/rsa_private_key_pkcs8.pem`

   私钥转化成pkcs8格式

   ![1558277908856](assets/1558277908856.png)


### 4.小结



## 搭建接口加密微服务

### 1.目标

- [ ] 实现十次方的接口加密微服务

### 2.分析

接口加解密请求参数的流程

![1563096130123](assets/1563096130123.png)

### 3.实现

#### 3.1注册中心微服务

+ 在十次方parent父工程pom.xml中添加SpringCloud依赖

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Greenwich.SR1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

+ 创建Maven工程tensquare_eureka，在pom.xml中添加以下依赖：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
</dependencies>
```

+ 添加配置文件：

```yaml
server:
  port: 6868
eureka:
  client:
    register-with-eureka: false #是否将自己注册到eureka中
    fetch-registry: false #是否从eureka中获取信息
    service-url:
      defaultZone: http://127.0.0.1:${server.port}/eureka/
```

+ 编写启动类：

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
```

#### 3.2 注册文章微服务

+ 在pom.xml中添加Eureka依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```



+ 修改配置文件，使用Eureka

```yaml
eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:6868/eureka/
  instance:
    prefer-ip-address: true
```



+ 在ArticleApplication添加@EnableEurekaClient依赖

```java
@SpringBootApplication
//配置Mapper包扫描
@MapperScan("com.tensquare.article.dao")
@EnableEurekaClient
public class ArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }

    @Bean
    public IdWorker createIdWorker() {
        return new IdWorker(1, 1);
    }
}
```

#### 3.3 tensquare_encrypt网关微服务

在tensquare_parent父工程下新建tensquare_encrypt子模块，并按下面的步骤添加配置和代码

1. 在pom.xml文件中添加以下配置

   ```xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
       </dependency>
   </dependencies>
   ```


2. 在resource文件夹下新建application.yml文件，并添加如下配置

   ```yaml
   server:
     port: 9013
   spring:
     application:
       name: tensquare-encrypt
   zuul:
     routes:
       tensquare-article: #文章
         path: /article/** #配置请求URL的请求规则
         serviceId: tensquare-article #指定Eureka注册中心中的服务id
         strip-prefix: true
         sentiviteHeaders:
         customSensitiveHeaders: true

   eureka:
     client:
       service-url:
         defaultZone: http://127.0.0.1:6868/eureka/
     instance:
       prefer-ip-address: true

   ```


3. 新建com.tensquare.encrypt包，并在包下新建启动类EncryptApplication，添加如下代码

   ```java
   package com.tensquare.encrypt;

   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
   import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

   @SpringBootApplication
   @EnableEurekaClient
   @EnableZuulProxy
   public class EncryptApplication {
      public static void main(String[] args) {
          SpringApplication.run(EncryptApplication.class);
      }
     }
   ```

#### 3.4使用tensquare_encrypt进行接口加密

1. 将rsa相关的工具类复制到在com.tensquare.encrypt包下

   ![1564303565483](assets/1564303565483.png) 

   + 将openssl生成的公钥和私钥添加进RsaKeys中

     公钥变量：`private static final String serverPubKey`

     私钥变量：`private static final String serverPrvKeyPkcs8`

2. 在src/test/java文件夹下创建测试用例EncryptTest

```java
package com.tensquare.encrypt.test;

import com.tensquare.encrypt.EncryptApplication;
import com.tensquare.encrypt.rsa.RsaKeys;
import com.tensquare.encrypt.service.RsaService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EncryptApplication.class)
public class EncryptTest {

    @Autowired
    private RsaService rsaService;

    @Test
    //加密
    public void fun01() throws Exception {
        String data = "{\"columnid\": \"1\", \"title\":\"java\"}";
        String rsaEncryptDataPEM = rsaService.rsaEncryptDataPEM(data, RsaKeys.getServerPubKey());
        System.out.println("加密后的数据:" + rsaEncryptDataPEM);

    }
    @Test
    //解密
    public void fun02() throws Exception {
        String data = "IManQGHLGMJ62MQibJH1pRJljF76RxkqOoT4BZL1YRguf9vmL69Drz3IX9BbH7PjfqLIObNM0L1ZxTDl5BT1XGo+bs/LJhKx4VakYU91HO7NNaXtnpvLev8MlZnY7MGzc2lyxxO+gbgNiffsRxm1D6fqbP65hd+V9qtjf+bZvyzMTcKdAsFD/ESXVj4WygNCm83/KMmuN0xwazyYJpTAzuzLBSncMqxnborvjjILzNspu17zmkzaOtz2KAc/0OBZ5/a8txomW+Hf/rc4F1/RQ5yThNkTxKN5IrCyiaXGhDcJKUrG+1LaLYLAt2kiCaIlj8qRO2Gp4LDEXRmw9HXC1Q==";
        String rsaEncryptDataPEM = rsaService.rsaDecryptDataPEM(data, RsaKeys.getServerPrvKeyPkcs8());
        System.out.println("解密后的数据:" + rsaEncryptDataPEM);

    }
}
```

3. 编写filter.在com.tensquare.encrypt包下新建filter包，然后新建过滤器类RSARequestFilter，添加下面的代码

```java
package com.tensquare.encrypt.filter;

import com.google.common.base.Charsets;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.tensquare.encrypt.rsa.RsaKeys;
import com.tensquare.encrypt.service.RsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: yp
 */
@Component
public class RSARequestFilter extends ZuulFilter {

    @Autowired
    private RsaService rsaService;

    @Override
    public String filterType() {
        return "pre";
    }


    @Override
    public int filterOrder() {
        return 1;
    }


    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        try {
            //1.获得request
            RequestContext requestContext = RequestContext.getCurrentContext();
            HttpServletRequest request = requestContext.getRequest();
            //2.获得请求参数(流的方式)
            ServletInputStream is = request.getInputStream();
            String params = StreamUtils.copyToString(is, Charsets.UTF_8);
          System.out.println("加密的请求参数:" + params);
            //3.进行解密
            if (!StringUtils.isEmpty(params)) {
                  
                params = rsaService.rsaDecryptDataPEM(params, RsaKeys.getServerPrvKeyPkcs8());
                System.out.println("解密的请求参数:" + params);

                //4.转发到微服务
                if(!StringUtils.isEmpty(params)){
                    byte[] bytes = params.getBytes();
                    requestContext.setRequest(new HttpServletRequestWrapper(request){
                        @Override
                        public ServletInputStream getInputStream() throws IOException {
                            return new ServletInputStreamWrapper(bytes);
                        }

                        @Override
                        public int getContentLength() {
                            return bytes.length;
                        }

                        @Override
                        public long getContentLengthLong() {
                            return bytes.length;
                        }
                    });

                }
                //5.设置request请求头中的Content-Type为application/json，否则api接口模块需要进行url转码操作
                requestContext.addZuulRequestHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON) + ";charset=UTF-8");
            }



        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}

```

4. 测试请求参数加解密微服务

   启动tensquare_eureka，tensquare_article，tensquare_encrypt，使用EncryptTest类加密请求参数，然后使用postman进行接口调用测试


### 4.小结






