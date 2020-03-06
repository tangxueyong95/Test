

import domain.User;
import mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 包名:mybatis
 * 作者:tangxueyong
 * 日期2019-06-04  09:14
 */
public class TestMybatis {

    private SqlSession sqlSession;
    private UserMapper mapper;

    @Before
    public void init() throws IOException {
        //1.将主配置文件加载进内存，形成一个字节输入流
        //底层其实就是使用的类加载器将文件转换成流
        InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");

        //2.创建一个SqlSessionFactory对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = builder.build(is);

        //3.使用SqlSessionFactory创建一个SqlSession对象
        sqlSession = sessionFactory.openSession();

        //4.使用sqlSession对象创建一个UserMapper接口的动态代理对象
        mapper = sqlSession.getMapper(UserMapper.class);
    }
    @Test
    public void testAddUser(){
        //测试使用mybatis添加用户
        User user = new User();
        user.setUsername("奥巴马");
        user.setSex("男");
        user.setBirthday("1963-05-09");
        user.setAddress("召唤师峡谷");
        mapper.addUser(user);
    }
    @Test
    public void testDeleteUser(){
        mapper.deleteUser(60);
    }

    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setId(64);
        user.setUsername("大泽老师");
        user.setSex("男");
        user.setAddress("中国黑龙江省哈尔滨市");
        mapper.updateUser(user);
    }

    @Test
    public void testFindUserById(){
        //测试根据id查询用户信息
        User user = mapper.findUserById(63);
        System.out.println(user);
    }

    @Test
    public void testFindAll(){
        //测试查询所有数据
        List<User> users = mapper.findAll();
        System.out.println(users);
    }

    @Test
    public void testFindUserByName(){
        List<User> users = mapper.findUserByName("杰");
        System.out.println(users);
    }

    @Test
    public void testFindUserByVo(){
        User user = new User();
        user.setUsername("周杰伦");
        User user1 = mapper.findUserByVo(user.getUsername());
        System.out.println(user1);
    }

    @Test
    public void testFindUsernameById(){
        String username = mapper.findUsernameById(63);
        System.out.println(username);
    }

    @Test
    public void testFindUserInfoById(){
        User user = mapper.findUserInfoById(63);
        System.out.println(user);
    }
    @After
    public void destroy(){
        //提交事务
        sqlSession.commit();
    }
}
