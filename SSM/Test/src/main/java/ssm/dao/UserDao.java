package ssm.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ssm.domain.User;

import java.util.List;

// 因为spring管理了所有的Dao接口，@Repository也可以不加
public interface UserDao {
    // 登录
    @Select("SELECT * FROM s_user WHERE username=#{username} AND PASSWORD=#{password}")
    public User login(@Param("username") String username,@Param("password") String password);

    @Select("SELECT * FROM s_user")
    List<User> findAll();
}
