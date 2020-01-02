import domain.ListUser;
import domain.Student;
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
import java.util.HashMap;
import java.util.Map;

public class text {
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
    @After
    public void destroy(){
        //提交事务
        sqlSession.commit();
    }
    @Test
    public void textAddUser(){
        User user = new User();
        user.setUsername("小泽老师");
        user.setSex("女");
        user.setBirthday("1963-05-09");
        user.setAddress("召唤师峡谷");
        mapper.addUser(user);
    }
    @Test
    public void textupdate(){
        User user = new User();
        user.setId(65);
        user.setSex("男");
        mapper.updateUser(user);
    }
    @Test
    public void textdelete(){
        mapper.deleteUser(68);
    }
    @Test
    public void textselect(){
        User user = mapper.selectUser(67);
        System.out.println(user);
    }
    @Test
    public void textselect1(){
        ListUser users = mapper.selectUser1(67);
        System.out.println(users);
    }
    @Test
    public void textselect2(){
        ListUser user = mapper.selectUser2(67);
        System.out.println(user);
    }
    @Test
    public void textselect3(){
        ListUser user = mapper.selectUser4(67);
        System.out.println(user);
    }
    @Test
    public void test(){
//        Map map=new HashMap();
//        map.put("name","tang");
//        map.put("age","18");
//        map.put("sex","男");
        Student map= new Student();
        map.setName("tang");
        map.setAge("18");
        map.setSex("男");
        int i = mapper.addStudent(map);
        int id = map.getId();
        System.out.println(i);
        System.out.println(id);
    }

    @Test
    public void updateTest(){
        Map map=new HashMap();
        map.put("name","唐");
        map.put("id","1");
        int i = mapper.updateStuent(map);
        String sex = (String)map.get("sex");
        System.out.println(i);
        System.out.println(sex);
    }
}
