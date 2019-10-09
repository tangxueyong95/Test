# <center>Mybatis 框架课程第三天</center>
##大纲内容
1. MyBatis延迟加载

	1.1)一对一延迟加载
	1.2)一对多延迟加载

2. MyBatis缓存

	2.1）MyBatis一级缓存
	2.2）MyBatis二级缓存

3. MyBatis注解开发

	3.1）MyBatis注解实现增删改查哈
	3.2）MyBatis注解实现数据库列名和属性名不一致映射
	3.3）MyBatis注解实现一对一映射
	3.4）MyBatis注解实现一对多映射

4. 注解缓存实现

	
	重点：重点掌握延迟加载和注解开发的增删改查以及映射关系处理。

### 第1章 Mybatis 延迟加载策略
通过前面的学习，我们已经掌握了 Mybatis 中一对一，一对多，多对多关系的配置及实现，可以实现对象的关联查询。实际开发过程中很多时候我们并不需要总是在加载用户信息时就一定要加载他的账户信息。 此时就是我们所说的延迟加载。

#### 1.1 何为延迟加载?
延迟加载：就是在需要用到数据时才进行加载，不需要用到数据时就不加载数据。延迟加载也称懒加载.

好处： 先从单表查询，需要时再从关联表去关联查询，大大提高数据库性能，因为查询单表要比关联查询多张表速度要快。 。

坏处： 因为只有当需要用到数据时，才会进行数据库查询，这样在大批量数据查询时，因为查询工作也要消耗时间，所以可能造成用户等待时间变长，造成用户体验下降。

前面实现多表操作时，我们使用了 resultMap 来实现一对一，一对多，多对多关系的操作。主要是通过 association、 collection 实现一对一及一对多映射。 association、 collection 具备延迟加载功能。


#### 1.2 实现需求
需求：

查询账户(Account)信息并且关联查询用户(User)信息。如果先查询账户(Account)信息即可满足要求，当我们需要查询用户(User)信息时再查询用户(User)信息。把对用户(User)信息的按需去查询就是延迟加载。
工程目录结构如下：

![](https://i.imgur.com/P8MywZA.png)

##### 1.2.1 使用 Assocation 实现延迟加载
>
需求： 查询账户信息同时查询用户信息。
>
Account 实体类中加入一个 User 类的对象
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

>第一步：只查询账户信息 的 DAO 接口
>
>	SQL:select * from account;
>
AccountMapper 类中添加查询账户信息的方法:
>	
	public interface AccountMapper {
	    /***
	     * 查询账户信息
	     * @return
	     */
	    List<Account> findAccounts();
	}

>
第二步： AccountMapper.xml 映射文件

>其中上面resultMap属性的值accountlayLoadUser，它是我们自定义的resultMap，具体如下：
>
>- select： 填写我们要调用的 select 映射的 id 
>- column： 填写我们要传递给 select 映射的参数
>
		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE mapper
	        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
		<mapper namespace="com.itheima.mapper.AccountMapper">
		    <!--映射配置-->
		    <resultMap id="accountLazyLoadUser" type="Account">
		        <id column="id" property="id"/>
		        <result column="money" property="money"/>
		        <result column="uid" property="uid"/>
		        <!--
		            select：调用指定查询结点
		            column:调用指定查询时，将当前查询出的某列的结果作为参数传递过去
		        -->
		        <association property="user" select="com.itheima.mapper.UserMapper.getUserByUserId" column="uid"></association>
		    </resultMap>
>
		    <!--查询账户信息-->
		    <select id="findAccounts" resultMap="accountLazyLoadUser">
		        SELECT * FROM account
		    </select>
		</mapper>

>第三步：在UserMapper.xml映射文件中添加映射。
>	
	<select id="getUserByUserId" parameterType="int" resultType="User">
	    SELECT * from USER  WHERE  id=#{id}
	</select>

>第四步：开启Mybatis的延迟加载策略
>
>进入Mybaits的官方文档，找到settings的说明信息：
>
![](https://i.imgur.com/8aUcwj3.png)
>
**我们需要在Mybatis的配置文件SqlMapConfig.xml文件中添加延迟加载的配置。**
>	
	<settings>
	    <!--开启延迟加载-->
	    <setting name="lazyLoadingEnabled" value="true"/>
	    <!--当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属性会按需加载-->
	    <setting name="aggressiveLazyLoading" value="false" />
	</settings>
>
第四步：编写测试只查账户信息不查用户信息。
>
	/****
	 * 测试懒加载
	 * 一对一
	 */
	@Test
	public void testFindAccounts(){
	    List<Account> accounts = accountMapper.findAccounts();
	}
>测试结果如下：
>
![](https://i.imgur.com/ZxSOSqT.png)
>
我们发现，因为本次只是将Account对象查询出来放入List集合中，并没有涉及到User对象，所以就没有发出SQL语句查询账户所关联的User对象的查询。
>
第五步：测试加载账户信息同时加载用户信息
>
重新修改测试方法：
>
	/****
	 * 测试懒加载
	 * 一对一
	 */
	@Test
	public void testFindAccounts(){
	    List<Account> accounts = accountMapper.findAccounts();
	    for (Account account : accounts) {
	        System.out.println(account.getUser());
	    }
	}
>
测试效果如下：
>
![](https://i.imgur.com/cFpfaxu.png)
>
小结:
>
通过本示例，我们可以发现Mybatis的延迟加载还要有很明显效果，对于提升软件性能这是一个不错的手段。
>
实现的关键：association的配置
>
	<association property="user" select="com.itheima.mapper.UserMapper.getUserByUserId" column="uid"></association>


##### 1.2.2 使用Collection实现延迟加载
同样我们也可以在一对多关系配置的<collection>结点中配置延迟加载策略。
<collection>结点中也有select属性，column属性。

需求：完成加载用户对象时，查询该用户所拥有的账户信息。
>
第一步：在User实体类中加入List<Account>属性
>
	public class User implements Serializable {
	    private Integer id;           //主键ID
	    private String username;     //用户名
	    private Date birthday;       //用户生日
	    private String sex;          //用户性别
	    private String address;      //用户住址
>	
	    private List<Account> accList;
	    //get...set...toString...
	}
>
>
第二步：UserMapper接口
>
在UserMapper接口中添加查询所有用户信息的方法
>
	public interface UserMapper {
>	
	    /***
	     * 查询用户列表
	     * @return
	     */
	    List<User> findUserList();
	}
>
>
>
第三步：UserMapper.xml 配置文件
>
	<?xml version="1.0" encoding="UTF-8" ?>
	<!DOCTYPE mapper
	        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.itheima.mapper.UserMapper">
	    <!--userLazyLoadAccountListResultMap-->
	    <resultMap id="userLazyLoadAccountListResultMap" type="User">
	        <id column="id" property="id" />
	        <result column="username" property="username" />
	        <result column="sex" property="sex" />
	        <result column="birthday" property="birthday" />
	        <result column="address" property="address" />
	        <!--一对多映射-->
	        <collection property="accList" ofType="Account" column="id" select="com.itheima.mapper.AccountMapper.findAccountsByUid"></collection>
	    </resultMap>
>
	    <select id="getUserByUserId" parameterType="int" resultType="User">
	        SELECT * from USER  WHERE  id=#{id}
	    </select>
>        
	    <!--findUserList-->
	    <select id="findUserList" resultMap="userLazyLoadAccountListResultMap">
	        SELECT * FROM  user
	    </select>
	</mapper>
>
- <collection>标签主要用于加载关联的集合对象
- select属性用于指定查询account列表的sql语句，所以填写的是该sql映射的id
- column属性用于指定select属性的sql语句的参数来源，上面的参数来自于user的id列，所以就写成id这一个字段名了
>
第四步：AccountMapper.xml映射文件加入如下结点
>
UserMapper.xml映射文件中的<collection>标签的select属性的值就是来自这个文件的< select>的id的值.
>
	<!--findAccountsByUid，根据用户ID查询用户账户信息-->
	<select id="findAccountsByUid" parameterType="int" resultType="Account">
	    SELECT * FROM  account WHERE uid=#{uid}
	</select>
>
第五步：开启Mybatis的延迟加载 
>
在Mybatis的配置文件SqlMapConfig.xml中添加延迟加载的配置。
>
	<settings>
	    <!--开启延迟加载-->
	    <setting name="lazyLoadingEnabled" value="true"/>
	    <!--当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属性会按需加载-->
	    <setting name="aggressiveLazyLoading" value="false" />
	</settings>
>
第六步：测试只加载用户信息
>
在测试类中加入测试方法，如下：
>
	/***
	 * 测试一对多懒加载
	 */
	@Test
	public void testFindUserList(){
	    //查询用户列表
	    List<User> users = userMapper.findUserList();
	}
>
测试结果如下：
>
![](https://i.imgur.com/9XG268t.png)
>
我们发现并没有加载Account账户信息。
>
第七步：测试加载用户信息同时还加载账户列表
>	
	/***
	 * 测试一对多懒加载
	 */
	@Test
	public void testFindUserList(){
	    //查询用户列表
	    List<User> users = userMapper.findUserList();
>	
	    for (User user : users) {
	        System.out.println(user.getUsername());
	        System.out.println(user.getAccList());
	    }
	}
>
测试结果如下：
>
![](https://i.imgur.com/GgitINx.png)

### 第2章Mybatis缓存

像大多数的持久化框架一样，Mybatis也提供了缓存策略，通过缓存策略来减少数据库的查询次数，从而提高性能。

Mybatis中缓存分为一级缓存，二级缓存。

![](https://i.imgur.com/7aZfRb6.png)

#### 2.1 Mybatis一级缓存

##### 2.1.1 证明一级缓存的存在
>一级缓存是SqlSession级别的缓存，只要SqlSession没有flush或close，它就存在。
>
>第一步：编写UserMapper接口
>
>public interface UserMapper {
>    User findByUserId(Integer userid);
>}
>
>第二步：编写UserMapper.xml映射文件
>
><?xml version="1.0" encoding="UTF-8" ?>
><!DOCTYPE mapper
>        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
>        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
><mapper namespace="com.itheima.mapper.UserMapper">
>    <!--findByUserId-->
>    <select id="findByUserId" parameterType="int" resultType="User">
>        SELECT * FROM  USER WHERE id=#{id}
>    </select>
></mapper>
>
>第三步：编写测试方法
>
>在测试类中编写测试方法
>
>/***
> * 测试缓存
> */
>@Test
>public void testFindByUserId(){
>    int id=41;
>    User user1 = userMapper.findByUserId(id);
>    User user2 = userMapper.findByUserId(id);
>    System.out.println(user1);
>    System.out.println(user2);
>}
>
>测试结果
>
>![](https://i.imgur.com/hchb66w.png)
>
>我们可以发现，虽然在上面的代码中我们查询了两次，但最后只执行了一次数据库操作，这就是Mybatis提供给我们的一级缓存在起作用了。因为一级缓存的存在，导致第二次查询id为41的记录时，并没有发出sql语句从数据库中查询数据，而是从一级缓存中查询。


##### 2.1.2 一级缓存的分析
一级缓存是SqlSession范围的缓存，当调用SqlSession的修改，添加，删除，commit()，close()等方法时，就会清空一级缓存。

![](https://i.imgur.com/bH0doav.png)

第一次发起查询用户id为1的用户信息，先去找缓存中是否有id为1的用户信息，如果没有，从数据库查询用户信息。
得到用户信息，将用户信息存储到一级缓存中。 
如果sqlSession去执行commit操作（执行插入、更新、删除），清空SqlSession中的一级缓存，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。

第二次发起查询用户id为1的用户信息，先去找缓存中是否有id为1的用户信息，缓存中有，直接从缓存中获取用户信息。

##### 2.1.3 测试一级缓存的清空

什么时候会清空某个SqlSession的一级缓存:

1. 该SqlSession执行增删改
2. 该SqlSession调用commit()方法
3. 该SqlSession调用close()方法
4. 该SqlSession调用clearCache()方法

>修改测试方法，增加清空缓存方法
>
	/***
	 * 测试缓存
	 */
	@Test
	public void testFindByUserId(){
	    int id=41;
	    User user1 = userMapper.findByUserId(id);
	    session.clearCache();
	    User user2 = userMapper.findByUserId(id);
	}
>
当执行sqlSession.clearCache()后，再次获取sqlSession并查询id=41的User对象时，又重新执行了sql 语句，从数据库进行了查询操作。
>
测试结果
>
![](https://i.imgur.com/GddI7QN.png)

#### 2.2 Mybatis二级缓存
>二级缓存是mapper映射级别的缓存，多个SqlSession去操作同一个Mapper映射的sql语句，多个SqlSession可以共用二级缓存，二级缓存是跨SqlSession的。
>
>
##### 2.2.1 二级缓存结构图
![](https://i.imgur.com/RqgF1cq.png)

首先开启mybatis的二级缓存。

sqlSession1去查询用户信息，查询到用户信息会将查询数据存储到二级缓存中。

如果SqlSession3去执行相同 mapper映射下sql，执行commit提交，将会清空该 mapper映射下的二级缓存区域的数据。

sqlSession2去查询与sqlSession1相同的用户信息，首先会去缓存中找是否存在数据，如果存在直接从缓存中取出数据。

###### 2.2.1.1 二级缓存的开启与关闭
>
第一步：在SqlMapConfig.xml文件开启二级缓存
>
	<settings>
	    <!--开启缓存-->
	    <setting name="cacheEnabled" value="true" />
	</settings>
>
因为cacheEnabled的取值默认就为true，所以这一步可以省略不配置。为true代表开启二级缓存；为false代表不开启二级缓存。
>
第二步：配置相关的Mapper映射文件
>
<cache>标签表示当前这个mapper映射将使用二级缓存，区分的标准就看mapper的namespace值。
>
UserMapper.xml配置
>
	<?xml version="1.0" encoding="UTF-8" ?>
	<!DOCTYPE mapper
	        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.itheima.mapper.UserMapper">
	    <!--配置对应的缓存-->
	    <cache />
>
	    //......
>        
	    <!--findByUserId useCache表示是否使用缓存-->
	    <select id="findByUserId" parameterType="int" useCache="true" resultType="User">
	        SELECT * FROM  USER WHERE id=#{id}
	    </select>
	</mapper>
>
将UserMapper.xml映射文件中的< select>标签中设置useCache=”true”代表当前这个statement要使用二级缓存，如果不使用二级缓存可以设置为false。
注意：针对每次查询都需要最新的数据sql，要设置成useCache=false，禁用二级缓存。
>
>
二级缓存测试
>
	/***
	 * 二级缓存测试
	 */
	@Test
	public void testCleanCache(){
	    int id=41;
	    //SqlSession1
	    SqlSession sqlSession1= sqlSessionFactory.openSession();
	    UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
	    User user1 = userMapper1.findByUserId(id);
>
	    //不要忘记关闭SqlSession1
	    sqlSession1.close();
>
	    //SqlSession2
	    SqlSession sqlSession2= sqlSessionFactory.openSession();
	    UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
	    User user2 = userMapper2.findByUserId(id);
	}
>
经过上面的测试，我们发现执行了两次查询，并且在执行第一次查询后，我们关闭了一级缓存，再去执行第二次查询时，我们发现并没有对数据库发出sql语句，所以此时的数据就只能是来自于我们所说的二级缓存。
>
二级缓存注意事项
>
当我们在使用二级缓存时，所缓存的类一定要实现java.io.Serializable接口，这种就可以使用序列化方式来保存对象。
>
如下：
>
	public class User implements Serializable {
	    private Integer id;           //主键ID
	    private String username;     //用户名
	    private Date birthday;       //用户生日
	    private String sex;          //用户性别
	    private String address;      //用户住址
>
	    //get...set...toString...
	
	}


### 第3章Mybatis注解开发
这几年来注解开发越来越流行，Mybatis也可以使用注解开发方式，这样我们就可以减少编写Mapper映射文件了。本次我们先围绕一些基本的 CRUD来学习，再学习复杂映射关系及延迟加载。

工程目录结构如下：

![](https://i.imgur.com/VY1GYWn.png)

#### 3.1 使用Mybatis注解实现基本CRUD
单表的CRUD操作是最基本的操作，前面我们的学习都是基于Mybaits的映射文件来实现的。
#####3.1.1 Mybatis的注解说明
	@Insert:实现新增
	@Update:实现更新
	@Delete:实现删除
	@Select:实现查询
	@Result:实现结果集封装
	@Results:可以与@Result一起使用，封装多个结果集
	@One:实现一对一结果集封装
	@Many:实现一对多结果集封装

我们也通过查看Mybatis官方文档来学习Mybatis注解开发

![](https://i.imgur.com/v3uuW4a.png)

##### 3.1.2 使用注解方式开发UserMapper接口实现增删改查

>在原有的项目中，把UserMapper接口中添加CRUD方法，并带上基本的注解。
>
	public interface UserMapper {
>	
	    /***
	     * 根据ID查询
	     * @param userid
	     * @return
	     */
	    @Select("select * from user where id=#{userid}")
	    User findByUserId(Integer userid);
>        
	    /***
	     * 根据名字模糊查询
	     * @param name
	     * @return
	     * @Select(value = "select * from user where username like#{name}")
	     */
	    @Select(value = "select * from user where username like '%${value}%'")
	    List<User> findUserListByName(String name);
>        
	    /***
	     * 查询所有
	     * @return
	     */
	    @Select(value = "select * from user")
	    List<User> findAll();
>        
	    /***
	     * 根据ID修改用户信息
	     * @param user
	     * @return
	     */
	    @Update(value = "update user set username=#{username},sex=#{sex} where id=#{id}")
	    int updateUserByUserId(User user);
>        
	    /***
	     * 根据ID删除
	     * @param i
	     * @return
	     */
	    @Delete(value = "delete from user where id=#{id}")
	    int deleteByUserId(int i);
>        
	    /****
	     * 查询数据条数
	     * @return
	     */
	    @Select("select count(*) from user")
	    int findCount();
	}
>通过注解方式，我们就不需要再去编写UserMapper.xml 映射文件了。
>
>修改SqlMapConfig 配置文件
>
因为不存在UserMapper.xml文件了，这样我们就不需要在Mybatis配置文件中加载UserMapper.xml映射文件了。此时我们只需要Mybatis 的配置文件能够加载我们的UserMapper接口就可以了。
>
	<!--映射文件指定-->
	<mappers>
	    <package name="com.itheima.mapper" />
	</mappers>
>
编写测试方法
>
	public class MyBatisTest {
>
	    private UserMapper userMapper;
	    private SqlSession session;
	    private SqlSessionFactory sqlSessionFactory;
	    private InputStream is;
>        
	    @Before
	    public void init() throws IOException {
	        //读取配置文件
	        is = Resources.getResourceAsStream("SqlMapConfig.xml");
>        
	        //创建SqlSessionFactoryBuilder对象
	        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	        //通过SqlSessionBuilder对象构建一个SqlSessionFactory
	        sqlSessionFactory = builder.build(is);
>        
	        //通过SqlSessionFactory构建一个SqlSession接口的代理实现类
	        session = sqlSessionFactory.openSession();
>        
	        //通过SqlSession实现增删改查
	        userMapper = session.getMapper(UserMapper.class);
	    }
>        
	    /***
	     * 根据用户名模糊查询
	     */
	    @Test
	    public void testFindUserListByName(){
	        //List<User> users = userMapper.findUserListByName("%王%");
	        List<User> users = userMapper.findUserListByName("王");
	    }
>        
	    /***
	     * 查询所有用户
	     */
	    @Test
	    public void testFindAll(){
	        List<User> users = userMapper.findAll();
	    }
>        
	    /***
	     * 根据ID修改用户
	     */
	    @Test
	    public void testUpdateUserByUserId(){
	        User user = new User();
	        user.setUsername("张三");
	        user.setSex("女");
	        user.setId(42);
	        int mcount = userMapper.updateUserByUserId(user);
	    }
>
>
	    /***
	     * 根据ID删除
	     */
	    @Test
	    public void testDeleteByUserId(){
	        int dcount = userMapper.deleteByUserId(49);
	    }
>
>
	    /*****
	     * 查询总数据条数
	     */
	    @Test
	    public void testFundCount(){
	        int count = userMapper.findCount();
	    }
>        
	    @After
	    public void destroy() throws IOException {
	        //提交事务
	        session.commit();
	        //关闭资源
	        session.close();
	        is.close();
	    }
	}

#### 3.2 使用注解实现数据库表列名和JavaBean属性名不一致

>在现实开发中，经常会有数据库表列名和JavaBean的属性名不一致，针对这种情况，我们可以用@Results注解来解决。
>
	@Results注解
	代替的是标签<resultMap> 
	该注解中可以使用单个@Result注解，也可以使用@Result集合
	@Results（{@Result（），@Result（）}）或@Results（@Result（））
>	
	@Resutl注解
	代替了 <id>标签和<result>标签
	@Result 中 属性介绍：
	column 数据库的列名
	Property需要装配的属性名
	one  需要使用的@One注解（@Result（one=@One）（）））
	many  需要使用的@Many注解（@Result（many=@many）（）））
>
>
在UserMapper接口的getAll方法上加上注解映射即可实现映射转换
>
	@Select(value = "select * from user")
	@Results(id="UserResultMap",value={
	        @Result(id = true,column ="id",property ="userId"),
	        @Result(column ="id",property ="userId"),
	        @Result(column ="userName",property ="userName"),
	        @Result(column ="birthday",property ="userBirthday"),
	        @Result(column ="sex",property ="userSex"),
	        @Result(column ="address",property ="userAddress")
	})
	List<User> findAll();
>
- @Results的id表示唯一标识，其他地方可以直接引用。
- @Result注解的id属性表示主键列，其他的表示非主键列。
- @Result注解中的column和查询的SQL语句中的列名保持一致，代表列名。
- @Result注解中的property和JavaBean中的属性名保持一致。



#### 3.3 使用注解实现复杂关系映射开发
实现复杂关系映射之前我们可以在映射文件中通过配置<resultMap>来实现，但通过后我们发现并没有@ResultMap这个注解。下面我们一起来学习@Results注解，@Result注解，@One注解，@Many注解。

实现后的工程结构如下：

![](https://i.imgur.com/dzwvL8j.png)


复杂关系映射的注解说明
	
	@One注解（一对一）
	代替了<assocation>标签，是多表查询的关键，在注解中用来指定子查询返回单一对象。
	@One注解属性介绍：
	select  指定用来多表查询的sqlmapper
	fetchType会覆盖全局的配置参数lazyLoadingEnabled。。
	使用格式：
	@Result(column=" ",property="",one=@One(select=""))
	
	@Many注解（多对一）
	           代替了<Collection>标签,是是多表查询的关键，在注解中用来指定子查询返回对象集合。
	注意：聚集元素用来处理“一对多”的关系。需要指定映射的Java实体类的属性，属性的javaType（一般为ArrayList）但是注解中可以不定义；
	使用格式：
	@Result(property="",column="",many=@Many(select=""))



##### 3.3.1 使用注解实现一对一复杂关系映射及延迟加载

>
需求：加载账户信息时并且加载该账户的用户信息，根据情况可实现延迟加载。（注解方式实现）
>
添加User实体类及Account实体类
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
>
User实体类
>
	public class User implements Serializable {
	    private Integer id;           //主键ID
	    private String username;     //用户名
	    private Date birthday;       //用户生日
	    private String sex;          //用户性别
	    private String address;      //用户住址
	    //get...set...toString...
	}
>
>
添加UserMapper接口及AccountMapper接口
>
	public interface UserMapper {
	    @Select("select * from user where id=#{userid}")
	    User findByUserId(Integer userid);
	}
>
	public interface AccountMapper {
	    /**
	     * 查询所有账户
	     * 实现一对一查询
	     * @return
	     */
	    @Select(value = "select * from account")
	    @Results({
	            @Result(property ="user" ,column ="uid" ,
	                    one = @One(select = "com.itheima.mapper.UserMapper.findByUserId",
	                            fetchType = FetchType.EAGER))
	    })
	    List<Account> findAll();
	}
>
>
测试一对一关联及延迟加载
>
	/***
	 * 查询所有账户信息
	 * 实现一对一查询
	 */
	@Test
	public void testFindAllAccounts(){
	    List<Account> accounts = accountMapper.findAll();
	    for (Account account : accounts) {
	        System.out.println("-----账户信息----------");
	        System.out.println(account);
	        System.out.println(account.getUser());
	    }
	}
>

##### 3.3.2 使用注解实现一对多复杂关系映射

>需求：查询用户信息时，也要查询他的账户列表。使用注解方式实现。
>
分析：一个用户具有多个账户信息，所以形成了用户(User)与账户(Account)之间的一对多关系。
>
User实体类及Account实体类
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
>
	public class Account {
	    private Integer id;
	    private Integer uid;
	    private Double money;
>
	    //get...set...toStrirng...
	}
>
>
UserMapper接口及AccountMapper接口
>
	//根据uid查询账户信息
	public interface AccountMapper {
>
	    /***
	     * 根据ID查询用户
	     * @param uid
	     * @return
	     */
	    @Select("select * from account where uid=#{uid}")
	    List<Account> findAccountListByUid(Integer uid);
	}
>
>
	//实现查询用户信息时，关联加载他的账户列表，并且要求使用延迟加载
	public interface UserMapper {
>
	    /***
	     * 查询所有
	     * 一对多
	     * @return
	     */
	    @Select(value = "select * from user")
	    @Results(
	            @Result(column = "id",property ="accList",
	                    many = @Many(select = "com.itheima.mapper.AccountMapper.findAccountListByUid",
	                            fetchType = FetchType.LAZY))
	    )
	    List<User> findAll();
	}
>
>
- @Many:相当于<collection>的配置
- select属性：代表将要执行的sql语句
- fetchType属性：代表加载方式，一般如果要延迟加载都设置为LAZY的值
>
添加测试方法
>
	/***
	 * 查询所有用户
	 * 实现一对多查询
	 */
	@Test
	public void testFindAllUsers(){
	    List<User> users = userMapper.findAll();
	    for (User user : users) {
	        System.out.println(user);
	        System.out.println(user.getAccList());
	    }
	}
>

#### 3.4 使用注解实现缓存

>首先我们需要开启缓存，然后加上缓存注解
>
修改SqlMapConfig.xml
>
	<settings>
	    <!--开启缓存-->
	    <setting name="cacheEnabled" value="true" />
	</settings>
>
在对应Mapper接口中加入如下注解即可实现二级缓存
>
	@CacheNamespace(blocking = true)
>


### 第4章Mybatis课程总结
本次课程结束了，通过Mybatis课程的学习，相信大家的水平都得到了一个质的飞跃，通过框架课程的学习， 我们会发现现在的自己变得更强大了。做一个综合案例！