---
typora-copy-images-to: assets
---

# day01-项目介绍和工程搭建

# 学习目标

- [ ] 了解十次方项目需求
- [ ] 了解前后端分离开发模式
- [ ] 理解RESTful
- [ ] 完成项目开发环境搭建
- [ ] 完成父工程、公共模块和文章微服务的搭建
- [ ] 掌握mybatis plus的使用，并开发完成文章微服务中文章的增删改查功能
- [ ] 掌握公共异常处理类的使用

# 第一章-项目介绍和设计

## 知识点-项目介绍

### 1.目标

- [ ] 了解十次方项目需求

### 2.路径

1. 项目介绍
2. 项目需求

### 3.讲解

#### 3.1 项目介绍

​	十次方是程序员的专属==社交平台==，包括头条、问答、活动、交友、吐槽、招聘六大频道。

![img](assets/1538267116128.png)

​	十次方名称的由来：2的10次方为1024，程序员都懂的。

​	如果你是一位技术大咖，那么赶快发布文章，增加知名度吧。

​	如果你是一名技术小白，那么赶快到问答频道寻求帮助的，这里高手如云哦！

​	如果你不想错过各种技术交流会，那么请经常关注活动频道吧。

​	如果你还是单身，那么赶快到交友频道找到你心仪的另一半。

​	如果你有太多的苦恼，那么赶快吐个槽吧。

​	如果你正在找工作或是想跳槽拿高薪，那么来招聘频道淘金吧。

#### 3.2 项目需求

> 详见： 资源\文档\十次方需求规格说明书.docx

### 4.小结

1. 十次方是针对 程序员 社交平台, 包含6大频道. 大家写简历的时候可以参考描述作为项目的描述



## 知识点-系统设计

### 1.目标

- [ ] 了解前后端分离开发模式

### 2.路径

1. 开发模式
2. 技术选型
3. 技术架构
4. 微服务模块划分
5. 数据库表结构分析
6. API文档

### 3.讲解

#### 3.1 开发模式

​	十次方项目采用==前后端分离==的开发模式

#### 3.2 技术选型

​	后端：springboot + springcloud + mybatis plus + mysql5.7

​	前端：nodejs + NUXT + elementUI + vue

#### 3.3 系统架构

​	采用前后端分离的系统架构

![1557635014480](assets/1557635014480.png)

1. jsp (很低   很老,  100%需要你写)  
2. 不是前后端分离 html  (有可能参与写) 传智健康, 旅游小练习
3. 前后端分离 (几乎不写前端)

##### 3.3.1以前老的方式

- 产品经理/领导/客户提出需求
- UI做出设计图
- 前端工程师做html页面
- 后端工程师将html页面套成jsp页面（前后端强依赖，后端必须要等前端的html做好才能套jsp。如果html发生变更，就更痛苦了，开发效率低）
- 集成出现问题
- 前端返工
- 后端返工
- 二次集成
- 集成成功
- 交付

##### 3.3.2前后端分离方式

- 产品经理/领导/客户提出需求  需求文档
- UI做出设计图
- 前后端约定接口&数据&参数   
- 前后端并行开发（无强依赖，可前后端并行开发，如果需求变更，只要接口&参数不变，就不用两边都修改代码，开发效率高）  
- 前后端集成
- 前端页面调整
- 集成成功
- 交付

![img](assets/tu_1.png)







#### 3.4 微服务模块划分

| 模块名称              | 模块中文名称   |
| --------------------- | -------------- |
| ==tensquare_common==  | 公共模块       |
| tensquare_base        | 基础微服务     |
| ==tensquare_article== | 文章微服务     |
| tensquare_friend      | 交友微服务     |
| tensquare_gathering   | 活动微服务     |
| tensquare_qa          | 问答微服务     |
| tensquare_recruit     | 招聘微服务     |
| tensquare_user        | 用户微服务     |
| tensquare_spit        | 吐槽微服务     |
| tensquare_search      | 搜索微服务     |
| ==tensquare_web==     | 前台微服务网关 |
| tensquare_manager     | 后台微服务网关 |
| ==tensquare_eureka==  | 注册中心       |
| tensquare_config      | 配置中心       |
| tensquare_sms         | 短信微服务     |
| ==tensquare_notice==  | 消息通知微服务 |



#### 3.5 数据库表结构分析

​	采用的分库分表设计，每个微服务模块为1个独立的数据库。

​	tensquare_article  文章

​	tensquare_base    基础

​	tensquare_friend   交友

​	tensquare_gathering 活动

​	tensquare_qa 问答

​	tensquare_recruit 招聘

​	tensquare_user   用户

​	tensquare_spit   吐槽

> 详见 资源\文档\十次方数据库文档.xlsx

#### 3.6 API文档

课程提供了前后端开发接口文档（采用Swagger语言进行编写），并与Nginx进行了整
合。双击Nginx执行文件启动后，在地址栏输入http://localhost:801 即可访问API文档

前后端约定的返回码列表：

| 状态描述         | 返回码 |
| ---------------- | ------ |
| 成功             | 20000  |
| 失败             | 20001  |
| 用户名或密码错误 | 20002  |
| 权限不足         | 20003  |
| 远程调用失败     | 20004  |
| 重复操作         | 20005  |

### 4.小结

1. 前后端分离项目
2. 技术

![1566717314578](assets/1566717314578.png) 



## 知识点-RESTful架构说明

### 1.目标

- [ ] 理解RESTful

### 2.路径

1. RESTful介绍
2. 接口规范

### 3.讲解

#### 3.1 何为RESTful 

​		RESTful架构，就是==目前最流行的一种互联网软件架构==。它结构清晰、符合标准、易于理解、扩展方便，所以正得到越来越多网站的采用。REST这个词，是==Roy ThomasFielding==在他2000年的博士论文中提出的 .REST 是Representational State Transfer的缩写，翻译是”表现层状态转化”。 可以总结为一句话：REST是所有Web应用都应该遵守的架构设计指导原则。面向资源是REST最明显的特征，对于同一个资源的一组不同的操作。资源是服务器上一个可命名的抽象概念，资源是以名词为核心来组织的，首先关注的是名词。REST要求，必须通过统一的接口来对资源执行各种操作。对于每个资源只能执行一组有限的操作。
​	8个HTTP方式：==GET/POST/PUT/DELETE==/OPTIONST/RACE/HEAD/CONNECT

- 实例

```
保存
	传统：http://localhost:8080/user/save
	REST：http://localhost:8080/user						POST	执行保存

更新
	传统：http://localhost:8080/user/update?id=1
	REST：http://localhost:8080/user/1				   	PUT	执行更新  1就是id

删除	
	传统：http://localhost:8080/user/delete?id=1
	REST：http://localhost:8080/user/1					DELETE	执行删除	

查询
	传统：http://localhost:8080/user/findAll
	REST：http://localhost:8080/user						GET	查所有

	传统：http://localhost:8080/user/findById?id=1
	REST：http://localhost:8080/user/1 				  GET	根据id查1个
```

#### 3.2 接口规范

​	十次方项目使用GET、POST、PUT、DELETE四种方法

​	幂等性：不论你请求多少次，资源的状态是一样的。

##### 3.2.1 GET

- 安全且幂等     
- 获取表示
- 变更时获取表示（缓存）

==适合查询类的接口使用==



##### 3.2.2 POST

- 不安全且不幂等  
- 使用服务端管理的（自动产生）的实例号创建资源
- 创建子资源
- 部分更新资源
- 如果没有被修改，则不过更新资源（乐观锁）

==适合数据提交类的接口使用==

#####  3.2.3 PUT

- 不安全但幂等     
- 用客户端管理的实例号创建一个资源
- 通过替换的方式更新资源
- 如果未被修改，则更新资源（乐观锁）

 ==适合更新数据的接口使用==

#####  3.2.4 DELETE

- 不安全但幂等  
- 删除资源

==适合删除数据的接口使用==

请求返回响应码：

| 代码    | 含义                                                         |
| ------- | ------------------------------------------------------------ |
| ==200== | （OK）- 如果现有资源已被更改                                 |
| 201     | （created）- 如果新资源被创建                                |
| 202     | （accepted）- 已接受处理请求但尚未完成（异步处理）           |
| 301     | （Moved Permanently）- 资源的URI被更新                       |
| 303     | （See Other）- 其他（如，负载均衡）                          |
| ==400== | （bad request）- 指代坏请求                                  |
| ==404== | （not found）- 资源不存在                                    |
| 406     | （not acceptable）- 服务端不支持所需表示                     |
| 409     | （conflict）- 通用冲突                                       |
| 412     | （Precondition Failed）- 前置条件失败（如执行条件更新时的冲突） |
| 415     | （unsupported media type）- 接受到的表示不受支持             |
| ==500== | （internal server error）- 通用错误响应                      |
| 503     | （Service Unavailable）- 服务当前无法处理请求                |

### 4.小结

1. RESTful一种风格

2. 利用了Http协议的请求的几个动词

   + get   一般做查询       安全幂等
   + post  一般做新增      不安全 不幂等
   + put   一般做更新       不安全 幂等
   + delete  一般做删除    不安全 幂等

   



# 第二章-项目工程的搭建

## 知识点-项目开发准备

### 1.目标

- [ ] 完成项目开发环境搭建

### 2.路径

1. 开发环境介绍
2. mysql建库建表
3. 接口测试工具postman

### 3.讲解

#### 3.1 开发环境介绍

- 虚拟系统环境 VMware Workstation
- 虚拟机系统 CentOS 7
- 容器 docker
- JDK1.8
- 数据库 mysql 5.7
- 开发工具 idea 
- 项目构建工具 maven 

所有的第三方工具如**mysql**等都是运行在docker容器中的

`注：虚拟机的帐户名root  密码itcast`

#### 3.2 mysql建库建表

进入安装了docker的虚拟机中，按以下顺序执行命令

（1）下载镜像（此步可省略）

```
docker pull centos/mysql-57-centos7
```

注：docker默认从国外的镜像网站拉取镜像，速度很慢。可以使用国内的阿里云镜像加速站点提升镜像拉取速度。具体步骤可以参考文档

> docker配置国内镜像加速站点.pdf

（2）创建容器

```shell
docker run -di --name=tensquare_mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 centos/mysql-57-centos7
```

（3）连接MYSQL ，并执行资料中的建表脚本，创建article数据库



#### 3.3 接口测试工具postman

postman是一款强大网页调试工具

- 能够发送任何类型的HTTP 请求 (GET，HEAD， POST，PUT。。。)
- 附带任意数量的参数

### 4.小结

1. 工作里面一定要==注意环境的统一(版本)==

## 知识点-项目工程搭建

### 1.目标

- [ ] 完成父工程、公共模块的搭建

### 2.路径

1. 搭建父工程
2. 搭建公共子模块

### 3.实现

#### 3.1 父工程搭建

步骤:

1. 创建Maven工程tensquare_parent(pom)
2. 在pom.xml文件添加坐标
3. 删除src



创建项目类型为maven的父工程

1. 打开idea开发工具

2. 选择菜单file-new project ,弹出窗口中左侧菜单选择Maven ，点击next按钮

   ![1557482311840](assets/1557482311840.png)


3. GroupId填写com.tensquare，ArtifacetId填写tensquare_parent，点击next按钮

   ![1557482444471](assets/1557482444471.png)

4. 点击Finish 完成

5. 修改pom.xml文件，添加以下内容

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
   
       <groupId>com.tensquare</groupId>
       <artifactId>tensquare_parent</artifactId>
       <version>1.0-SNAPSHOT</version>
       <packaging>pom</packaging>
   
       <parent>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-parent</artifactId>
           <version>2.1.4.RELEASE</version>
           <relativePath/>
       </parent>
   
       <properties>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
           <maven.compiler.source>1.8</maven.compiler.source>
           <maven.compiler.target>1.8</maven.compiler.target>
           <mybatisplus-spring-boot-starter.version>1.0.5</mybatisplus-spring-boot-starter.version>
           <mybatisplus.version>2.2.0</mybatisplus.version>
           <fastjson.version>1.2.39</fastjson.version>
           <gson.version>2.8.0</gson.version>
       </properties>
   
       <dependencies>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
           </dependency>
       </dependencies>
   
       <repositories>
           <repository>
               <id>spring-snapshots</id>
               <name>Spring Snapshots</name>
               <url>https://repo.spring.io/snapshot</url>
               <snapshots>
                   <enabled>true</enabled>
               </snapshots>
           </repository>
           <repository>
               <id>spring-milestones</id>
               <name>Spring Milestones</name>
               <url>https://repo.spring.io/milestone</url>
               <snapshots>
                   <enabled>false</enabled>
               </snapshots>
           </repository>
       </repositories>
   
       <pluginRepositories>
           <pluginRepository>
               <id>spring-snapshots</id>
               <name>Spring Snapshots</name>
               <url>https://repo.spring.io/snapshot</url>
               <snapshots>
                   <enabled>true</enabled>
               </snapshots>
           </pluginRepository>
           <pluginRepository>
               <id>spring-milestones</id>
               <name>Spring Milestones</name>
               <url>https://repo.spring.io/milestone</url>
               <snapshots>
                   <enabled>false</enabled>
               </snapshots>
           </pluginRepository>
       </pluginRepositories>
   
   
   </project>
   ```


#### 3.2 搭建公共子模块

##### 3.2.1 搭建子模块步骤

步骤:

1. 创建Maven工程tensquare_common(jar). 继承tensquare_parent
2. 拷贝工具类
3. 拷贝公共实体类



1. 右键点击父工程tensquare_parent，选择 New -> Module 弹出窗口选择Maven ,点击next按钮

2. ArtifacetId填写tensquare_common，点击next按钮

   ![1557487423299](assets/1557487423299.png)

3. 点击finish

##### 3.2.2 创建公共实体类和工具类

1. 新建com.tensquare.entity包，包下创建Result类，用于controller返回结果

   ```java
   package com.tensquare.entity;
   
   import java.io.Serializable;
   
   public class Result implements Serializable {
    private boolean flag;//是否成功
       private Integer code;// 返回码
       private String message;//返回信息
       private Object data;// 返回数据
   
       public Result() {
       }
   
       public Result(boolean flag, Integer code, String message, Object data) {
           this.flag = flag;
           this.code = code;
           this.message = message;
           this.data = data;
       }
   
   
       public Result(boolean flag, Integer code, String message) {
           super();
           this.flag = flag;
           this.code = code;
           this.message = message;
       }

       public boolean isFlag() {
           return flag;
       }

       public void setFlag(boolean flag) {
           this.flag = flag;
       }

       public Integer getCode() {
           return code;
       }

       public void setCode(Integer code) {
           this.code = code;
       }

       public String getMessage() {
           return message;
       }

       public void setMessage(String message) {
           this.message = message;
       }

       public Object getData() {
           return data;
       }
   
       public void setData(Object data) {
           this.data = data;
       }
   }
   ```




2. 创建类PageResult ，用于返回分页结果

   ```java
   package com.tensquare.entity;
   
   import java.io.Serializable;
   import java.util.List;
   
   public class PageResult<T> implements Serializable {
       private Long total;
       private List<T> rows;
   
       public PageResult() {
       }
   
       public PageResult(Long total, List<T> rows) {
           this.total = total;
           this.rows = rows;
       }
   
       public Long getTotal() {
           return total;
       }
   
       public void setTotal(Long total) {
           this.total = total;
       }
   
       public List<T> getRows() {
           return rows;
       }
   
       public void setRows(List<T> rows) {
           this.rows = rows;
       }
   }
   ```




3. 返回码定义类

   ```java
   package com.tensquare.entity;
   
   /**
    * @Description:
    * @Author: yp
    */
   public class StatusCode {
       public static final int OK = 20000;//成功
       public static final int ERROR = 20001;//失败
       public static final int LOGINERROR = 20002;//用户名或密码错误
       public static final int ACCESSERROR = 20003;//权限不足
       public static final int REMOTEERROR = 20004;//远程调用失败
       public static final int REPERROR = 20005;//重复操作
   }
   
   ```


4. 分布式ID生成器

   课程中已经提供了分布式ID生成器

   > 资源\微服务相关\工具类\IdWorker.java

   tensquare_common工程创建util包，将IdWorker.java直接拷贝到tensquare_common工程的util包中。


不能使用数据库本身的自增功能来产生主键值，原因是生产环境为分片部署的。

 使用snowflake （雪花）算法（twitter出品）生成***唯一***的主键值

   ![1557488319373](assets/1557488319373.png)

   * 41bit的时间戳可以支持该算法使用到2082年

   * 10bit的工作机器id可以支持1024台机器

   * 序列号支持1毫秒产生4096个自增序列id

   * 整体上按照时间自增排序

   * 整个分布式系统内不会产生ID碰撞

   * 每秒能够产生26万ID左右  

     

### 4.小结

1. tensquare_parent  版本固定, 引入SpringBoot, web和test模块的启动项
2. tensquare_common 公共模块
   + 工具类
   + 公共实体类



# 第三章-文章微服务

## 知识点-文章微服务搭建

### 1.目标

- [ ] 完成文章微服务的搭建

### 2.路径

1. 基本环境的搭建
   + 创建tensquare_article子工程
   + 添加坐标
   + 创建application.yml
   + 创建启动类(注册IdWork)
2. 集成mybatis plus

### 3.讲解

#### 3.1基本环境的搭建

  1. 在tensquare_parent项目下创建tensquare_article模块
  2. 修改tensquare_article模块的pom.xml文件，添加以下依赖

```xml
<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <dependency>
        <groupId>com.tensquare</groupId>
        <artifactId>tensquare_common</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

3. 在resources文件夹下创建application.yml，并添加以下内容

```yaml
server:
  port: 9004 # 服务访问端口号
spring:
  application:
    name: tensquare-article #服务名称
  datasource: # 数据库连接四个属性
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.147.177:3306/tensquare_article?characterEncoding=utf-8
    username: root
    password: 123456
```

4. 创建com.tensquare.article包，并创建ArticleApplication启动类

```java
package com.tensquare.article;

import com.tensquare.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Description:
 * @Author: yp
 */
@SpringBootApplication
public class ArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class,args);
    }
    
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

}

```



#### 3.2集成mybatis plus

##### 3.2.1 表结构分析

tensquare_article数据库，tb_article表

| 文章表     | tb_article |          |                     |
| ---------- | ---------- | -------- | ------------------- |
| 字段名称   | 字段含义   | 字段类型 | 备注                |
| id         | ID         | 文本     |                     |
| columnid   | 专栏ID     | 文本     |                     |
| userid     | 用户ID     | 文本     |                     |
| title      | 文章标题   | 文本     |                     |
| content    | 文章内容   | 文本     |                     |
| image      | 文章封面   | 文本     |                     |
| createtime | 发表日期   | 日期     |                     |
| updatetime | 修改日期   | 日期     |                     |
| ispublic   | 是否公开   | 文本     | 0：不公开           |
| istop      | 是否置顶   | 文本     | 0：不置顶           |
| visits     | 浏览量     | 整型     |                     |
| thumbup    | 点赞数     | 整型     |                     |
| comment    | 评论数     | 整型     |                     |
| state      | 审核状态   | 文本     | 0：未审核 1：已审核 |
| channelid  | 所属频道   | 整型     | 关联频道表ID        |
| url        | URL地址    | 文本     |                     |
| type       | 文章类型   | 文本     | 0：分享             |

##### 3.2.2 集成mybatis plus   

1. mybatis plus概述

   * 是对Mybatis框架的二次封装和扩展
   * 纯正血统：完全继承原生 Mybatis 的所有特性
   * 最少依赖：仅仅依赖Mybatis以及Mybatis-Spring
   * 性能损耗小：启动即会自动注入基本CURD ，性能无损耗，直接面向对象操作 
   * 自动热加载：Mapper对应的xml可以热加载，大大减少重启Web服务器时间，提升开发效率
   * 性能分析：自带Sql性能分析插件，开发测试时，能有效解决慢查询
   * 全局拦截：提供全表delete、update操作智能分析阻断
   * 避免Sql注入：内置Sql注入内容剥离器，预防Sql注入攻击

   github:https://github.com/baomidou/mybatis-plus

   官网: https://mybatis.plus/

2. 在pom.xml文件中引入相关依赖

```xml
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
```



3. 在配置文件application.yml中添加相关配置

```
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
```

4. 修改启动类，增加Mapper扫描注解

```java
@SpringBootApplication
//Mapper扫描注解
@MapperScan("com.tensquare.article.dao")
public class ArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }
}
```



5. 创建pojo

```java
package com.tensquare.article.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("tb_article")
public class Article implements Serializable {

    @TableId(type = IdType.INPUT)
    private String id;//ID

    private String columnid;    //专栏ID
    private String userid;      //用户ID
    private String title;       //标题
    private String content;     //文章正文
    private String image;       //文章封面
    private Date createtime;    //发表日期
    private Date updatetime;    //修改日期
    private String ispublic;    //是否公开
    private String istop;       //是否置顶
    private Integer visits;     //浏览量
    private Integer thumbup;    //点赞数
    private Integer comment;    //评论数
    private String state;       //审核状态
    private String channelid;   //所属频道
    private String url;         //URL
    private String type;        //类型

	//getters and setters
}

```

6. 创建Dao

```java
/**
 * @Description:
 * @Author: yp
 */
public interface ArticleDao extends BaseMapper<Article>{


}
```

### 4.小结

1. mybatis plus: MyBatis封装和扩展
2. 使用步骤
   + 添加坐标
   + 添加配置文件
   + 在配置类/启动类添加dao包扫描
   + 创建pojo 添加注解
   + 创建dao接口继承BaseMapper接口

## 案例-完成文章的CRUD

### 1.需求

- [ ] 完成文章基本的CRUD

### 2.路径

1. 查询所有
2. 根据id查询
3. 新增
4. 更新
5. 删除

### 3.实现

#### 3.1查询所有

![1566723234381](assets/1566723234381.png) 

+ ArticleController

```java
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<Article> list = articleService.findAll();
        return new Result(true, StatusCode.OK, "文章查询成功", list);
    }
}
```

+ ArticleService

```java
@Transactional
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;
    /**
     * 查询所有
     *
     * @return
     */
    public List<Article> findAll() {
        return articleDao.selectList(null);
    }
}
```



#### 3.2根据id查询

![1566723774998](assets/1566723774998.png) 

- ArticleController

```java
    @RequestMapping(value = "/{articleId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("articleId") String articleId) {
        Article article = articleService.findById(articleId);
        return new Result(true, StatusCode.OK, "文章查询成功", article);
    }
```



- ArticleService

```java
    public Article findById(String articleId) {
        return articleDao.selectById(articleId);
    }
```



#### 3.3新增

![1566724131979](assets/1566724131979.png) 

- ArticleController

```java
//新增文章
@RequestMapping(method = RequestMethod.POST)
public Result add(@RequestBody Article article) {
    articleService.add(article);
    return new Result(true, StatusCode.OK, "添加成功");
}
```



- ArticleService

```java
public void add(Article article) {
    //1.新增文章
    String authorId = "1";
    article.setId(idWorker.nextId() + "");
    article.setVisits(0);   //浏览量
    article.setThumbup(0);  //点赞数
    article.setComment(0);  //评论数
    article.setUserid(authorId);

    articleDao.insert(article);
}
```



#### 3.4更新

![1566724393128](assets/1566724393128.png) 

- ArticleController

```java
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable String id, @RequestBody Article article) {
        article.setId(id);
        articleService.update(article);
        return new Result(true, StatusCode.OK, "修改成功");
    }
```



- ArticleService

```java
public void update(Article article) {
    //方式一
    articleDao.updateById(article);
    //方式二
    //EntityWrapper<Article> wrapper = new EntityWrapper<>();
    //wrapper.eq("id",article.getId());
    //articleDao.update(article, wrapper);
}
```



#### 3.5删除

![1566724740686](assets/1566724740686.png) 

工作有两种

1. 逻辑删除  说白了就是update
2. 物理删除  说白了就是delete

> 工作里面一般使用逻辑删除



- ArticleController

```java
@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
public Result delete(@PathVariable String id) {
    articleService.delete(id);
    return new Result(true, StatusCode.OK, "删除成功");
}
```



- ArticleService

```java
public void delete(String id) {
    articleDao.deleteById(id);
}
```



### 4.小结

1. 练习MyBatisPlus



## 案例-条件查询和分页

### 1.需求

- [ ] 完成文章条件,分页查询 

### 2.分析

#### 2.1条件查询

+ 使用Mybatis Plus 提供的EntityWrapper对象封装where查询条件，例如以下使用方式

```java
EntityWrapper wrapper = new EntityWrapper<Article>();
wrapper.eq("id", article.getId());
```

#### 2.2分页查询

使用 Mybatis Plus 提供的分页查询

+  Mybatis Plus提供了分页Page对象进行封装

+ Mybatis Plus提供了PaginationInterceptor插件,需要进行配置  PageHelper 拦截器

```java
@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
```

> PageHelper 还是 MyBatisPlus 分页原理:  1. MyBatis的拦截器  2.ThreadLocal

### 3.实现

![1566726000653](assets/1566726000653.png) 

+ Controller

```java
    /**
     * 文章分页
     * @param map
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result search(@RequestBody Map map, @PathVariable(value = "page") int page, @PathVariable(value = "size")int size) {
       Page<Article> pageList = articleService.search(map,page,size);
       PageResult pageResult = new PageResult(pageList.getTotal(),pageList.getRecords());
        return new Result(true, StatusCode.OK, "分页查询成功",pageResult);
    }
```



+ Service

```java
    /**
     * 分页查询
     * @param map
     * @param page
     * @param size
     * @return
     */
    public Page<Article> search(Map map, int page, int size) {
        //1.封装分页对象
        Page<Article> pageList = new Page<>(page, size);
        //2.封装条件对象
        EntityWrapper<Article> entityWrapper = new EntityWrapper<>();
        if(!StringUtils.isEmpty(map.get("columnid"))){
            entityWrapper.eq("columnid",map.get("columnid"));
        }
        if(!StringUtils.isEmpty(map.get("userid"))){
            entityWrapper.eq("userid",map.get("userid"));
        }
        if(!StringUtils.isEmpty(map.get("title"))){
            entityWrapper.like("title","%"+map.get("title")+"%");
        }
        if(!StringUtils.isEmpty(map.get("content"))){
            entityWrapper.like("content","%"+map.get("content")+"%");
        }

        //3.调用Dao
        List<Article> list = articleDao.selectPage(pageList, entityWrapper);
        pageList.setRecords(list);
        return pageList;
    }
```



### 4.小结

1. 分页使用的MyBatisPlus(类似之前PageHelper)
   + 配置拦截器
   + 使用Page对象(设置查询哪一页, 一页查询多少)
   + 调用dao.findPage()
2. 条件查询

```
EntityWrapper<Article> wrapper = new EntityWrapper<Article>();
```





## 知识点-公共异常和跨域处理

### 1.目标

- [ ] 掌握公共异常处理类的使用

### 2.路径

1. 公共异常处理
2. 跨域处理

### 3.讲解

#### 3.1公共异常处理

​	为了使代码容易维护，减少冗余，我们创建一个类集中处理异常.在com.tensquare.user.controller包下创建公共异常处理类BaseExceptionHandler

```java
@ControllerAdvice
@ResponseBody
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
```

#### 3.2跨域处理

1. 什么是跨域

   浏览器从一个域名的网页去请求另一个域名的资源时，域名、端口、协议任一不同，都是跨域 。

   十次方项目是采用前后端分离开发的，也是前后端分离部署的，必然会存在跨域问题。

2. 如何解决跨域

   只需要在controller类上添加注解`@CrossOrigin `即可！

### 4.小结

1. 公共异常处理器作用: 我们在Controller不需要捕获异常, 统一处理 方便一些 

![1566727700947](assets/1566727700947.png) 