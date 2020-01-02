package mapper;

import domain.ListUser;
import domain.Student;
import domain.User;
import domain.User1;
import org.apache.ibatis.annotations.*;

import java.util.Map;

public interface UserMapper {
    @Insert("insert into user2 values (#{id},#{username},#{birthday},#{sex},#{address},null)")
    void addUser(User user);

    @Delete("delete from user2 where id=#{id}")
    void deleteUser(int id);

    @Update("update user2 set sex=#{sex} where id=#{id}")
    void updateUser(User user);

    @Select("select * from user2 where id=#{id}")
    User selectUser(int id);

    ListUser selectUser1(int id);

    @Results(id = "a", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "username", property = "username"),
            @Result(column = "birthday", property = "birthday"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "address", property = "address"),
            @Result(property = "users", many = @Many(select = "mapper.UserMapper.selectUser3"),column = "aid"),
    })
    @Select("select * from user2 where id=#{id}")
    ListUser selectUser2(int id);

    ListUser selectUser4(int id);

    /*@Select("select * from user1 where aid=#{aid}")*/
    User1 selectUser3(int aid);

    int addStudent(Map map);

    int addStudent(Student map);

    int updateStuent(Map map);
}
