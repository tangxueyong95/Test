# <center>Mybatis 框架课程第二天</center>


### 第1章 Mybatis 连接池与事务深入
##MyBatis大纲
1. MyBatis连接池技术以及事务

	1.1）UnpooledDataSource

	1.2）PooledDataSource
		

2. 动态SQL(重点)
	
	2.1）if使用

	2.2）where使用

	2.3）foreach使用


3. 高级映射(重点)

	3.1)一对一映射

	3.2）一对多映射

	3.3）多对多映射

	
		重点：
			掌握数据库连接池的流程
			
			动态SQL：if、where、foreach使用方法
		
			高级映射：一对一映射(association实现方案)，一对多映射（collection实现方案）

#### 1.1 Mybatis 的连接池技术
我们在前面的 WEB 课程中也学习过类似的连接池技术，而在 Mybatis 中也有连接池技术，但是它采用的是自己的连接池技术。在 Mybatis 的 SqlMapConfig.xml 配置文件中，通过 <dataSourcetype=”pooled”>来实现 Mybatis 中连接池的配置。

##### 1.1.1 Mybatis 连接池的分类
在 Mybatis 中我们将它的数据源 dataSource 分为以下几类：

![](https://i.imgur.com/Sq3AEY2.png)

可以看出 Mybatis 将它自己的数据源分为三类：

- UNPOOLED 不使用连接池的数据源
- POOLED 使用连接池的数据源
- JNDI 使用 JNDI 实现的数据源(知道它的存在就行了)

具体结构如下：

![](https://i.imgur.com/tWWnvhf.png)

相应地，MyBatis内部分别定义了实现了java.sql.DataSource接口的UnpooledDataSource，PooledDataSource类来表示 UNPOOLED、POOLED类型的数据源。

![](https://i.imgur.com/5pQmLQk.png)

- PooledDataSource和UnpooledDataSource都实现了DataSource接口。

- PooledDataSource有一个UnpooledDataSource的引用。

- 当PooledDataSource创建Connection对象时，还是通过UnpooledDataSource来创建。

- PooledDataSource提供了一种缓存连接池机制。

在这三种数据源中，我们一般采用的是 POOLED 数据源（很多时候我们所说的数据源就是为了更好的管理数据库连接，也就是我们所说的连接池技术）。


为了方便，以下案例的SqlMapConfig.xml中都将要添加包扫描

    <!--别名配置-->
    <typeAliases>
        <package name="com.itheima.domain" />
    </typeAliases>
    
    <!--映射文件指定-->
    <mappers>
        <package name="com.itheima.mapper" />
    </mappers>



##### 1.1.2 Mybatis 中数据源的配置
我们的数据源配置就是在 SqlMapConfig.xml 文件中，具体配置如下：

	<!--数据源配置-->
	<environments default="development">
	    <environment id="development">
	        <transactionManager type="JDBC"/>
	        <dataSource type="UNPOOLED">
	            <property name="driver" value="com.mysql.jdbc.Driver"/>
	            <property name="url" value="jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8"/>
	            <property name="username" value="root"/>
	            <property name="password" value="123456"/>
	        </dataSource>
	    </environment>
	</environments>


MyBatis 在初始化时，解析此文件，根据<dataSource>的 type 属性来创建相应类型的的数据源DataSource，即:

- type=”UNPOOLED” ： MyBatis 会创建 UnpooledDataSource 实例
- type=”POOLED”：MyBatis 会创建 PooledDataSource 实例
- type=”JNDI”：MyBatis 会从 JNDI 服务上查找 DataSource 实例，然后返回使用




##### 1.1.3 Mybatis 中 DataSource 的存取
MyBatis是通过工厂模式来创建数据源DataSource对象的,MyBatis定义了抽象的工厂接口 :org.apache.ibatis.datasource.DataSourceFactory,通过其 getDataSource()方法返回数据源DataSource。

下面是 DataSourceFactory 源码，具体如下：

	public interface DataSourceFactory {
	  void setProperties(Properties props);
	  DataSource getDataSource();
	}


MyBatis创建了DataSource实例后，会将其放到Configuration对象内的Environment对象中，供以后使用。

SqlSessionFactoryBuilder.build(inputstream)的时候会初始化创建数据源信息，具体分析过程如下：

1. 先进入 XMLConfigBuilder 类中，可以找到如下代码：

		public Configuration parse() {
			if (parsed) {
		  	throw new BuilderException("Each XMLConfigBuilder can only be used once.");
			}
			parsed = true;
			parseConfiguration(parser.evalNode("/configuration"));
			return configuration;
		}

2. 我们用DEBUG模式执行查询，在XMLConfigBuilder.java的parse()方法断点监控分析configuration对象的environment 属性，结果如下：

![](https://i.imgur.com/U2cVEIE.png)





###### 1.1.3.1 Mybatis 数据源UNPOOLED
SqlMapConfig.xml配置

	<dataSource type="UNPOOLED">

执行查询，并没有发现这句日志记录Returned connection 155361948 to pool 测试日志如下：

	2018-07-05 20:01:47,207 [main] DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Opening JDBC Connection
	2018-07-05 20:01:47,812 [main] DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@272113c4]
	2018-07-05 20:01:47,819 [main] DEBUG [com.itheima.mapper.UserMapper.findAll] - ==>  Preparing: SELECT * FROM USER 
	2018-07-05 20:01:47,951 [main] DEBUG [com.itheima.mapper.UserMapper.findAll] - <==      Total: 6
	User{id=41, username='老王', birthday=Tue Feb 27 17:47:08 CST 2018, sex='男', address='北京'}
	User{id=48, username='小马宝莉', birthday=Thu Mar 08 11:44:00 CST 2018, sex='女', address='北京修正'}
	2018-07-05 20:01:47,956 [main] DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@272113c4]
	2018-07-05 20:01:47,964 [main] DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@272113c4]


>UnpooledDataSource源码分析
>
>源码中的getConnection会调用doGetConnection(String username, String password)方法，该方法中又调用doGetConnection(Properties properties)方法，doGetConnection(Properties properties)方法源码如下：

	  private Connection doGetConnection(Properties properties) throws SQLException {
		//该方法实际上是注册驱动
	    initializeDriver();
	
		//获取连接对象
	    Connection connection = DriverManager.getConnection(url, properties);
	    configureConnection(connection);
	    return connection;
	  }

initializeDriver()方法是用于注册驱动，代码如下：

	  private synchronized void initializeDriver() throws SQLException {
	    if (!registeredDrivers.containsKey(driver)) {
	      Class<?> driverType;
	      try {
	        if (driverClassLoader != null) {
			  //注册驱动
	          driverType = Class.forName(driver, true, driverClassLoader);
	        } else {
	          //....
	        }
	        //....
	      } catch (Exception e) {
	        throw new SQLException("Error setting driver on UnpooledDataSource. Cause: " + e);
	      }
	    }
	  }

###### 1.1.3.2 Mybatis 数据源POOLED
SqlMapConfig.xml配置

	<dataSource type="POOLED">

执行查询，会有一句将Connection回收到连接池日志记录Returned connection 155361948 to pool 测试日志如下：

	2018-07-05 19:55:49,888 [main] DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Opening JDBC Connection
	2018-07-05 19:55:50,466 [main] DEBUG [org.apache.ibatis.datasource.pooled.PooledDataSource] - Created connection 155361948.
	2018-07-05 19:55:50,466 [main] DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@942a29c]
	2018-07-05 19:55:50,469 [main] DEBUG [com.itheima.mapper.UserMapper.findAll] - ==>  Preparing: SELECT * FROM USER 
	2018-07-05 19:55:50,662 [main] DEBUG [com.itheima.mapper.UserMapper.findAll] - <==      Total: 6
	User{id=41, username='老王', birthday=Tue Feb 27 17:47:08 CST 2018, sex='男', address='北京'}
	User{id=42, username='小二王', birthday=Fri Mar 02 15:09:37 CST 2018, sex='女', address='北京金燕龙'}
	2018-07-05 19:55:50,668 [main] DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@942a29c]
	2018-07-05 19:55:50,668 [main] DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@942a29c]
	2018-07-05 19:55:50,668 [main] DEBUG [org.apache.ibatis.datasource.pooled.PooledDataSource] - Returned connection 155361948 to pool.



>PooledDataSource源码分析

>下面是PooledDataSource连接获取的源代码：

	  @Override
	  public Connection getConnection() throws SQLException {
	    return popConnection(dataSource.getUsername(), dataSource.getPassword()).getProxyConnection();
	  }
	
	  @Override
	  public Connection getConnection(String username, String password) throws SQLException {
	    return popConnection(username, password).getProxyConnection();
	  }



>源码中的getConnection会调用popConnection(String username,String password)方法，其中popConnection方法源码如下：


	  private PooledConnection popConnection(String username, String password) throws SQLException {
	    //.....
	
	    while (conn == null) {
	      synchronized (state) {
			//先去idleConnections查找是否有空闲的连接
	        if (!state.idleConnections.isEmpty()) {
	          // Pool has available connection
	          conn = state.idleConnections.remove(0);
	          //...
	        } else {
			  //如果idleConnections没有空闲的连接,查询activeConnections中的连接是否满了
	          // Pool does not have available connection
	          if (state.activeConnections.size() < poolMaximumActiveConnections) {
				//如果没满就创建新的
	            // Can create new connection
	            conn = new PooledConnection(dataSource.getConnection(), this);
	            //...
	          } else {
	            // Cannot create new connection
				//如果activeConnections中连接满了就取出活动连接池的第一个，也就是最早创建的
	            PooledConnection oldestActiveConnection = state.activeConnections.get(0);
	            long longestCheckoutTime = oldestActiveConnection.getCheckoutTime();
	            if (longestCheckoutTime > poolMaximumCheckoutTime) {
	              // Can claim overdue connection
				  //查询最早创建的是否过期，如果过期了就移除他并创建新的
	              //....
	            } else {
	              // 还未过期，就必须等待，再次重复上述步骤
				  //.....
	            }
	          }
	        }
	        if (conn != null) {
	          // .......
	        }
	      }
	
	    }
	    return conn;
	  }


流程如下图：

![](https://i.imgur.com/5VfZcaT.png)


##### 1.1.4 Mybatis 的事务控制

###### 1.1.4.1 JDBC 中事务的回顾
在 JDBC 中我们可以通过手动方式将事务的提交改为手动方式，通过 setAutoCommit()方法就可以调整。

通过 JDK 文档，我们找到该方法如下：

![](https://i.imgur.com/chxQMqP.png)

那么我们的 Mybatis 框架因为是对 JDBC 的封装，所以 Mybatis 框架的事务控制方式，本身也是用 JDBC的 setAutoCommit()方法来设置事务提。

###### 1.1.4.2 Mybatis 中事务提交方式
Mybatis 中事务的提交方式，本质上就是调用 JDBC 的 setAutoCommit()来实现事务控制。

我们运行之前所写的代码：

>测试类
>
    @Test
    public void testSaveUser() {
        User user = new User("张三",new Date(),"男","深圳市");
        //增加有用户
        userDao.saveUser(user);
    }


>UserDaoImpl中的saveUsser()方法需要手动执行sqlSession.commit()提交
>
	public int saveUser(User user) {
	    //获得SqlSession
	    SqlSession sqlSession = sqlSessionFactory.openSession();
	    //执行保存
	    int insert = sqlSession.insert("com.itheima.dao.IUserDao.saveUser",user);
	    //提交
	    sqlSession.commit();
	    //关闭资源
	    sqlSession.close();
	    return insert;
	}
>日志信息
>
	DEBUG [org.apache.ibatis.logging.LogFactory] - Logging initialized using 'class org.apache.ibatis.logging.log4j.Log4jImpl' adapter.
	DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Opening JDBC Connection
	DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@2a556333]
	[com.itheima.mapper.UserMapper.saveUser] - ==>  Preparing: INSERT INTO USER (username,birthday,sex,address)VALUES(?,?,?,?) 
	DEBUG [com.itheima.mapper.UserMapper.saveUser] - ==> Parameters: 张三(String), 2018-07-05 22:29:20.974(Timestamp), 男(String), 深圳市(String)
	DEBUG [com.itheima.mapper.UserMapper.saveUser] - <==    Updates: 1
	DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Committing JDBC Connection [com.mysql.jdbc.JDBC4Connection@2a556333]
	[main] DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@2a556333]
	2018-07-05 22:29:21,471 [main] DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@2a556333]

>这是我们的 Connection 的整个变化过程，通过分析我们能够发现之前的 CUD操作过程中，我们都要手动进行事务的提交，原因是 setAutoCommit()方CUD操作中，必须通过 sqlSession.commit()方法来执行提交操作。



###### 1.1.4.3 Mybatis 自动提交事务的设置
通过上面的研究和分析，现在我们一起思考，为什么 CUD 过程中必须使用 sqlSession.commit()提交事务？主要原因就是在连接池中取出的连接，都会将调用 connection.setAutoCommit(false)方法，这样我们就必须使用 sqlSession.commit()方法，相当于使用了 JDBC 中的 connection.commit()方法实现事务提交。

明白这一点后，我们现在一起尝试不进行手动提交，一样实现 CUD 操作。

>测试类
>
    @Test
    public void testSaveUser() {
        User user = new User("张三",new Date(),"男","深圳市");
        //增加有用户
        userDao.saveUser(user);
    }


>UserDaoImpl中的saveUsser()方法注释手动执行sqlSession.commit()提交
>
	public int saveUser(User user) {
	    //获得SqlSession
	    SqlSession sqlSession = sqlSessionFactory.openSession(true);
	    //执行保存
	    int insert = sqlSession.insert("com.itheima.dao.IUserDao.saveUser",user);
	    //提交
	    //sqlSession.commit();
	    //关闭资源
	    sqlSession.close();
	    return insert;
	}

>
>所对应的 DefaultSqlSessionFactory 类的源代码：
>
	  @Override
	  public SqlSession openSession(boolean autoCommit) {
	    return openSessionFromDataSource(configuration.getDefaultExecutorType(), null, autoCommit);
	  }

>日志信息
>
	DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Opening JDBC Connection
	DEBUG [org.apache.ibatis.datasource.pooled.PooledDataSource] - Created connection 2108753062.
	DEBUG [com.itheima.mapper.UserMapper.saveUser] - ==>  Preparing: INSERT INTO USER (username,birthday,sex,address)VALUES(?,?,?,?) 
	DEBUG [com.itheima.mapper.UserMapper.saveUser] - ==> Parameters: 张三(String), 2018-07-05 23:03:01.121(Timestamp), 男(String), 深圳市(String)
	DEBUG [com.itheima.mapper.UserMapper.saveUser] - <==    Updates: 1
	DEBUG [org.apache.ibatis.transaction.jdbc.JdbcTransaction] - Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@7db12bb6]
	DEBUG [org.apache.ibatis.datasource.pooled.PooledDataSource] - Returned connection 2108753062 to pool.


我们发现，此时事务就设置为自动提交了，同样可以实现 CUD 操作时记录的保存。虽然这也是一种方式，但就编程而言，设置为自动提交方式为 false 再根据情况决定是否进行提交，这种方式更常用。因为我们可以根据业务情况来决定提交是否进行提交。

### 第2章 Mybatis 映射文件的 SQL 深入
Mybatis 的映射文件中，前面我们的 SQL 都是比较简单的，有些时候业务逻辑复杂时，我们的 SQL是动态变化的，此时在前面的学习中我们的 SQL 就不能满足要求了。

参考的官方文档，描述如下：

![](https://i.imgur.com/AQjG5NP.png)

#### 2.1 动态 SQL 之\<if\>标签
我们根据实体类的不同取值，使用不同的 SQL 语句来进行查询。比如在 id 如果不为空时可以根据 id查询，如果 username 不同空时还要加入用户名作为条件。这种情况在我们的多条件组合查询中经常会碰到。

>UserMapper接口中加如下方法
>
	/***
	 * 根据QueryVo查询
	 * @param queryVo
	 * @return
	 */
	List<User> findByVo(QueryVo queryVo);


>UserMapper.xml  代码如下：
>
	<!--findByVo-->
	<select id="findByVo" parameterType="QueryVo" resultType="User">
	    SELECT id,username,birthday,sex,address FROM  USER  WHERE 1=1
	    <!--QueryVo中封装了user属性，而条件要的是user的id属性，注意这种写法-->
	    <if test="user.id!=null">
	        AND id=#{user.id}
	    </if>
	    <!--QueryVo中封装了user属性，而条件要的是user的username属性，注意这种写法-->
	    <if test="user.username!=null and user.username!=''">
	        AND username=#{user.username}
	    </if>
	</select>

>注意：<if>标签的 test 属性中写的是对象的属性名，如果是包装类的对象要使用 OGNL 表达式的写法。
>
>另外要注意 where 1=1 的作用~！
>
>测试代码:
>
	@Test
	public void testFindByVo(){
	    //封装User数据
	    User user = new User();
	    //user.setUsername("老王");
	    user.setId(46);	
	    //封装QueryVo
	    QueryVo queryVo = new QueryVo();
	    queryVo.setUser(user);	
	    /***
	     * 根据QueryVo查询
	     */
	    List<User> users = userMapper.findByVo(queryVo);
	    for (User u : users) {
	        System.out.println(u);
	    }
	}


#### 2.2 动态 SQL 之\<where\>标签
为了简化上面 where 1=1 的条件拼装，我们可以采用<where>标签来简化开发。

修改 UserMapper.xml 映射文件如下：

	<!--findByVo-->
	<select id="findByVo" parameterType="QueryVo" resultType="User">
	    SELECT id,username,birthday,sex,address FROM  USER 
	    <where>
	        <!--QueryVo中封装了user属性，而条件要的是user的id属性，注意这种写法-->
	        <if test="user.id!=null">
	            AND id=#{user.id}
	        </if>
	        <!--QueryVo中封装了user属性，而条件要的是user的username属性，注意这种写法-->
	        <if test="user.username!=null and user.username!=''">
	            AND username=#{user.username}
	        </if>
	    </where>
	</select>

<where />可以自动处理第一个 and。

#### 2.3 动态标签之\<foreach\>标签



- 需求

		传入多个 id 查询用户信息，用下边两个 sql 实现：
		SELECT * FROM USERS WHERE username LIKE '%张%' AND (id =10 OR id =89 OR id=16)
		SELECT * FROM USERS WHERE username LIKE '%张%' AND id IN (10,89,16)

这样我们在进行范围查询时，就要将一个集合中的值，作为参数动态添加进来。这样我们将如何进行参数的传递？

##### 2.3.1 在 QueryVo 中加入一个 List 集合用于封装参数
>在UserMapper接口中加入如下方法
>
	/**
	 * 根据ID集合查询
	 * @param queryVo
	 * @return
	 */
	List<User> findByRang(QueryVo queryVo);


>在UserMapper.xml中加入findByRang方法
>
	<!--findByRang-->
	<select id="findByRang" parameterType="QueryVo" resultType="User">
	    SELECT id,username,birthday,sex,address FROM  USER
	    <where>
	        <if test="ids!=null and ids.size>0">
	            <foreach collection="ids" item="id" close=")" open="AND id IN (" separator=",">
	                #{id}
	            </foreach>
	        </if>
	    </where>
	</select>

>SQL 语句： select 字段 from user where id in (?)

>< foreach>标签用于遍历集合，它的属性：
>
- collection:代表要遍历的集合元素，注意编写时不要写#{}
- open:代表语句的开始部分
- close:代表结束部分
- item:代表遍历集合的每个元素，生成的变量名
- sperator:代表分隔符

>在测试中增加测试方法
>
	/***
	 * 根据findByRang查询
	 */
	@Test
	public void testFindByRang(){
	    //封装QueryVo
	    QueryVo queryVo = new QueryVo();
	    List<Integer> ids = new ArrayList<Integer>();
	    ids.add(41);
	    ids.add(45);
	    queryVo.setIds(ids);
	    /***
	     * 根据findByRang查询
	     */
	    List<User> users = userMapper.findByRang(queryVo);
	    for (User u : users) {
	        System.out.println(u);
	    }
	}


#### 2.4 Mybatis 中简化编写的 SQL 片段
Sql 中可将重复的 sql 提取出来，使用时用 include 引用即可，最终达到 sql 重用的目的。<br/>
我们先到 UserMapper.xml 文件中使用<sql>标签，定义出公共部分，如下：

	<!--定义SQL片段-->
	<sql id="selectUserTable">
	    SELECT id,username,birthday,sex,address FROM  USER
	</sql>

然后在 UserMapper.xml 文件中用<include>标签再引用上面的 id。

	<!--findByRang-->
	<select id="findByRang" parameterType="QueryVo" resultType="User">
	    <include refid="selectUserTable" />
	    <where>
	        <if test="ids!=null and ids.size>0">
	            <foreach collection="ids" item="id" close=")" open="AND id IN (" separator=",">
	                #{id}
	            </foreach>
	        </if>
	    </where>
	</select>

其中<include>标签的 refid 属性的值就是<sql> 标签定义 id 的取值。<br/>
注意：如果引用其它 mapper.xml 的 sql 片段，则在引用时需要加上 namespace，如下：

	<include refid="namespace.sql 片段"/>


### 第3章 Mybatis 的多表关联查询(难点)
本次案例主要以最为简单的用户和账户的模型来分析 Mybatis 多表关系。用户为 User 表，账户为Account 表。一个用户（User）可以有多个账户（Account）。具体关系如下：

![](https://i.imgur.com/BlQ6GKL.png)

#### 3.1 一对一查询
案例：查询所有账户信息，关联查询下单用户信息。

注意：因为一个账户信息只能供某个用户使用，所以从查询账户信息出发关联查询用户信息为一对一查询。如果从用户信息出发查询用户下的账户信息则为一对多查询，因为一个用户可以有多个账户。

##### 3.1.1 方式一
>定义账户信息的 PO 类
>
>使用 resultType，定义账户信息 po 类，此 po 类中包括了账户信息。具体实现如下：
>
	public class Account {
	    private Integer id;
	    private Integer uid;
	    private Double money;
>
	    //get...set...toString...
	}


>编写 Sql语句
>
	SELECT 
	 account.*,
	 user.username,
	 user.address
	FROM
	 account,user
	WHERE account.uid = user.id
在 MySQL 中测试的查询结果如下：

>![](https://i.imgur.com/V7vwU7y.png)
>
>定义AccountCustomer 类
为了能够封装上面 SQL 语句的查询结果，定义 AccountCustomer 类中要包含账户信息同时还要包含用户信息，所以我们要在定义 AccountCustomer 类时可以继承 User 类。
>
	public class AccountCustomer extends Account {
	    private String username;
	    private String address;
>		
	    //get...set...toString...
	}
>
>定义 AccountMapper 接口
>
定义 AccountMapper 接口，用于查询 account 相关的账户信息。为了查询账户信息同时还能关联查询出他的用户信息，所以我们的返回结果采用的是 AccountCustomer 类型
>
	public interface AccountMapper {
	    /***
	     * 查询账户信息，同时还要展示用户信息
	     * @return
	     */
	    List<AccountCustomer> findAccountList();
	}

>定义 AccountMapper.xml 文件中的查询配置信息
>
	<?xml version="1.0" encoding="UTF-8" ?>
	<!DOCTYPE mapper
	        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.itheima.mapper.AccountMapper">
	    <!--findAccountList-->
	    <select id="findAccountList" resultType="AccountCustomer">
	        SELECT
	         account.*,
	         user.username,
	         user.address
	        FROM
	         account,user
	        WHERE account.uid = user.id
	    </select>
	</mapper>
>
>注意：因为上面查询的结果中包含了账户信息同时还包含了用户信息，所以我们的返回值类型returnType 的值设置为 accountCustomer 类型，这样就可以接收账户信息和用户信息了。
>
>创建 AccountTest 测试类
>
	/***
	 * findAccountList
	 */
	@Test
	public void testFindAccountList(){
	    List<AccountCustomer> customers = accountMapper.findAccountList();
	    for (AccountCustomer customer : customers) {
	        System.out.println(customer);
	    }
	}


>小结
>
>定义专门的 po 类作为输出类型，其中定义了 sql 查询结果集所有的字段。此方法较为简单，企
业中使用普遍。


##### 3.1.2 方式二
使用 resultMap，定义专门的 resultMap 用于映射一对一查询结果。

通过面向对象的(has a)关系可以得知，我们可以在 Account 类中加入一个 User 类的对象来代表这个
账户是哪个用户的。


###### 3.1.2.1 修改 Account
>在 Account 类中加入 User类的对象作为 Account 类的一个属性。
>
	public class Account {
	    private Integer id;
	    private Integer uid;
	    private Double money;
>
	    //加上一个用户对象，has a的关系
	    private User user;
	    //get...set...toStrirng...
	}

>修改 AccountMapper 接口中的方法，注释掉方式一
>
	public interface AccountMapper {
	    /***
	     * 方式：二
	     * 直接用Account方式来封装
	     * @return
	     */
	    List<Account> findAccountList();
	    /***
	     * 方式：一
	     * 查询账户信息，同时还要展示用户信息
	     * @return
	     */
	    //List<AccountCustomer> findAccountList();
	}
注意：第二种方式，将返回值改 为了 Account 类型。因为 Account 类中包含了一个 User 类的对象，它可以封装账户所对应的用户信息。
>
>重写 Account 类的 toString()方法
>
	@Override
	public String toString() {
	    return "Account{" +
	            "id=" + id +
	            ", uid=" + uid +
	            ", money=" + money +
	            ", user=" + user +
	            '}';
	}
>重新定义 AccountMapper.xml 文件
>
	<?xml version="1.0" encoding="UTF-8" ?>
	<!DOCTYPE mapper
	        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.itheima.mapper.AccountMapper">
	    <!--resultMap-->
	    <resultMap id="accountMap" type="Account">
	        <id column="id" property="id" />
	        <result column="money" property="money" />
	        <result column="uid" property="uid" />
	        <!--
	            一对一映射关系配置
	            association:用于加载关联的对象，property代表要加载的对象属性
	            javaType:表示要加载的属性对应的Java数据类型
	         -->
	        <association property="user" javaType="User">
	            <result column="username" property="username" />
	            <result column="address" property="address" />
	        </association>
	    </resultMap>
>
	    <!--
	        findAccountList
	        resultType换成resultMap，值为上面的accountMap
	     -->
	    <select id="findAccountList" resultMap="accountMap">
	        SELECT
	         account.*,
	         user.username,
	         user.address
	        FROM
	         account,user
	        WHERE account.uid = user.id
	    </select>
	</mapper>
>
测试类修改
>
	@Test
	public void testFindAccountList(){
	    //List<AccountCustomer> customers = accountMapper.findAccountList();
	    List<Account> accounts = accountMapper.findAccountList();
	    for (Account account : accounts) {
	        System.out.println(account);
	    }
	}
测试结果:
>![](https://i.imgur.com/fsg2ril.png)


#### 3.2 一对多查询

需求：查询所有用户信息及用户关联的账户信息。

分析：用户信息和他的账户信息为一对多关系，并且查询过程中如果用户没有账户信息，此时也要
将用户信息查询出来，我们想到了左外连接查询比较合适。

>编写 SQL 语句
>
	SELECT u.*, acc.id id,acc.uid, acc.money FROM user u LEFT JOIN account acc ON u.id = acc.uid
>
测试该 SQL 语句在 MySQL 客户端工具的查询结果如下：
>
![](https://i.imgur.com/ZVG00Wj.png)
>
改写 User 类加入 List< Account>
>
>为了能够让查询的 User 信息中，带有他的个人多个账户信息，我们就需要在 User 类中添加一个集合，
用于存放他的多个账户信息，这样他们之间的关联关系就保存了
>
	public class User implements Serializable {
	    private Integer id;           //主键ID
	    private String username;     //用户名
	    private Date birthday;       //用户生日
	    private String sex;          //用户性别
	    private String address;      //用户住址
>	
	    //用于保存用户多个账号信息
	    private List<Account> accList;
	    //get...set...toString...
	}

>UserMapper 接口中加入查询方法
>
>在 UserMapper 接口中加入查询方法：List<User> findUserAccountList();
>
	public interface UserMapper {
	    /***
	     * 查询用户信息，包含账号集合信息
	     * @return
	     */
	    List<User> findUserAccountList();
	}
>
修改 UserMapper.xml 映射文件
>
	<?xml version="1.0" encoding="UTF-8" ?>
	<!DOCTYPE mapper
	        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.itheima.mapper.UserMapper">
	    <!--userAccountsResultMap-->
	    <resultMap id="userAccountsResultMap" type="User">
	        <id column="id" property="id" />
	        <result column="username" property="username" />
	        <result column="sex" property="sex" />
	        <result column="birthday" property="birthday" />
	        <result column="address" property="address" />
	        <!--一对多映射-->
	        <collection property="accList" ofType="Account">
	            <id column="aid" property="id" />
	            <result column="auid" property="uid" />
	            <result column="amoney" property="money" />
	        </collection>
	    </resultMap>
	    <!--
	        findUserAccountList
	        为了区分两张表数据，account的列我们分别取个别名加个字母a
	     -->
	    <select id="findUserAccountList" resultMap="userAccountsResultMap">
	        SELECT u.*, acc.id aid,acc.uid auid, acc.money amoney FROM user u LEFT JOIN account acc ON u.id = acc.uid
	    </select>
	</mapper>
>
>
- collection 部分定义了用户关联的账户信息。表示关联查询结果集
- property="accList"：关联查询的结果集存储在 User 对象的上哪个属性。
- ofType="account"：指定关联查询的结果集中的对象类型即 List 中的对象类型。此处可以使用别名，也可以使用全限定名。
- <id />及<result/>的意义同一对一查询.
>
测试方法
>
	/***
	 * 查询所有用户信息，包含账号信息
	 */
	@Test
	public void testFindUserAccountList(){
	    List<User> users = userMapper.findUserAccountList();
	    for (User user : users) {
	        System.out.println(user);
	    }
	}
>
注意重写了 User 类和 Account 类的 toString()方法。
>
User 类的 toString()方法
>
	@Override
	public String toString() {
	    return "User{" +
	            "id=" + id +
	            ", username='" + username + '\'' +
	            ", birthday=" + birthday +
	            ", sex='" + sex + '\'' +
	            ", address='" + address + '\'' +
	            ", accList=" + accList +
	            '}';
	}
>
Account 类的 toString()方法
>
	@Override
	public String toString() {
	    return "Account{" +
	            "id=" + id +
	            ", uid=" + uid +
	            ", money=" + money +
	            '}';
	}
>
测试结果
>
	User{id=41, username='老王', birthday=Tue Feb 27 17:47:08 CST 2018, sex='男', address='北京', accList=[Account{id=1, uid=41, money=1000.0}, Account{id=3, uid=41, money=2000.0}]}
	User{id=45, username='传智播客', birthday=Sun Mar 04 12:04:06 CST 2018, sex='男', address='北京金燕龙', accList=[Account{id=2, uid=45, money=1000.0}]}
	User{id=42, username='小二王', birthday=Fri Mar 02 15:09:37 CST 2018, sex='女', address='北京金燕龙', accList=[]}




#### 3.3 Mybatis 维护多对多关系
##### 3.3.1 实现 Role 到 User 多对多
通过前面的学习，我们使用 Mybatis 实现一对多关系的维护。多对多关系其实我们看成是双向的一对多关系。工程结构如下：

![](https://i.imgur.com/FXAnmRs.png)

###### 3.3.1.1 用户与角色的关系模型
用户与角色的多对多关系模型如下：

![](https://i.imgur.com/pLAHRtP.png)

在 MySQL 数据库中添加角色表，用户角色的中间表。

角色表

![](https://i.imgur.com/ZdoUnkw.png)

用户角色中

![](https://i.imgur.com/iz2iAMs.png)

###### 3.3.1.2 业务要求及实现 SQL
需求：实现查询所有对象并且加载它所分配的用户信息。

分析：查询角色我们需要用到 Role 表，但角色分配的用户的信息我们并不能直接找到用户信息，而是要通过中间表(USER_ROLE 表)才能关联到用户信息。

下面是实现的 SQL 语句：

	SELECT
	 r.*,u.id uid,
	 u.username username,
	 u.birthday birthday,
	 u.sex sex,
	 u.address address
	FROM 
	 ROLE r
	INNER JOIN  USER_ROLE ur
	ON ( r.id = ur.rid)
	INNER JOIN
	 USER u
	ON (ur.uid = u.id);


###### 3.3.1.3 代码实现
我们加载的信息中不仅有角色信息，同时还要加载关联加载具有该角色的用户信息。因为一个角色可以分配给多个用户，所以我们可以考虑在 Role 类中加入一个 List<User>的属性，用于存放这个角色分配给了哪些用户对象。
>
具体的角色实体类（Role 类）如下：
>
	public class Role {
	    private Integer id;
	    private String roleName;
	    private String roleDesc;
>
	    //一个角色可以分配给多个人
	    private List<User> userList;
	    //get...set...toString...
	}
其中 Role 类中 userList 集合就是用于存在该角色分配给的用户列表。
>
编写 RoleMapper 接口
在 RoleMapper 接口中添加一个用于查询所有角色并关联查询出它所分配的用户列表的方法。
>
	public interface RoleMapper {
	    /***
	     * 查询所有角色信息，包含User信息
	     * @return
	     */
	    List<Role> findRoleUserList();
	}

>编写 RoleMapper.xml 映射文件
>
>在 RoleMapper.xml 文件中添加一个 resultMap 用于加载角色对象及它所关联的用户信息。然后在 RoleMapper.xml 文件中加入 SQL 语句的映射
>
	<?xml version="1.0" encoding="UTF-8" ?>
	<!DOCTYPE mapper
	        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.itheima.mapper.RoleMapper">
	    <!--roleUserResultMap-->
	    <resultMap id="roleUserResultMap" type="Role">
	        <id column="id" property="id" />
	        <result column="role_name" property="roleName" />
	        <result column="role_desc" property="roleDesc" />
>
	        <!--用户属性填充-->
	        <collection property="userList" ofType="User">
	            <id column="uid" property="id" />
	            <result column="username" property="username" />
	            <result column="sex" property="sex" />
	            <result column="birthday" property="birthday" />
	            <result column="address" property="address" />
	        </collection>
	    </resultMap>
	    <!--
	        findRoleUserList
	        因为有2个id，为了区分，user表的id取别名uid
	     -->
	    <select id="findRoleUserList" resultMap="roleUserResultMap">
	        SELECT
	         r.*,u.id uid,
	         u.username username,
	         u.birthday birthday,
	         u.sex sex,
	         u.address address
	        FROM
	         ROLE r
	        INNER JOIN  USER_ROLE ur
	        ON ( r.id = ur.rid)
	        INNER JOIN
	         USER u
	        ON (ur.uid = u.id);
	    </select>
	</mapper>
>
编写测试类 RoleTest
>
>添加 RoleTest 类，并加入测试方法，测试方法如下：
>
	/***
	 * 查询所有角色信息，包含用户信息
	 */
	@Test
	public void testFindUserAccountList(){
	    List<Role> roles = roleMapper.findRoleUserList();
	    for (Role role : roles) {
	        System.out.println(role);
	    }
	}


##### 3.3.2 实现 User 到 Role 的多对多
从 User 出发，我们也可以发现一个用户可以具有多个角色，这样用户到角色的关系也还是一对多关系。这样我们就可以认为 User 与 Role 的多对多关系，可以被拆解成两个一对多关系来实现。

###### 3.3.2.1 作业：实现 User 到 Role 的一对多查询
需求：实现查询所有用户信息并关联查询出每个用户的角色列表。

### 总结
本次课程主要是不断深入学习 Mybatis 的一些知识点，比如我们通过学习能够更好的掌握 Mybatis连接池技术、事务、复杂参数的传入、复杂结果集的封装，多表的关联查询。



### 知识点补充：JNDI数据源配置
创建一个web工程[war包],pom.xml如下

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
	        <version>4.10</version>
	        <scope>test</scope>
	    </dependency>
	
	    <!--servlet-->
	    <dependency>
	        <groupId>javax.servlet</groupId>
	        <artifactId>servlet-api</artifactId>
	        <version>2.5</version>
	        <scope>provided</scope>
	    </dependency>
	
	    <!--jsp-->
	    <dependency>
	        <groupId>javax.servlet.jsp</groupId>
	        <artifactId>jsp-api</artifactId>
	        <version>2.0</version>
	        <scope>provided</scope>
	    </dependency>
	</dependencies>

在webapp目录下创建META-INF/context.xml

	<?xml version="1.0" encoding="UTF-8"?>
	<Context>
	    <!--
	    <Resource
	    name="jdbc/eesy_mybatis"                  数据源的名称
	    type="javax.sql.DataSource"                   数据源类型
	    auth="Container"                        数据源提供者
	    maxActive="20"                         最大活动数
	    maxWait="10000"                            最大等待时间
	    maxIdle="5"                               最大空闲数
	    username="root"                            用户名
	    password="1234"                            密码
	    driverClassName="com.mysql.jdbc.Driver"          驱动类
	    url="jdbc:mysql://localhost:3306/eesy_mybatis" 连接url字符串
	    />
	     -->
	    <Resource
	            name="jdbc/web_jndi"
	            type="javax.sql.DataSource"
	            auth="Container"
	            maxActive="20"
	            maxWait="10000"
	            maxIdle="5"
	            username="root"
	            password="123456"
	            driverClassName="com.mysql.jdbc.Driver"
	            url="jdbc:mysql://localhost:3306/mybatis?useUnicode=true&amp;characterEncoding=utf8"
	    />
	</Context>


SqlMapConfig.xml配置

	<!--数据源配置-->
	<environments default="development">
	    <environment id="development">
	        <transactionManager type="JDBC"/>
	        <dataSource type="JNDI">
	            <property name="data_source" value="java:comp/env/jdbc/web_jndi" />
	        </dataSource>
	    </environment>
	</environments>


新建JNDIServlet

	private RoleMapper roleMapper;
	private SqlSession session;
	private InputStream is;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //读取配置文件
	    is = Resources.getResourceAsStream("SqlMapConfig.xml");
	
	    //创建SqlSessionFactoryBuilder对象
	    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	    //通过SqlSessionBuilder对象构建一个SqlSessionFactory
	    SqlSessionFactory sqlSessionFactory = builder.build(is);
	
	    //通过SqlSessionFactory构建一个SqlSession接口的代理实现类
	    session = sqlSessionFactory.openSession(true);
	
	    //通过SqlSession实现增删改查
	    roleMapper = session.getMapper(RoleMapper.class);
	
	    List<Role> roles = roleMapper.findRoleUserList();
	    for (Role role : roles) {
	        System.out.println(role);
	    }
	
	    //关闭资源
	    session.close();
	    is.close();
	}


上网账号密码:

账号  javaee55@sz.itcast.cn

密码  ds7cjxds