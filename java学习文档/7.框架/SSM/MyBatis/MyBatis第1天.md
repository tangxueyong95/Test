# MyBatis第1天
## MyBatis大纲
### 1 框架概述
#### 1.1 什么是框架
框架（Framework）是整个或部分系统的可重用设计，表现为一组抽象构件及构件实例间交互的方法;另一种定义认为，框架是可被应用开发者定制的应用骨架。前者是从应用方面而后者是从目的方面给出的定义。

简而言之，框架其实就是某种应用的半成品，就是一组组件，供你选用完成你自己的系统。简单说就是使用别人搭好的舞台，你来做表演。而且，框架一般是成熟的，不断升级的软件。

#### 1.2 框架要解决的问题
框架要解决的最重要的一个问题是技术整合的问题，在 J2EE 的 框架中，有着各种各样的技术，不同的软件企业需要从 J2EE 中选择不同的技术，这就使得软件企业最终的应用依赖于这些技术，技术自身的复杂性和技术的风险性将会直接对应用造成冲击。而应用是软件企业的核心，是竞争力的关键所在，因此应该将应用自身的设计和具体的实现技术解耦。这样，软件企业的研发将集中在应用的设计上，而不是具体的技术实现，技术实现是应用的底层支撑，它不应该直接对应用产生影响。

<h5>框架一般处在低层应用平台（如 J2EE）和高层业务逻辑之间的中间层。</h5>

#### 1.3 软件开发的分层重要性
框架的重要性在于它实现了部分功能，并且能够很好的将低层应用平台和高层业务逻辑进行了缓和。为了实现软件工程中的“高内聚、低耦合”。把问题划分开来各个解决，易于控制，易于延展，易于分配资源。 我们常见的 MVC 软件设计思想就是很好的分层思想。

![](https://i.imgur.com/4rQR3ZP.png)

通过分层更好的实现了各个部分的职责，在每一层将再细化出不同的框架，分别解决各层关注的问题。

#### 1.4 分层开发下的常见框架

s(spring)s(strus2)h(hibernate)、s(spring)s(springMVC)m(mybatis)

<h5>常见的JavaEE  开发框架：</h5>
1）解决数据的持久化问题的框架

	MyBatis 本是apache的一个开源项目iBatis, 2010年这个项目由apache software foundation 迁移到了google code，并且改名为MyBatis 。2013年11月迁移到Github。
	
	iBATIS一词来源于“internet”和“abatis”的组合，是一个基于Java的持久层框架。iBATIS提供的持久层框架包括SQL Maps和Data Access Objects（DAOs）
	
	作为持久层的框架，还有一个封装程度更高的框架就是 Hibernate，但这个框架因为各种原因目前在国内的流行程度下降太多，现在公司开发也越来越少使用。 目前使用 Spring Data 来实现数据持久化也是一种趋势

2）解决 WEB层问题的 MVC 框架

	Spring MVC属于SpringFrameWork的后续产品，已经融合在Spring Web Flow里面。
	Spring 框架提供了构建 Web 应用程序的全功能 MVC 模块。
	使用 Spring 可插入的 MVC 架构，从而在使用Spring进行WEB开发时，可以选择使用Spring的SpringMVC框架或集成其他MVC开发框架，如Struts1(现在一般不用)，Struts2等。

3）解决技术整合问题的框架

	Spring是一个开放源代码的设计层面框架，他解决的是业务逻辑层和其他各层的松耦合问题，因此它将面向接口的编程思想贯穿整个系统应用。
	Spring是于2003 年兴起的一个轻量级的Java 开发框架，由Rod Johnson创建。简单来说，Spring是一个分层的JavaSE/EE full-stack(一站式) 轻量级开源框架。
	
	目的：解决企业应用开发的复杂性 
	功能：使用基本的JavaBean代替EJB 
	范围：任何Java应用
	Spring是一个轻量级控制反转(IOC)和面向切面(AOP)的容器框架

#### 1.5 MyBatis 框架概述
mybatis 是一个优秀的基于 java 的持久层框架，它内部封装了 jdbc，使开发者只需要关注 sql 语句本身，而不需要花费精力去处理加载驱动、创建连接、创建 statement 等繁杂的过程。

mybatis 通过 xml或注解的方式将要执行的各种 statement 配置起来，并通过 java 对象和 statement 中 sql的动态参数进行映射生成最终执行的 sql 语句，最后由 mybatis 框架执行 sql 并将结果映射为 java 对象并返回。采用 ORM 思想解决了实体和数据库映射的问题，对 jdbc 进行了封装，屏蔽了 jdbc api 底层访问细节，使我们不用与 jdbc api 打交道，就可以完成对数据库的持久化操作。

为了我们能够更好掌握框架运行的内部过程，并且有更好的体验，下面我们将从自定义 Mybatis 框架开始来学
习框架。此时我们将会体验框架从无到有的过程体验，也能够很好的综合前面阶段所学的基础。

#### 1.6 JDBC 编程的分析
##### 1.6.1 JDBC程序回顾
​	public static void main(String[] args) {
​	    Connection connection = null;
​	    PreparedStatement preparedStatement = null;
​	    ResultSet resultSet = null;
​	    try {
​	        //加载数据库驱动
​	        Class.forName("com.mysql.jdbc.Driver");
​	        //通过驱动管理类获取数据库链接
​	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8", "rot", " root");
​	        //定义 sql 语句 ?表示占位符
​	        String sql = "select * from user where username = ?";
​	        //获取预处理 statement
​	        preparedStatement = connection.prepareStatement(sql);
​	        //设置参数，第一个参数为 sql 语句中参数的序号（从 1 开始），第二个参数为设置的参数值
​	        preparedStatement.setString(1, "王五");
​	        //向数据库发出 sql 执行查询，查询出结果集
​	        resultSet = preparedStatement.executeQuery();
​	        //遍历查询结果集
​	        while (resultSet.next()) {
​	            System.out.println(resultSet.getString("id") + " " + resultSet.getString("username"));
​	        }
​	    } catch (Exception e) {
​	        e.printStackTrace();
​	    } finally {
​	        //释放资源
​	        if (resultSet != null) {
​	            try {
​	                resultSet.close();
​	            } catch (SQLException e) {
​	                e.printStackTrace();
​	            }
​	        }
​	        if (preparedStatement != null) {
​	            try {
​	                preparedStatement.close();
​	            } catch (SQLException e) {
​	                e.printStackTrace();
​	            }
​	        }
​	        if (connection != null) {
​	            try {
​	                connection.close();
​	            } catch (SQLException e) {
​	                e.printStackTrace();
​	            }
​	        }
​	    }
​	}

##### 1.6.2 JDBC缺陷总结

- 数据库链接创建、释放频繁造成系统资源浪费从而影响系统性能，如果使用数据库链接池可解决此问题。
  - mybatis中自带连接池
- Sql 语句在代码中硬编码，造成代码不易维护，实际应用 sql 变化的可能较大， sql 变动需要改变java 代码。
  - mybatis中的SQL语句不是写在代码中，而是写在配置文件中
- 使用 preparedStatement 向占位符号传参数存在硬编码，因为 sql 语句的 where 条件不一定，可能多也可能少，修改 sql 还要修改代码，系统不易维护。
  - mybatis中的根据不同的参数添加SQL语句中不同条件也在配置文件可以完成
- 对结果集解析存在硬编码（查询列名）， sql 变化导致解析代码变化，系统不易维护，如果能将数据库记录封装成 pojo 对象解析比较方便。
  - mybatis中会使用反射将结果集中的数据自动封装到JavaBean对象中

##### 1.6.3 JDBC问题解决分析
![](https://i.imgur.com/CAQ6Swd.png)

通过上面的分析我们已找到了相对应的解决方案，下面我们先学习一下一个框架的解决实现以及通过自己定义一个框架的方式来解决jdbc 编程中所存在的问题。

### 2 MyBatis快速入门
#### 2.1 官网下载 Mybatis 框架
网址 [http://www.mybatis.org/mybatis-3/zh/getting-started.html](http://www.mybatis.org/mybatis-3/zh/getting-started.html "http://www.mybatis.org/mybatis-3/zh/getting-started.html")

![](https://i.imgur.com/yimnIjg.png)

对应依赖包下载 [https://github.com/mybatis/mybatis-3/releases](https://github.com/mybatis/mybatis-3/releases "https://github.com/mybatis/mybatis-3/releases")

下载的 zip 文件如下（ 我们的资料文件夹）：

![](https://i.imgur.com/iaGXKAN.png)

我们所使用的 Mybatis 版本是 3.4.5 版本。

#### 2.2 搭建 Mybatis 开发环境
创建工程之前，我们先新建数据库mybatis,并在数据库中新建一张User表，并加一些数据。表结构入下

	CREATE TABLE `user` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `username` varchar(32) NOT NULL COMMENT '用户名称',
	  `birthday` datetime DEFAULT NULL COMMENT '生日',
	  `sex` char(1) DEFAULT NULL COMMENT '性别',
	  `address` varchar(256) DEFAULT NULL COMMENT '地址',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;



##### 2.2.1 创建Maven工程

- 创建 mybatis-day01-demo1 的工程， 工程信息如下：
- Groupid:com.itheima
- ArtifactId:mybatis-day01-demo1
- Packing:jar

![](https://i.imgur.com/LVQH2wL.png)

##### 2.2.2 pom.xml导入依赖
​	<?xml version="1.0" encoding="UTF-8"?>
​	<project xmlns="http://maven.apache.org/POM/4.0.0"
​	         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
​	         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
​	    <modelVersion>4.0.0</modelVersion>
​	

	    <groupId>com.itheima</groupId>
	    <artifactId>mybatis-day01-demo1</artifactId>
	    <version>1.0-SNAPSHOT</version>
	
	    <!--打包方式-->
	    <packaging>jar</packaging>
	
	    <properties>
	        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
	    </properties>
	
	    <!--引入相关依赖-->
	    <dependencies>
	        <!--MyBatis依赖包-->
	        <dependency>
	            <groupId>org.mybatis</groupId>
	            <artifactId>mybatis</artifactId>
	            <version>3.4.5</version>
	        </dependency>
	
	        <!--MySQL驱动包-->
	        <dependency>
	            <groupId>mysql</groupId>
	            <artifactId>mysql-connector-java</artifactId>
	            <version>5.1.6</version>
	            <scope>runtime</scope>
	        </dependency>
	
	        <!--日志包-->
	        <dependency>
	            <groupId>log4j</groupId>
	            <artifactId>log4j</artifactId>
	            <version>1.2.12</version>
	        </dependency>
	
	        <!--测试包-->
	        <dependency>
	            <groupId>junit</groupId>
	            <artifactId>junit</artifactId>
	            <version>4.12</version>
	            <scope>test</scope>
	        </dependency>
	    </dependencies>
	
	    <build>
	        <resources>
	            <resource>
	                <directory>src/main/java</directory>
	                <includes>
	                    <include>**/*.xml</include>
	                </includes>
	            </resource>
	        </resources>
	    </build>
	</project>

##### 2.2.2 创建User
创建com.itheima.domain包，在该包下创建User对象，并添加对应的属性。

	public class User implements Serializable {
	    private Integer id;
	    private String username;
	    private String birthday;
	    private String sex;
	    private String address;
	
	    //略 get...set...toString...
	}



##### 2.2.3 创建UserMapper接口
创建com.itheima.mapper包，并在该包下创建接口，代码如下：

它其实就是dao层的接口

	public interface UserMapper {
	    List<User> findAll();
	}



##### 2.2.4 创建UserMapper.xml

这个xml配置文件的位置，必须和对应的那个Mapper接口的位置一样。而且其文件名也要和接口名一样

在com.itheima.mapper包下创建UserMapper.xml,并在UserMapper.xml中添加一个select查询结点，代码如下：

	<?xml version="1.0" encoding="UTF-8" ?>
	        <!DOCTYPE mapper
	                PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	                "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.itheima.mapper.UserMapper">
	    <!--findAll-->
	    <select id="findAll" resultType="com.itheima.domain.User">
	        SELECT * FROM  user
	    </select>
	</mapper>

##### 2.2.5 创建SqlMapConfig.xml
在main/resources下创建SqlMapConfig.xml,在文件中配置数据源信息和加载映射文件，代码如下：

这个配置文件的名字不是固定的，你可以随便命名

	<?xml version="1.0" encoding="UTF-8" ?>
	<!DOCTYPE configuration
	        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-config.dtd">
	<configuration>
	    <!--数据源配置-->
	    <environments default="development">
	        <environment id="development">
	            <transactionManager type="JDBC"/>
	            <dataSource type="POOLED">
	                <property name="driver" value="com.mysql.jdbc.Driver"/>
	                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8"/>
	                <property name="username" value="root"/>
	                <property name="password" value="123"/>
	            </dataSource>
	        </environment>
	    </environments>
	
	    <!--加载映射文件-->
	    <mappers>
	        <mapper resource="com/itheima/mapper/UserMapper.xml"/>
	    </mappers>
	</configuration>

##### 2.2.6 创建log4j.properties
为了方便查看日志，在main/resources下创建log4j.properties文件，代码如下：

	log4j.rootLogger=DEBUG,Console
	log4j.appender.Console=org.apache.log4j.ConsoleAppender
	log4j.appender.Console.layout=org.apache.log4j.PatternLayout
	log4j.appender.Console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n
	log4j.logger.org.apache=DEBUG


项目总体结构如下：

![](https://i.imgur.com/Qw22tgu.png)

#### 2.3 编写测试类
在test包下创建com.itheima.test,再在该包下创建MyBatisTest类，代码如下：

	public class MyBatisTest {
	
	    @Test
	    public void testFindAll() throws IOException {
	        //读取配置文件
	        InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");
	
	        //创建SqlSessionFactoryBuilder对象
	        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	
	        //通过SqlSessionBuilder对象构建一个SqlSessionFactory
	        SqlSessionFactory sqlSessionFactory = builder.build(is);
	
	        //通过SqlSessionFactory构建一个SqlSession
	        SqlSession session = sqlSessionFactory.openSession();
	
	        //通过SqlSession实现增删改查
	        UserMapper userMapper = session.getMapper(UserMapper.class);
	        List<User> users = userMapper.findAll();
	
	        //打印输出
	        for (User user : users) {
	            System.out.println(user);
	        }
	        //关闭资源
	        session.close();
	        is.close();
	    }
	}


测试日志：

	[main] DEBUG [com.itheima.mapper.UserMapper.findAll] - ==>  Preparing: SELECT * FROM user 
	[main] DEBUG [com.itheima.mapper.UserMapper.findAll] - ==> Parameters: 
	[main] DEBUG [com.itheima.mapper.UserMapper.findAll] - <==      Total: 11
	User{id=41, username='老王', birthday=Tue Feb 27 17:47:08 CST 2018, sex='男', address='北京'}
	User{id=42, username='张三', birthday=Fri Mar 02 15:09:37 CST 2018, sex='女', address='北京金燕龙'}
	User{id=43, username='小大王', birthday=Sun Mar 04 11:34:34 CST 2018, sex='女', address='北京金燕龙'}
	User{id=45, username='传智播客', birthday=Sun Mar 04 12:04:06 CST 2018, sex='男', address='北京金燕龙'}
	User{id=46, username='王五', birthday=Wed Jul 04 16:43:04 CST 2018, sex='男', address='越南'}



### 3 自定义 Mybatis 框架
本章我们将使用前面所学的基础知识来构建一个属于自己的持久层框架，将会涉及到的一些知识点：工厂模式（Factory 工厂模式）、构造者模式（Builder 模式）、动态代理模式，反射，自定义注解，注解的反射， xml 解析，数据库元数据，元数据的反射等。

#### 3.1 MyBatis框架设计模式分析
我们来看看MyBatis框架使用过程中用到的一些设计模式。

	@Test
	public void testFindAll() throws IOException {
	    //读取配置文件
	    InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");
	
	    //创建SqlSessionFactoryBuilder对象
	    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	
	    /***
	     * 1)构建者模式：
	     *    通过SqlSessionBuilder对象构建一个SqlSessionFactory
	     *    是将一个复杂的对象的构建过程分离出来，隐藏了复杂对象的创建过程，使我们不用关心对象构建的过程。
	     */
	    SqlSessionFactory sqlSessionFactory = builder.build(is);
	
	    /***
	     * 2)工厂模式
	     *     通过SqlSessionFactory构建一个SqlSession
	     *     工厂模式是我们最常用的实例化对象模式了，是用工厂方法代替new操作的一种模式。
	     *     用工厂模式能够降低程序之间的耦合程度，给系统带来更大的可扩展性和尽量少的修改量。
	     */
	    SqlSession session = sqlSessionFactory.openSession();
	
	    /***
	     * 3)代理模式
	     *     通过SqlSession实现增删改查
	     *     构建一个UserMapper接口的代理类，让代理对象完成增删改查。
	     *     在某些情况下，一个对象不适合或者不能直接引用另一个对象，而代理对象可以在客户端和目标对象之间起到中介的作用。
	     *     
	     *     优点
	     *     (1).职责清晰
	     *         真实的角色就是实现实际的业务逻辑，不用关心其他非本职责的事务，通过后期的代理完成一件完成事务，附带的结果就是编程简洁清晰。
	     *     (2).代理对象可以在客户端和目标对象之间起到中介的作用，这样起到了中介的作用和保护了目标对象的作用。
	     *     (3).高扩展性
	     *
	     */
	    UserMapper userMapper = session.getMapper(UserMapper.class);
	    List<User> users = userMapper.findAll();
	
	    //打印输出
	    for (User user : users) {
	        System.out.println(user);
	    }
	    //关闭资源
	    session.close();
	    is.close();
	}

#### 3.2 JDBC流程分析
我们来看看下面这个图片，是JDBC执行流程中的总结，我们要自定义一个框架，可以基于这个流程寻找突破口。

![](https://i.imgur.com/25I1ipb.jpg)

#### 3.3 基于JDBC实现封装流程分析
基于上面我们说到的JDBC流程再结合MyBatis流程，我们封装一个持久城框架，达到MyBatis中的增删改查效果。来分析一波：

![](https://i.imgur.com/qFngqBf.jpg)

#### 3.4 自定义封装
##### 3.4.1 创建工程

- 工程类型：maven
- 打包方式：jar
- groupId：com.itheima
- artifactId：mybatis-day01-demo2-custom
- version：1.0-SNAPSHOT

![](https://i.imgur.com/9kraG7r.png)

##### 3.4.2 pom.xml

	<?xml version="1.0" encoding="UTF-8"?>
	<project xmlns="http://maven.apache.org/POM/4.0.0"
	         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	    <modelVersion>4.0.0</modelVersion>
	
	    <groupId>com.itheima</groupId>
	    <artifactId>mybatis-day01-demo2-custom</artifactId>
	    <version>1.0-SNAPSHOT</version>
	    <!--打包方式jar-->
	    <packaging>jar</packaging>
	
	    <dependencies>
	        <!-- 日志坐标 -->
	        <dependency>
	            <groupId>log4j</groupId>
	            <artifactId>log4j</artifactId>
	            <version>1.2.12</version>
	        </dependency>
	        <!-- mysql驱动 -->
	        <dependency>
	            <groupId>mysql</groupId>
	            <artifactId>mysql-connector-java</artifactId>
	            <version>5.1.36</version>
	        </dependency>
	
	        <!-- 解析xml的dom4j -->
	        <dependency>
	            <groupId>dom4j</groupId>
	            <artifactId>dom4j</artifactId>
	            <version>1.6.1</version>
	        </dependency>
	        <!-- dom4j的依赖包jaxen -->
	        <dependency>
	            <groupId>jaxen</groupId>
	            <artifactId>jaxen</artifactId>
	            <version>1.1.3</version>
	        </dependency>
	
	        <!--c3p0连接池-->
	        <dependency>
	            <groupId>c3p0</groupId>
	            <artifactId>c3p0</artifactId>
	            <version>0.9.1.2</version>
	        </dependency>
	
	        <dependency>
	            <groupId>junit</groupId>
	            <artifactId>junit</artifactId>
	            <version>4.12</version>
	        </dependency>
	    </dependencies>
	
	    <build>
	        <!--IDEA是不会编译src的java目录的xml文件，如果需要读取，则需要手动指定哪些配置文件需要读取-->
	        <resources>
	            <resource>
	                <directory>src/main/java</directory>
	                <includes>
	                    <include>**/*.xml</include>
	                </includes>
	            </resource>
	        </resources>
	    </build>
	</project>

##### 3.4.3 其他准备工作
分别将上一个工程的User.java、UserMapper.java、MyBatisTest.java、UserMapper.xml、log4j.properties、SqlMapConfig.xml都拷贝到该工程中，将XML中引用的DTD文件约束去掉，不然每次解析都会去网上下载。结构如下：

![](https://i.imgur.com/SuLLqTd.png)

SqlMapConfig.xml代码如下：

	<?xml version="1.0" encoding="UTF-8" ?>
	<configuration>
	    <!--数据源配置-->
	    <environments default="development">
	        <environment id="development">
	            <transactionManager type="JDBC"/>
	            <dataSource type="POOLED">
	                <property name="driver" value="com.mysql.jdbc.Driver"/>
	                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8"/>
	                <property name="username" value="root"/>
	                <property name="password" value="123"/>
	            </dataSource>
	        </environment>
	    </environments>
	
	    <!--加载映射文件-->
	    <mappers>
	        <mapper resource="com/itheima/mapper/UserMapper.xml"/>
	    </mappers>
	</configuration>

UserMapper.xml代码如下：

	<?xml version="1.0" encoding="UTF-8" ?>
	<mapper namespace="com.itheima.mapper.UserMapper">
	    <!--findAll-->
	    <select id="findAll" resultType="com.itheima.domain.User">
	        SELECT * FROM  user
	    </select>
	</mapper>

##### 3.4.4 工程错误改造
我们看到上面的工程存在错误，我们对他进行改造一下，首先创建对应的文件来去掉错误。

###### 3.4.4.1 创建Resources类
该类的主要作用是读取类路径下的资源文件，所以要求被它读取的文件务必放到classes下，我们创建它的目的主要是模拟加载读取UserMapper.xml和SqlMapConfig.xml文件。

	public class Resources {
	
	    /***
	     * 读取类路径下的文件
	     * @param path
	     * @return
	     */
	    public static InputStream getResourceAsStream(String path){
	        //读取类路径下的文件，获取文件字节输入流
	        InputStream is = Resources.class.getClassLoader().getResourceAsStream(path);
	        return is;
	    }
	}

此时MyBatisTest中的Resources类就引用上面创建的类就可以去掉一个错误了。



###### 3.4.4.2 创建SqlSessionFactoryBuilder类
创建该类，并创建一个build方法返回一个SqlSessionFactory对象，但SqlSessionFactory还没创建，所以接着我们需要创建它。

	public class SqlSessionFactoryBuilder {
	
	    /***
	     * 读取并解析配置文件，构建一个SqlSessionFactory对象
	     * @param is
	     * @return
	     */
	    public SqlSessionFactory build(InputStream is) {
	        return null;
	    }
	}

###### 3.4.4.3 创建SqlSessionFactory接口及其实现类
创建SqlSessionFactory接口及其实现类，并且创建一个openSession方法。这时候我们发现SqlSession不存在，所以一会儿还要创建一个SqlSession。

	public interface SqlSessionFactory {
	    SqlSession openSession();
	}
	
	public class DefaultSqlSessionFactory implements SqlSessionFactory {
	
	    @Override
	    public SqlSession openSession() {
	        return null;
	    }
	}

###### 3.4.4.4 创建SqlSession接口及其实现类
创建SqlSession接口，并在接口里面创建对应方法，然后创建一个实现类，叫DefaultSqlSession并实现对应方法。

	public interface SqlSession {
	
	    UserMapper getMapper(Class<UserMapper> userMapperClass);
	
	    void close();
	}
	
	public class DefaultSqlSession implements SqlSession {
	
	    @Override
	    public UserMapper getMapper(Class<UserMapper> userMapperClass) {
	        return null;
	    }
	
	    @Override
	    public void close() {
	
	    }
	}


把MyBatisTest类重新导包后，错误就全部消失了。接着我们就要开始对每个模块展开分析和代码实现了。

![](https://i.imgur.com/rssKyQz.png)

##### 3.4.5 获取Connection实现
![](https://i.imgur.com/URAx9QC.png)

我们回到刚才我们的分析，首先我们要解析SqlMapConfig.xml,并把信息存储到Configuration对象中，然后通过Configuration对象获取数据库连接对象Connection。我们可以分这么几个步骤完成：

- 创建XMLConfigBuilder解析SqlMapConfig.xml
- 创建Configuration对象，存储解析的数据库连接信息
- 改造Configuration对象，使它具备获取数据库链接Connection的功能

###### 3.4.5.1 创建XMLConfigBuilder解析SqlMapConfig.xml
public class XMLConfigBuilder {

    /***
     * 解析SqlMapConfig.xml配置文件
     * @param is
     */
    public static void loadConfiguration(InputStream is){
        try {
            //创建SAXReader对象读取XML文件字节输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
    
            //解析配置文件,获取根节点信息,//property表示获取根节点下所有的property结点对象
            List<Element> rootList = document.selectNodes("//property");
    
            //循环迭代所有结点对象
            for (Element element : rootList) {
                //name属性的值
                String name = element.attributeValue("name");
                //vallue属性的值
                String value = element.attributeValue("value");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

###### 3.4.5.2 创建Configuration对象，存储解析的数据库连接信息
####### 3.4.5.2.1 创建Configuration对象
	public class Configuration {
	    //数据库用户名
	    private String username;
	    //数据库用户密码
	    private String password;
	    //数据库连接地址
	    private String url;
	    //数据库驱动
	    private String driver;
	

	    //get..set..toString..
	}

####### 3.4.5.2.2 把解析的数据库连接信息存储到Configuration对象中
注意有2处改动，1）处改动是创建Configuration对象的实例，用于存储数据库配置信息。2）处改动是将数据库配置信息存储到Configuration对象对应的属性中。

	/***
	 * 解析SqlMapConfig.xml配置文件
	 * @param is
	 */
	public static void loadConfiguration(InputStream is){
	    try {
	        //1）数据库配置信息存储
	        Configuration cfg = new Configuration();
	
	        //创建SAXReader对象读取XML文件字节输入流
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(is);
	
	        //解析配置文件,获取根节点信息,//property表示获取根节点下所有的property结点对象
	        List<Element> rootList = document.selectNodes("//property");
	
	        //循环迭代所有结点对象
	        for (Element element : rootList) {
	            //name属性的值
	            String name = element.attributeValue("name");
	            //vallue属性的值
	            String value = element.attributeValue("value");
	
				//2）将解析的数据库连接信息存储到Configuration中
	            //数据库驱动
	            if(name.equals("driver")){
	                cfg.setDriver(value);
	            }else if(name.equals("url")){
	                //数据库连接地址
	                cfg.setUrl(value);
	            }else if(name.equals("username")){
	                //数据库账号
	                cfg.setUsername(value);
	            }else if(name.equals("password")){
	                //数据库密码
	                cfg.setPassword(value);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}



###### 3.4.5.3 改造Configuration对象，使它具备获取数据库链接Connection的功能
在Configuration对象中创建ComboPooledDataSource对象，并创建获得数据源的方法getDataSource，再创建一个获得Connection的方法getConnection，getConnection通过调用getDataSource获得数据源，然后获得Connection对象。

	public class Configuration {
	    //数据库用户名
	    private String username;
	    //数据库用户密码
	    private String password;
	    //数据库连接地址
	    private String url;
	    //数据库驱动
	    private String driver;
	
	    //创建数据源
	    private ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	    //get..set..toString..
	
	    //获取数据源
	    private DataSource getDataSource(){
	        //设置数据源配置
	        try {
	            dataSource.setUser(username);
	            dataSource.setPassword(password);
	            dataSource.setJdbcUrl(url);
	            dataSource.setDriverClass(driver);
	        } catch (PropertyVetoException e) {
	            e.printStackTrace();
	        }
	        return dataSource;
	    }
	
	    /***
	     * 获取数据库连接
	     * @return
	     */
	    public Connection getConnection(){
	        try {
	            return getDataSource().getConnection();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	}

##### 3.4.6 解析UserMapper.xml，提取SQL语句和返回参数类型
![](https://i.imgur.com/YkEMTyO.png)

UserMapper.xml内容：

	<?xml version="1.0" encoding="UTF-8" ?>
	<mapper namespace="com.itheima.mapper.UserMapper">
	    <!--findAll-->
	    <select id="findAll" resultType="com.itheima.domain.User">
	        SELECT * FROM  user
	    </select>
	</mapper>


接着上面流程，我们需要解析UserMapper.xml获取SQL语句，并获取返回值的类型，这个时候我们可以考虑封装一个Mapper对象，存储对应的SQL语句和返回值类型。针对这个操作实现，我们可以分为下面几个步骤实现：

- 解析UserMapper.xml获得SQL语句和返回类型的全限定名
- 创建Mapper对象，存储SQL语句和返回类型的全限定名
- 将解析的结果封装到Mapper中

###### 3.4.6.1 解析UserMapper.xml获得SQL语句和返回类型的全限定名
​	/**
	 * 解析UserMapper.xml，提取SQL语句和返回JavaBean全限定名
	 * path为UserMapper.xml的路径
	 */
	public static void loadMapper(String path){
	    try {
	        //获得文件字节输入流
	        InputStream is = Resources.getResourceAsStream(path);
	

	        //创建SAXReader对象,加载文件字节输入流
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(is);
	
	        //获得根节点
	        Element rootElement = document.getRootElement();
	
	        //获取命名空间的值
	        String namespace = rootElement.attributeValue("namespace");
	
	        //获取所有select结点
	        List<Element> selectList = document.selectNodes("//select");
	
	        //循环所有select结点
	        for (Element element : selectList) {
	            //获取ID属性值
	            String id = element.attributeValue("id");
	
	            //获取resultType属性值
	            String resultType = element.attributeValue("resultType");
	
	            //获取SQL语句
	            String sql = element.getText();
	        }
	    } catch (DocumentException e) {
	        e.printStackTrace();
	    }
	}

###### 3.4.6.2 创建Mapper对象，存储SQL语句和返回类型的全限定名
Mapper对象用于存储SQL语句和返回的JavaBean全限定名，这时候我们可以考虑定义2个属性来接收存储。

	public class Mapper  {
	    
	    //执行的SQL语句
	    private String sql;
	    //执行SQL语句后要返回的JavaBean全限定名
	    private String resultType;
	
		//带参构造函数
		public Mapper(String sql, String resultType) {
	        this.sql = sql;
	        this.resultType = resultType;
	    }
	
	    //get..set..toString
	}

###### 3.4.6.3 将解析的结果封装到Mapper中
我们接着将刚才解析的XML信息存储到Mapper中。我们分析下，目前我们只存在一个select结点，如果以后存在多个怎么操作？如下代码：

	<?xml version="1.0" encoding="UTF-8" ?>
	<mapper namespace="com.itheima.mapper.UserMapper">
	    <!--findAll-->
	    <select id="findAll" resultType="com.itheima.domain.User">
	        SELECT * FROM  user
	    </select>
	
	    <!--findOne-->
	    <select id="findOne" resultType="com.itheima.domain.User">
	        SELECT * FROM  user WHERE id=1
	    </select>
	</mapper>


我们可以把多个select结点信息封装成多个Mapper，并将多个Mapper存储到Map<String,Mapper>中，key可以用id来表示，但这种情况只适合单个UserMapper.xml文件，如果我们再创建一个TeacherMapper.xml解析，同样也有select id="findAll"的话，多个文件解析，key会冲突。解决这种冲突问题，我们可以把namespace也作为key的一部分，只要namespace不冲突就可以保证key不冲突，key=namespace+.+id  即可。

有3处要改，分别为1）、2），代码如下：

	/**
	 * 解析UserMapper.xml，提取SQL语句和返回JavaBean全限定名
	 * path为UserMapper.xml的路径
	 */
	public static void loadMapper(String path){
	    try {
	        /****
	         * 1）定义一个Map<String,Mapper> mappers
	         *    用于存储解析的XML封装的Mapper信息
	         */
	        Map<String,Mapper> mappers = new HashMap<String,Mapper>();
	
	        //获得文件字节输入流
	        InputStream is = Resources.getResourceAsStream(path);
	
	        //创建SAXReader对象,加载文件字节输入流
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(is);
	
	        //获得根节点
	        Element rootElement = document.getRootElement();
	
	        //获取命名空间的值
	        String namespace = rootElement.attributeValue("namespace");
	
	        //获取所有select结点
	        List<Element> selectList = document.selectNodes("//select");
	
	        //循环所有select结点
	        for (Element element : selectList) {
	            //获取ID属性值
	            String id = element.attributeValue("id");
	
	            //获取resultType属性值
	            String resultType = element.attributeValue("resultType");
	
	            //获取SQL语句
	            String sql = element.getText();
	
	            //2）构建Mapper对象
	            Mapper mapper = new Mapper(sql,resultType);
	            //key = namespace+.+id;
	            String key = namespace+"."+id;
	            //存储到Map中
	            mappers.put(key,mapper);
	        }
	        return mappers;
	    } catch (DocumentException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

###### 3.4.6.4 将Mapper存入到Configuration中
按照JDBC操作流程，最终调用SQL语句的是PreparedStatment对象，而PreparedStatment对象由Connection对象构建，所以我们可以把SQL语句给Configuration对象管理。按照这个思路，可以在获取Configuration对象中创建一个Map<String,Mapper>来存储Mapper，可以直接在解析完SqlMapConfig.xml后接着解析UserMapper.xml并将解析的Map返回并填充到Configuration中。另外loadMapper(path)中path其实是SqlMapConfig.xml中的<mapper resource="com/itheima/mapper/UserMapper.xml"/>中指定的XML，可以直接解析它，把它的值传递给loadMapper(path)方法。因此我们这里可以分为3个步骤：

- 改造Configuration，让它能够存储Mapper信息
- 解析mapper，获取UserMapper.xml路径
- 调用loadMapper解析所有XML结点获取Mapper对象并填充给Configuration

###### 3.4.6.4 改造Configuration，让它能够存储Mapper信息
这里主要添加了一个Map<String,Mapper> mappers属性和对应的get、set方法。这里注意为了避免每次set方法调用的时候把之前解析存储的信息替换，所以直接new 了一个Map，set的时候只是往Map中塞数据。

	public class Configuration {
	
	    //数据库用户名
	    private String username;
	    //数据库用户密码
	    private String password;
	    //数据库连接地址
	    private String url;
	    //数据库驱动
	    private String driver;
	
	    //创建数据源
	    private ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	    //存储所有SQL语句和返回值全限定名
	    private Map<String,Mapper> mappers = new HashMap<String,Mapper>();
	
	    public Map<String, Mapper> getMappers() {
	        return mappers;
	    }
	
	    //这里的set方法为了保证每次填充进来的数据不被覆盖，直接调用putAll塞进Map中
	    public void setMappers(Map<String, Mapper> mappers) {
	        this.mappers.putAll(mappers);
	    }
	
	    //...略
	}



###### 3.4.6.5 解析mapper，获取UserMapper.xml路径
由于次解析<mapper>结点个数可能存在多个，所以这里需要用集合接收，并挨个挨个解析，所以上面的Configuration中的Map需要修改set方法改成putAll。

	public static void loadConfiguration(InputStream is){
	    try {
	        //...略
	
	        //循环迭代所有结点对象
	        for (Element element : rootList) {
	            //...略
	        }
	
	        //解析<mapper resource="com/itheima/mapper/UserMapper.xml"/>
	        List<Element> mapperList = document.selectNodes("//mappers//mapper");
	        for (Element element : mapperList) {
	            //获取需要解析的XML路径
	            String resource = element.attributeValue("resource");
	            Map<String, Mapper> mappers = loadMapper(resource);
	
	            //将mappers存入Configuration中
	            cfg.setMappers(mappers);
	        }
	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

##### 3.4.7 增删改查流程串联
###### 3.4.7.1 增删改查思路分析
我们再来看看MyBatis操作的流程，从代码中我们可以看到，通过SqlSession的getMapper方法创建的代理对象是具备查询数据库功能的，也就是说它拥有操作数据库的能力，而操作数据库的能力的前提是能获得连接数据库Connection对象，我们可以基于这个想法，把上面获得的Configuration对象给DefaultSqlSession对象，这样就能通过SqlSession构建一个代理对象，对赋予这个代理对象操作数据库的能力。

	@Test
	public void testFindAll() throws IOException {
	    //读取配置文件
	    InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");
	
	    //创建SqlSessionFactoryBuilder对象
	    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	
	    //通过SqlSessionBuilder对象构建一个SqlSessionFactory
	    SqlSessionFactory sqlSessionFactory = builder.build(is);
	
	    //通过SqlSessionFactory构建一个SqlSession
	    SqlSession session = sqlSessionFactory.openSession();
	    //通过SqlSession实现增删改查
	    UserMapper userMapper = session.getMapper(UserMapper.class);
	    List<User> users = userMapper.findAll();
	    //打印输出
	    for (User user : users) {
	        System.out.println(user);
	    }
	    //关闭资源
	    session.close();
	    is.close();
	}


那么我们会有下面这些疑问：

1）什么时候加载解析配置文件呢？<br/>
答：我们可以在SqlSessionFactory.openSqlSession()的时候，初始化加载上面配置文件。

2）加载配置文件会初始化数据库连接信息，这时候需要加载读取SqlMapConfig.xml配置文件，如何通知程序读取这个配置文件？<br/>
答：早在我们第一步的时候就加载读取了SqlMapConfig.xml文件获取了文件字节输入流，我们可以在构建SqlSessionFactory对象的时候把它传给build方法，如果这时候DefaultSqlSessionFactory中可以接受这个文件字节输入流，那么在openSqlSession()的时候，就可以把这个字节输入流传给XMLConfigBuilder来解析，并获取对应的配置。

3）上面说到让DefaultSqlSession对象具备操作数据库的能力，需要把Configuration对象给DefaultSqlSession对象，如果做到呢？<br/>
答：可以在解析XML对象的时候，直接把DefaultSqlSession的实例传给XMLConfigBuilder.loadConfiguration(DefaultSqlSession session,InputStream is)

###### 3.4.7.2 改造工程
####### 3.4.7.2.1 改造DefaultSqlSession
在DefaultSqlSession中加上Configuration对象，让他具备操作数据库的能力，创建set方法给Configuration赋值，代码如下：

	public class DefaultSqlSession implements SqlSession {
	
	    //把Configuration对象给DefaultSqlSession
	    private Configuration cfg;
	
	    //创建一个set方法，给Configuration赋值
	    public void setCfg(Configuration cfg) {
	        this.cfg = cfg;
	    }
	
	    @Override
	    public UserMapper getMapper(Class<UserMapper> userMapperClass) {
	        return null;
	    }
	
	    @Override
	    public void close() {
	
	    }
	}

####### 3.4.7.2.2 改造DefaultSqlSessionFactory
改造DefaultSqlSessionFactory，加入SqlMapConfig.xml配置文件的字节输入流，并创建DefaultSqlSession对象，加入加载解析配置文件的方法loadConfiguration(sqlSession,is),此时不要忘了改造XMLConfigBuilder.loadConfiguration方法;

	public class DefaultSqlSessionFactory implements SqlSessionFactory {
	
	    //SqlMapConfig.xml的字节输入流
	    private InputStream is;
	
	    public void setIs(InputStream is) {
	        this.is = is;
	    }
	
	    @Override
	    public SqlSession openSession() {
	        //创建一个DefaultSqlSession
	        DefaultSqlSession sqlSession = new DefaultSqlSession();
	
	        //加载解析配置文件
	        XMLConfigBuilder.loadConfiguration(sqlSession,is);
	
	        return sqlSession;
	    }
	}

####### 3.4.7.2.3 改造SqlSessionFactoryBuilder
改造SqlSessionFactoryBuilder的build方法，创建DefaultSqlSessionFactory对象，并将SqlMapConfig.xml的字节输入流传给DefaultSqlSessionFactory。

	public class SqlSessionFactoryBuilder {
	
	    /***
	     * 读取并解析配置文件，构建一个SqlSessionFactory对象
	     * @param is
	     * @return
	     */
	    public SqlSessionFactory build(InputStream is) {
	        //创建一个SqlSessionFactory的实例
	        DefaultSqlSessionFactory sqlSessionFactory =new DefaultSqlSessionFactory();
	        //给SqlSessionFactory的is属性赋值
	        sqlSessionFactory.setIs(is);
	        return sqlSessionFactory;
	    }
	}

####### 3.4.7.2.4 改造XMLConfigBuilder.loadConfiguration方法
主要添加入参DefaultSqlSession，同时需要将解析的结果赋值给该DefaultSqlSession对象。

	/***
	 * 解析SqlMapConfig.xml配置文件
	 * @param is
	 */
	public static void loadConfiguration(DefaultSqlSession sqlSession,InputStream is){
	    try {
	        //略...
	
	        //解析配置文件,获取根节点信息,//property表示获取根节点下所有的property结点对象
	        List<Element> rootList = document.selectNodes("//property");
	
	        //循环迭代所有结点对象
	        for (Element element : rootList) {
	            //略...
	        }
	
	        //解析<mapper resource="com/itheima/mapper/UserMapper.xml"/>
	        List<Element> mapperList = document.selectNodes("//mappers//mapper");
	        for (Element element : mapperList) {
	            //略...
	        }
	
	        //将cfg给DefaultSqlSession
	        sqlSession.setCfg(cfg);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

##### 3.4.7 代理类操作
###### 3.4.7.1 修改SqlSession
把其中getMapper改成通用的方法

	/***
	 * 改造成通用的方法
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	<T> T getMapper(Class<T> clazz);

###### 3.4.7.2 修改DefaultSqlSession
把DefaultSqlSession中的getMapper改成通用的方法

	@Override
	public <T> T getMapper(Class<T> clazz) {
	    return null;
	}

###### 3.4.7.3 修改DefaultSqlSession添加代理实现
​	@Override
​	public <T> T getMapper(Class<T> clazz) {
​	    /*****
	     * 参数：
	     *      1)被代理对象的类加载器
	     *      2)字节数组，让代理对象和被代理对象有相同的行为[行为也就是有相同的方法]
	          *      3)InvocationHandler:增强代码,需要使用提供者增强的代码，改代码是以接口的实现类方式提供的，通常用匿名内部内，但不绝对。
	               */
	            return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
	        @Override
	        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	                return null;
	        }
	            });
	}

###### 3.4.7.4 动态代理实现增删改查
按照JDBC操作流程，我们得先拿到Connection对象，再拿到SQL语句，再执行获取返回结果集，再将返回结果集封装成要的对象即可。因此我们需要DefaultSqlSession，通过它来实现增删改查，实现增删改查就需要获取当前所需的Mapper，Mapper里面包含需要执行的SQL语句和执行SQL语句后返回的结果集需要转换的JavaBean对象。

我们需要用DefaultSqlSession来实现增删改查，可以直接考虑在SqlSession接口中编写增删改查，让DefaultSqlSession完成增删改查的实现，因此我们这里只需要引入SqlSession即可。

按照上面这个分析，我们可以总结为如下几个步骤实现：

- 在SqlSession中编写增删改查，在DefaultSqlSession中实现增删改查
- 在getMapper的代理实现中确定当前操作所需要的Mapper
- 在getMapper的代理实现中引入SqlSession，通过调用对应增删改查实现数据库操作

####### 3.4.7.4.1 在SqlSession中编写增删改查，在DefaultSqlSession中实现增删改查
修改SqlSession，在SqlSession中增加selectList方法

    /***
     * 集合查询
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String statement);

修改DefaultSqlSession，在DefaultSqlSession中实现selectList方法,其中集合查询我们用到了一个Converter转换器，转换器的写法紧接着在后面会列出。

	@Override
	public <E> List<E> selectList(String statement) {
	    //获取对应的Mapper
	    Mapper mapper = cfg.getMappers().get(statement);
	
	    //JDBC操作流程实现
	    if(mapper!=null){
	        //执行查询
	        Connection conn = null;
	        PreparedStatement stm = null;
	        ResultSet resultSet = null;
	        try {
	            //获取Connection对象
	            conn = cfg.getConnection();
	
	            //获取PreparedStatment
	            stm = conn.prepareStatement(mapper.getSql());
	
	            //执行查询
	            resultSet = stm.executeQuery();
	
	            //调用Converter实现转换
	            List<E> list = Converter.list(resultSet,Class.forName(mapper.getResultType()));
	
	            return list;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            try {
	                if(resultSet!=null){
	                    resultSet.close();
	                }
	                if(stm!=null){
	                    stm.close();
	                }
	                //关闭Connection
	                this.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return null;
	}

####### 3.4.7.4.2 Converter转换器编写
Converter转换器主要利用反射机制实现ResultSet转成JavaBean,代码实现如下：

	public class Converter {
	
	    /***
	     * 将ResultSet转换成List集合
	     * @param set
	     * @param clazz
	     * @param <E>
	     * @return
	     */
	    public static <E> List<E> list(ResultSet set,Class clazz){
	        try {
	            //定义一个List集合对象
	            List<E> list = new ArrayList<E>();
	
	            //获取所有属性名
	            Field[] fields = clazz.getDeclaredFields();
	
	            //循环封装
	            while (set.next()){
	                //获取当前Class的实例
	                Object instance = clazz.newInstance();
	
	                //如果属性名和列名一样时，直接可以从ResultSet中根据列名取数据
	                for (Field field : fields) {
	                    //把属性名当数据库表列名去取数据
	                    Object result = set.getObject(field.getName());
	                    //给对应的属性赋值
	                    if(result!=null){
	                        //获取访问属性的权限
	                        field.setAccessible(true);
	                        //给该属性赋值
	                        field.set(instance,result);
	                    }
	                }
	                //将实例加入到集合中
	                list.add((E) instance);
	            }
	
	            return list;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return  null;
	    }
	}

####### 3.4.7.4.3 在getMapper的代理实现中确定当前操作所需要的Mapper
我们定义一个代理实现类，在代理实现类中通过被调用的方法来确定Mapper的key。

	public class MapperProxyFactory implements InvocationHandler {
	
	    @Override
	    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	        //1、获取当前操作锁对应的Mapper信息
	        String className = method.getDeclaringClass().getName();    //类的名字，和UserMapper.xml中mapper的namespace一致
	        String methodName = method.getName();   //方法名字，和UserMapper.xml中的id值一致
	        String key = className+"."+methodName;
	
	        //确定当前操作是否是查询所有
	        Class<?> returnType = method.getReturnType();
	        if(returnType== List.class){
	            return  null;
	        }else{
	            return null;
	        }
	    }
	}

####### 3.4.7.4.4 在getMapper的代理实现中引入SqlSession，通过调用对应增删改查实现数据库操作
其中有2处做了修改，分别为1）处和2）处，因为我们需要用到SqlSession实现查询，所以需要引入当前使用的SqlSession对象。2）处是直接调用SqlSession中定义的selectList方法。

	public class MapperProxyFactory implements InvocationHandler {
		//1）
	    private SqlSession sqlSession;
	    public MapperProxyFactory(SqlSession sqlSession) {
	        this.sqlSession = sqlSession;
	    }
	
	    @Override
	    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	        //1、获取当前操作锁对应的Mapper信息
	        String className = method.getDeclaringClass().getName();    //类的名字，和UserMapper.xml中mapper的namespace一致
	        String methodName = method.getName();   //方法名字，和UserMapper.xml中的id值一致
	        String key = className+"."+methodName;
	
	        //确定当前操作是否是查询所有
	        Class<?> returnType = method.getReturnType();
	        if(returnType== List.class){
	            //2）执行集合查询操作
	            return  sqlSession.selectList(key);
	        }else{
	            return null;
	        }
	    }
	}

DefaultSqlSession中的getMapper方法
	
	@Override
	public <T> T getMapper(Class<T> clazz) {
	    /*****
	     * 参数：
	     *      1)被代理对象的类加载器
	     *      2)字节数组，让代理对象和被代理对象有相同的行为[行为也就是有相同的方法]
	     *      3)InvocationHandler:增强代码,需要使用提供者增强的代码，改代码是以接口的实现类方式提供的，通常用匿名内部内，但不绝对。
	     */
	    return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MapperProxyFactory(this));
	}


此时我们执行查询，结果如下：

	User{id=41, username='老王', birthday=2018-02-27 17:47:08.0, sex='男', address='北京'}
	User{id=42, username='张三', birthday=2018-03-02 15:09:37.0, sex='女', address='北京金燕龙'}
	User{id=43, username='小大王', birthday=2018-03-04 11:34:34.0, sex='女', address='北京金燕龙'}
	User{id=45, username='传智播客', birthday=2018-03-04 12:04:06.0, sex='男', address='北京金燕龙'}
	User{id=46, username='王五', birthday=2018-07-04 16:43:04.0, sex='男', address='越南'}
	User{id=48, username='小马宝莉', birthday=2018-03-08 11:44:00.0, sex='女', address='北京修正'}
	User{id=53, username='小红', birthday=2018-07-08 10:45:46.0, sex='男', address='深圳'}
	User{id=54, username='小黑', birthday=2018-07-08 10:45:46.0, sex='男', address='武汉'}
	User{id=55, username='小牛', birthday=2018-07-08 10:45:46.0, sex='男', address='天津'}
	User{id=56, username='小黄', birthday=2018-07-08 10:45:46.0, sex='男', address='上海'}
	User{id=57, username='小红红红', birthday=2018-07-08 16:57:00.0, sex='女', address='中南安'}



###### 3.4.7.5 执行SQL代码封装Executor
将DefaultSqlSession中selectList的代码封装到一个Executor的工具类中，方便使用。我们新建一个Executor的工具类。

	public class Executor {
	
	    /***
	     * 集合查询
	     * @param conn
	     * @param mapper
	     * @param <E>
	     * @return
	     */
	    public static <E> List<E> list(Connection conn, Mapper mapper) {
	        //执行查询
	        PreparedStatement stm = null;
	        ResultSet resultSet = null;
	        try {
	            //获取PreparedStatment
	            stm = conn.prepareStatement(mapper.getSql());
	
	            //执行查询
	            resultSet = stm.executeQuery();
	
	            //调用Converter实现转换
	            List<E> list = Converter.list(resultSet, Class.forName(mapper.getResultType()));
	
	            return list;
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        } finally {
	            try {
	                if (resultSet != null) {
	                    resultSet.close();
	                }
	                if (stm != null) {
	                    stm.close();
	                }
	                if(conn!=null){
	                    conn.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}


修改DefaultSqlSession中的selectList方法

	@Override
	public <E> List<E> selectList(String statement) {
	    //获取对应的Mapper
	    Mapper mapper = cfg.getMappers().get(statement);
	
	    //JDBC操作流程实现
	    if(mapper!=null){
	        return Executor.list(cfg.getConnection(), mapper);
	    }
	    return null;
	}

##### 3.4.8 自定义MyBatis注解方式实现
上面那种方式我们采用的是基于XML配置来实现数据库操作的，那么我们能不能不用写UserMapper.xml实现数据库的增删改查呢？答案是肯定的。我们之前是把SQL语句和返回的结果集需要转换的类型全限定名存储到UserMapper.xml中，如果我们写一个注解，注解中给一个value属性用来存储SQL语句，所需要的返回值类型直接从方法返回类型中获取是不是可以达到同样效果？

	public interface UserMapper {
	    @Select("select * from user")
	    List<User> findAll();
	}

###### 3.4.8.1 自定义注解
​	@Target(ElementType.METHOD) //作用范围，可以在方法上使用该注解
​	@Retention(RetentionPolicy.RUNTIME) //生命周期
​	public @interface Select {
​	    

	    //定义一个value属性
	    String value();
	}

###### 3.4.8.2 使用自定义注解
定义注解后，直接在UserMapper.java接口上使用，使用方式如下：

	public interface UserMapper {
	    @Select("select * from user")
	    List<User> findAll();
	}


######3.4.8.3 注解解析
我们之前采用的注解方式实现，会在SqlMapConfig.xml中配置需要解析的XML文件，现在我们可以把SqlMapConfig.xml中的配置改一下：

	<mapper class="com.itheima.mapper.UserMapper" />

如果是class表示使用注解方式。

在XMLConfigBuilder中增加一个注解解析方法，代码如下

	/***
	 * 解析注解配置
	 * @param clazz
	 * @return
	 */
	public static Map<String,Mapper> loadAnnotationMapper(Class clazz){
	    //定义Map，存储所有Mapper信息
	    Map<String,Mapper> mappers = new HashMap<String,Mapper>();
	
	    //1、获取所有方法
	    Method[] methods = clazz.getDeclaredMethods();
	
	    //2、迭代所有有@Select注解的方法
	    for (Method method : methods) {
	        //获取注解
	        Select annotation = method.getAnnotation(Select.class);
	
	        if(annotation!=null){
	            //获取方法返回值的泛型
	            Type type = method.getGenericReturnType();
	
	            //判断当前返回数据是否是参数化类型
	            if(type instanceof ParameterizedType){
	                //强转
	                ParameterizedType ptype = (ParameterizedType) type;
	                //获得实际参数类型
	                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
	                //取出第1个
	                Class domainClass = (Class) types[0];
	
	                //返回类型
	                String retunType = domainClass.getName();
	
	                //解析注解，获取SQL语句
	                String sql = annotation.value();
	
	                //key
	                String key = method.getDeclaringClass().getName()+"."+method.getName();
	
	                Mapper mapper = new Mapper(sql,retunType);
	
	                //封装Map<String,Mapper>
	                mappers.put(key,mapper);
	            }
	        }
	
	    }
	    return mappers;
	}


在loadConfiguration方法中调用

	/***
	 * 解析SqlMapConfig.xml配置文件
	 * @param is
	 */
	public static void loadConfiguration(DefaultSqlSession sqlSession,InputStream is){
	    try {
	        //略....
	
	        //解析<mapper resource="com/itheima/mapper/UserMapper.xml"/>
	        List<Element> mapperList = document.selectNodes("//mappers//mapper");
	        for (Element element : mapperList) {
	            //获取resource属性
	            Attribute resourceAttribute = element.attribute("resource");
	
	            //使用配置文件方式
	            if(resourceAttribute!=null){
	                //获取需要解析的XML路径
	                //String resource = element.attributeValue("resource");
	                String resource = resourceAttribute.getValue();
	                Map<String, Mapper> mappers = loadMapper(resource);
	
	                //将mappers存入Configuration中
	                cfg.setMappers(mappers);
	            }else{
	                //使用注解的方式
	                String clazz = element.attributeValue("class");
	                //解析注解
	                Map<String, Mapper> mappers = loadAnnotationMapper(Class.forName(clazz));
	
	                //将mappers存入Configuration中
	                cfg.setMappers(mappers);
	            }
	        }
	
	        //将cfg给DefaultSqlSession
	        sqlSession.setCfg(cfg);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


测试运行结果

	User{id=41, username='老王', birthday=2018-02-27 17:47:08.0, sex='男', address='北京'}
	User{id=42, username='张三', birthday=2018-03-02 15:09:37.0, sex='女', address='北京金燕龙'}
	User{id=43, username='小大王', birthday=2018-03-04 11:34:34.0, sex='女', address='北京金燕龙'}
	User{id=45, username='传智播客', birthday=2018-03-04 12:04:06.0, sex='男', address='北京金燕龙'}
	User{id=46, username='王五', birthday=2018-07-04 16:43:04.0, sex='男', address='越南'}
	User{id=48, username='小马宝莉', birthday=2018-03-08 11:44:00.0, sex='女', address='北京修正'}
	User{id=53, username='小红', birthday=2018-07-08 10:45:46.0, sex='男', address='深圳'}
	User{id=54, username='小黑', birthday=2018-07-08 10:45:46.0, sex='男', address='武汉'}
	User{id=55, username='小牛', birthday=2018-07-08 10:45:46.0, sex='男', address='天津'}
	User{id=56, username='小黄', birthday=2018-07-08 10:45:46.0, sex='男', address='上海'}
	User{id=57, username='小红红红', birthday=2018-07-08 16:57:00.0, sex='女', address='中南安'}