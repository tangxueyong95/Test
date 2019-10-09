---
typora-copy-images-to: assets
---

# day04-消息通知系统

# 学习目标

- [ ] 了解消息通知系统的业务场景
- [ ] 了解消息通知和即时通讯区别
- [ ] 实现消息通知微服务的基本功能
- [ ] 实现文章订阅和群发消息
- [ ] 实现文章点赞和点对点消息
- [ ] 了解基于数据库实现的通知系统的问题
- [ ] 了解通知系统的改进方案



# 第一章-消息通知介绍  

## 知识点-消息通知介绍

### 1.目标

- [ ] 了解消息通知系统的业务场景
- [ ] 了解消息通知和即时通讯区别

### 2.路径

1. 消息通知的业务场景
2. 消息通知与即时通讯的区别

### 3.讲解

#### 3.1 消息通知的业务场景

​	消息通知微服务的定位是“平台内”的“消息”功能，分为全员消息，订阅类消息，点对点消息。例如系统通知，私信，@类消息      

- 全员消息

  系统通知，活动通知，管理员公告等全部用户都会收到的消息


- 订阅类消息   

  关注某一类数据的用户，该类数据有更新时向用户发送的消息。例如关注某位大v的微博，公众号，订阅某位知名作家的专栏

- 点对点消息     

  某位用户对另外一位用户进行操作后，系统向被操作的用户发送的消息。例如点赞，发红包。

  

#### 3.2 消息通知与即时通讯的区别

|          | 即时通信 | 消息通知 |
| :------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
|   传输的内容   | 包括文字聊天、语音消息发送、文件传输、音视频播放等吗，内容极其丰富。| 以文字，超链接为主，辅以图片，不能再多了。|
|  核心需求点  | 要求连接稳定可靠。就像网络游戏，如果总是掉线，你还玩的下去吗？ | 要求消息的高送达率，也就是说“这件事儿一定要想尽办法通知到对方”。对延时要求不高。 |
| 系统建设成本 | 存储成本高（图片，视频等）。基于TCP协议，需建设或租用多线机房，基建成本高。 | 一般只保存文本消息，存储成本低。可根据用户量自由调整服务器集群配置。 |
| 交互方式 | 任何消息均可回复 | 消息一般被设计为“仅通知，不需要回复” |
| 技术实现 | XMPP，MQTT，Strophe等全双工长连接协议 | JMS，AMQP，http等等各种协议 |





# 第二章-十次方消息通知实现

## 知识点-业务分析和表结构

### 1.目标

- [ ] 掌握十次方消息通知业务

### 2.路径

1. 业务分析
2. 表结构

### 3.讲解

#### 3.1 业务分析

用户可以对文章作者进行订阅，当被订阅的用户发布新的文章时，可以通过消息通知系统发送消息给订阅者。

流程如下：

![1563359920194](assets/1563417873173.png)

#### 3.2 表结构分析

把资料中的sql脚本导入到数据库中，创建数据库和表。

十次方消息通知微服务总共需要两张数据库表，tb_notice 和 tb_notice_fresh。

1. 消息通知表 tb_notice(保存用户的消息通知)

   | 字段名 | 类型 | 字段说明 |
| ---- | ---- | ---- |
   |id|varchar|ID|
   |receiverId|varchar|接收消息用户的ID（eg:文章作者的ID）|
   |operatorId|varchar|进行操作用户的ID(eg:谁点赞了)|
   |action|varchar|操作类型（发布文章publish，点赞thumbup等）|
   |targetType|varchar|被操作的对象，例如文章，评论等|
   |targetId|varchar|被操作对象的id，例如文章的id，评论的id|
   |createtime|datetime|发表日期|
   |type|varchar|消息通知类型(eg:sys,user)|
   |state|varchar|状态：0 未读；1 已读|


2. 待推送消息表 tb_notice_fresh(保存准备推送给用户的消息通知)

   用户登录之后 查询自己通知  数量

   | 字段名     | 类型     | 字段说明                               |
   | ---------- | -------- | -------------------------------------- |
   | userId | varchar  | 用户ID                       |
   | noticeId | varchar  | 通知id                       |

### 4.小结

1. 订阅类
   + 有一篇文章(作者玻璃通), 用户张三看到了这篇文章, 觉得很好, 张三就关注了这个作者玻璃通. 用户李四看到了这篇文章, 觉得很好, 李四就关注了这个作者玻璃通....
   + 作者玻璃通再写了文章, 就会发送信息推送给关注他的用户(张三,李四)
2. 点对点
   + 有一篇文章(作者玻璃通), 用户张三看到了这篇文章, 觉得很好, 张三点赞了
   + 作者玻璃通就会收到被点赞通知消息



## 知识点-搭建消息通知微服务

### 1.目标

- [ ] 掌握消息通知微服务的搭建

### 2.步骤

1. 创建tensquare_notice
2. 添加坐标
3. 创建application.yml
4. 创建启动类
5. 创建pojo
6. 创建拦截器(MyBatis Plus)

### 3.实现

1. 在tensquare_parent父工程下创建tensquare_notice子模块

2. 修改pom.xml文件，添加下面的配置

   ```xml
   <dependencies>
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
       <dependency>
           <groupId>com.alibaba</groupId>
           <artifactId>druid-spring-boot-starter</artifactId>
           <version>1.1.9</version>
       </dependency>
       <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
       </dependency>
       <dependency>
           <groupId>com.tensquare</groupId>
           <artifactId>tensquare_common</artifactId>
           <version>1.0-SNAPSHOT</version>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-data-redis</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-openfeign</artifactId>
       </dependency>
   </dependencies>
   ```

   


3. 在resources文件夹下添加application.yml文件，并添加下面的配置

   ```yaml
   server:
     port: 9014
   spring:
     application:
       name: tensquare-notice
     datasource:
       driver-class-name: com.mysql.jdbc.Driver
       url: jdbc:mysql://192.168.147.177:3306/tensquare_notice?characterEncoding=utf-8
       username: root
       password: 123456
     redis:
       host: 192.168.147.177
   # Mybatis-Plus 配置
   mybatis-plus:
   #  mapper-locations: classpath:/mapper/*Mapper.xml
     #实体扫描，多个package用逗号或者分号分隔
     typeAliasesPackage: com.tensquare.notice.pojo
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
   eureka:
     client:
       service-url:
         defaultZone: http://127.0.0.1:6868/eureka/
     instance:
       prefer-ip-address: true
   ```

   


4. 创建启动类 新建com.tensquare.notice包，并在该包下新建NoticeApplication，添加如下代码

   ```java
@SpringBootApplication
   @EnableEurekaClient
   @EnableFeignClients
   public class NoticeApplication {
   
       public static void main(String[] args) {
        SpringApplication.run(NoticeApplication.class, args);
       }
   
       @Bean
    public IdWorker idWorkker(){
           return new IdWorker(1, 1);
       }
   }
   ```
   
   

5. 编写pojo

   ```java
   @TableName("tb_notice")
   public class Notice implements Serializable {

   	@TableId(type = IdType.INPUT)
   	private String id;//ID

   	private String receiverId;//接收消息的用户ID
   	private String operatorId;//进行操作的用户ID

   	@TableField(exist = false)
   	private String operatorName;//进行操作的用户昵称
   	private String action;//操作类型（评论，点赞等）
   	private String targetType;//对象类型（评论，点赞等）

   	@TableField(exist = false)
   	private String targetName;//对象名称或简介
   	private String targetId;//对象id
   	private Date createtime;//创建日期
   	private String type;	//消息类型
   	private String state;	//消息状态（0 未读，1 已读）

    	//set get...   
   }
   ```

   ```java
   @TableName("tb_notice_fresh")
   public class NoticeFresh {
       
       private String userId;
       private String noticeId;

    //set ge  
   }

   ```

   

7. com.tensquare.notice.config添加配置

   ```java
   @Configuration
   //配置Mapper包扫描
   @MapperScan("com.tensquare.notice.dao")
   public class MyBatisPlusConfig {

       @Bean
       public PaginationInterceptor createPaginationInterceptor() {
           return new PaginationInterceptor();
       }
   }

   ```

### 4.小结



## 案例-实现消息通知微服务的基本功能

### 1.目标

- [ ] 实现消息通知微服务的基本功能

### 2.路径

#### 2.1基本的CRUD

1. 根据id查询消息通知
2. 新增通知
3. 分页查询消息通知
4. 修改通知
5. 根据用户id查询该用户的待推送消息（新消息）

#### 2.2完善返回的消息内容

### 3.实现

#### 3.1基本CRUD

##### 3.1.1根据id查询消息通知

`url: /notice/{id}`

`method:get`

- Controller

```java
@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        Notice notice =  noticeService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功",notice);
    }
}

```



- Service

```java
@Service
@Transactional
public class NoticeService {


    @Autowired
    private NoticeDao noticeDao;
    public Notice findById(String id) {
        return noticeDao.selectById(id);
    }
}

```

- Dao

```java
public interface NoticeDao extends BaseMapper<Notice>{
}
```

##### 3.1.2新增通知

`url: /notice`

`method:post`

+ Controller

```java
@RequestMapping(method = RequestMethod.POST)
public Result add(@RequestBody Notice notice) {
    noticeService.add(notice);
    return new Result(true, StatusCode.OK, "新增成功");
}
```



+ Service

```java
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private NoticeFreshDao noticeFreshDao;

    public void add(Notice notice) {
        //1.设置初始值
        notice.setState("0");
        notice.setCreatetime(new Date());
        String id = idWorker.nextId() + "";
        notice.setId(id);
        noticeDao.insert(notice);

        //2.待推送消息入库，新消息提醒
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setNoticeId(id);//消息id
        noticeFresh.setUserId(notice.getReceiverId());//待通知用户的id
        noticeFreshDao.insert(noticeFresh);
    }
```



+ Dao

```java
public interface NoticeFreshDao extends BaseMapper<NoticeFresh>{
}
```



##### 3.1.3分页查询消息通知

`url: /notice/findPage/{page}/{size}`

`method:get`

- Controller

```java
    @RequestMapping(value = "/findPage/{page}/{size}",method = RequestMethod.GET)
    public Result findPage(@PathVariable int page,@PathVariable int size) {
        Page<Notice> pageList =  noticeService.findPage(page,size);
        PageResult<Notice> pageResult = new PageResult<>(pageList.getTotal(), pageList.getRecords());
        return new Result(true, StatusCode.OK, "查询成功",pageResult);
    }
```



- Service

```java
    public Page<Notice> findPage(int page, int size) {
        Page<Notice> pageList = new Page<>(page,size);
        List<Notice> list = noticeDao.selectPage(pageList, new EntityWrapper<Notice>());
        pageList.setRecords(list);
        return pageList;
    }
```



##### 3.1.4修改通知

`url: /notice/{id}`

`method:put`

- Controller

```
@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
public Result update(@PathVariable String id,@RequestBody Notice notice) {
    notice.setId(id);
    noticeService.update(notice);
    return new Result(true, StatusCode.OK, "修改成功");
}
```

- Service

```
public void update(Notice notice) {
	noticeDao.updateById(notice);
}
```

##### 3.1.5根据用户id查询该用户的待推送消息（新消息）

`url: /notice/freshPage/{userId}/{page}/{size}`

`method:get`

- Controller

```java
@RequestMapping(value = "/freshPage/{userId}/{page}/{size}",method = RequestMethod.GET)
public Result freshPage(@PathVariable String userId,@PathVariable int page ,@PathVariable int size) {
    Page<NoticeFresh> pageList =  noticeService.freshPage(userId,page,size);
    PageResult<NoticeFresh> pageResult =   new PageResult<NoticeFresh>(pageList.getTotal(),pageList.getRecords());
    return new Result(true, StatusCode.OK, "查询成功",pageResult);
}
```



- Service

```java
    public Page<NoticeFresh> freshPage(String userId, int page, int size) {
        Page<NoticeFresh> pageList = new Page<>(page, size);
        EntityWrapper<NoticeFresh> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("userId",userId);
        List<NoticeFresh> list = noticeFreshDao.selectPage(pageList, entityWrapper);
        pageList.setRecords(list);
        return pageList;

    }
```





#### 3.2 完善返回的消息内容

​	数据库表设计的时候，为了提高性能，并没有保存用户昵称，文章标题等信息，只保存了主键id。但用户在查看消息的时候，只有id是很难阅读的，所以需要根据id，把用户昵称，文章标题等信息查询，并在返回消息之前设置到消息中。

​	由于消息通知微服务需要调用其他微服务获取字段信息，所以需要做 feign client 调用。

##### 3.2.1用户微服务的准备

+ 导入坐标

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

+ application.yml

```yml
eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:6868/eureka/
  instance:
    prefer-ip-address: true
```

+ 启动类

```java
@EnableEurekaClient
```



##### 3.2.2 tensquare_notice远程调用

+ 创建ArticleClient

```java
@FeignClient("tensquare-article")
public interface ArticleClient {

    @RequestMapping(value = "/article/{articleId}",method = RequestMethod.GET)
    Result findById(@PathVariable("articleId") String articleId);
}
```

+ 创建UserClient

```java
@FeignClient("tensquare-user")
public interface UserClient {
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    Result findById(@PathVariable(value = "userId") String userId);
}
```

+ 修改NoticeService

```java
    public Notice findById(String id) {
        Notice notice = noticeDao.selectById(id);
        getNoticeInfo(notice);
        return notice;
    }

    public Page<Notice> findPage(int page, int size) {
        Page<Notice> pageList = new Page<>(page,size);
        List<Notice> list = noticeDao.selectPage(pageList, new EntityWrapper<Notice>());

        for (Notice notice : list) {
            getNoticeInfo(notice);
        }

        pageList.setRecords(list);
        return pageList;
    }

    private void getNoticeInfo(Notice notice) {
        Result userResult = userClient.findById(notice.getOperatorId());
        HashMap userMap = (HashMap) userResult.getData();
        notice.setOperatorName(userMap.get("nickname").toString());
        //获取文章信息
        if ("article".equals(notice.getTargetType())) {
            Result articleResult = articleClient.findById(notice.getTargetId());
            HashMap articleMap = (HashMap) articleResult.getData();
            notice.setTargetName(articleMap.get("title").toString());
        }
    }
```

+ 测试功能

需要开启tensquare-eureka，tensquare-user，tensquare-article，tensquare-notice四个微服务进行测试

### 4.小结

![1567067509781](assets/1567067509781.png) 





## 文章订阅 - 实现群发消息功能

### 1.需求

​	用户在登录十次方以后，可以查看文章。如果觉得文章好，可以订阅文章作者，从而可以收到这个作者发布的新文章的消息。

+ 根据文章id，订阅文章作者。
+ 新增文章群发消息

### 2.分析

#### 2.1订阅文章作者分析

* 用户之间的文章订阅关系的数据存放在redis中。
* 用户订阅文章作者，则系统将作者的id放入用户自己的订阅集合（set类型），同时系统将用户的id放入文章作者的订阅者集合中。
* 由于redis的set集合，其中的数据是不重复的，所以不用担心重复数据的问题。

#### 2.2新增文章群发消息分析

新增文章后，需要通知订阅的用户，文章微服务需要调用消息通知微服务，创建消息。

![1563608327797](assets/1563608327797.png)



用户登录十次方后，访问的前端页面，页面需要定时轮询通知接口，获取消息。（接口已完成）.轮询由前端完成.

![1563608148788](assets/1563608341301.png)



### 3.实现

#### 3.1订阅文章作者实现

根据文章id订阅作者.需要在tensquare_article微服务中添加根据文章id订阅文章作者的功能

+ ArticleController

`url:/article/subscribe/{articleId}`

`method:post` 

```java
 /**
     * 订阅
     *
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/subscribe/{articleId}", method = RequestMethod.POST)
    public Result subscribe(@PathVariable("articleId") String articleId) {
        String userId = "2";
        boolean flag = articleService.subscribe(userId, articleId);
        if(flag){
            return new Result(true, StatusCode.OK, "订阅成功");
        }else{
            return new Result(true, StatusCode.OK, "取消订阅");
        }
    }
```

+ ArticleService

```java
  @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 订阅
     * @param userId
     * @param articleId
     * @return
     */
    public boolean subscribe(String userId, String articleId) {
        //1.根据文章Id获得文章作者Id
        String authorId =  articleDao.selectById(articleId).getUserid();
        //2.存到Redis
        //当前用户关注的作者set
        String userKey = "article_subscribe_"+userId;
        //该作者被关注的用户set
        String authorKey = "article_author_"+authorId;

        Boolean isMember = redisTemplate.opsForSet().isMember(userKey, authorId);

        //3.判断是否已经订阅
        if(isMember){
            redisTemplate.opsForSet().remove(userKey,authorId);
            redisTemplate.opsForSet().remove(authorKey,userId);
            return false;
        }else{
            redisTemplate.opsForSet().add(userKey,authorId);
            redisTemplate.opsForSet().add(authorKey,userId);
            return true;
        }
    }
```



+  启动微服务测试功能

需要开启tensquare-eureka，tensquare-article

####3.2 新增文章群发消息实现

1） 在tensquare-article新增NoticeClient，调用消息通知微服务

pom.xml添加依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

启动类添加注解

```java
@EnableFeignClients
```



在com.tensquare.article.client中编写NoticeClient，把消息通知的Notice类复制到文章微服务

```java
@FeignClient("tensquare-notice")
public interface NoticeClient {
    @RequestMapping(value = "/notice",method = RequestMethod.POST)
    Result add(@RequestBody Notice notice);
}
```



2） 修改ArticleService的save方法，进行消息通知

```java
    @Autowired
    private NoticeClient noticeClient;


    /**
     * 新增文章
     *
     * @param article
     */
    public void add(Article article) {
        //1.新增文章
        String authorId = "1";
        article.setId(idWorker.nextId() + "");
        article.setVisits(0);   //浏览量
        article.setThumbup(0);  //点赞数
        article.setComment(0);  //评论数
        article.setUserid(authorId);

        //2.通知
        Set<String> members = redisTemplate.opsForSet().members("article_author_" + authorId);
        for (String uid : members) {
            //消息通知
            Notice notice = new Notice();
            notice.setReceiverId(uid);
            notice.setOperatorId(authorId);
            notice.setAction("publish");
            notice.setTargetType("article");
            notice.setTargetId(article.getId());
            notice.setCreatetime(new Date());
            notice.setType("sys");
            notice.setState("0");
            noticeClient.add(notice);
        }


        articleDao.insert(article);
    }
```

### 4.小结



## 文章点赞 - 实现点对点消息功能

### 1.需求

​	用户在登录十次方以后，可以查看文章。如果觉得文章好,可以对文章进行点赞. 文章作者可以获得通知.

### 2.分析

#### 2.1实现文章点赞

1. 使用Redis控制重复点赞
2. 点赞说白了就是根据文章id把thumbup更新

#### 2.2实现点赞消息通知

### 3.实现

#### 3.1 实现文章点赞

`url:/article/thumbup/{articleId}`

`method:put`

+ ArticleController

```java

    /**
     * 文章点赞
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/thumbup/{articleId}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable(value = "articleId") String articleId) {
        //模拟当前用户
        String userId = "4";

        //控制重复点赞
        Object value = redisTemplate.opsForValue().get("article_" + userId + "_" + articleId);
        if(value != null){
            return new Result(false, StatusCode.REPERROR, "不能重复点赞");
        }

        articleService.thumbup(articleId);
        redisTemplate.opsForValue().set("article_" + userId + "_" + articleId,"OK");
        return new Result(true, StatusCode.OK, "点赞成功");
    }
```



编写ArticleService：

```java
    /**
     * 文章点赞
     * @param articleId
     */
    public void thumbup(String articleId) {
        Article article = articleDao.selectById(articleId);
        article.setThumbup(article.getThumbup()+1);
        articleDao.updateById(article);
    }
```





####3.2 实现点赞消息通知

修改ArticleService的点赞方法，增加消息通知：

```java
    /**
     * 文章点赞
     * @param articleId
     */
    public void thumbup(String articleId, String userId) {
        Article article = articleDao.selectById(articleId);
        article.setThumbup(article.getThumbup()+1);
        articleDao.updateById(article);

        //消息通知
        Notice notice = new Notice();
        notice.setReceiverId(article.getUserid());
        notice.setOperatorId(userId);
        notice.setAction("thumbup");
        notice.setTargetType("article");
        notice.setTargetId(articleId);
        notice.setCreatetime(new Date());
        notice.setType("user");
        notice.setState("0");

        noticeClient.add(notice);
    }
```

### 4.小结



## 基于db实现的通知系统存在的问题

### 1.目标

- [ ] 了解通知系统的改进方案

### 2.讲解

#### 2.1 数据库访问压力大

​	用户的通知消息和新通知提醒数据都放在数据库中，数据库的读写操作频繁，尤其是tb_notice_refresh表，访问压力大。

#### 2.2 服务器性能压力大

​	采用页面轮询调接口的方式实现服务器向用户推送消息通知，服务器的接口访问压力大。

#### 2.3 改进的方法

 1. 使用 rabbitmq 实现新消息提醒数据的缓存功能，替代tb_notice_refresh表

 2. 使用全双工长连接的方式实现服务器向用户推送最新的消息通知，替换轮询

    * 页面使用websocket
* 微服务端使用异步高性能框架netty

### 3.小结





