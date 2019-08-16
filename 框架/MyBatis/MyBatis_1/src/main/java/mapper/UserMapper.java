package mapper;

import domain.User;
import java.util.List;

/**
 * 包名:com.itheima.mapper
 * 作者:Leevi
 * 日期2019-06-04  08:54
 */
public interface UserMapper {
    /**
     * 添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    int deleteUser(int id);

    /**
     * 更新用户的信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 根据id查询一个用户信息
     * @param id
     * @return
     */
    User findUserById(int id);

    /**
     * 查询所有用户的信息
     * @return
     */
    List<User> findAll();

    /**
     * 根据name进行模糊查询
     * @return
     */
    List<User> findUserByName(String name);

    /**
     * 模糊查询
     * @param username
     * @return
     */
    User findUserByVo(String username);

    /**
     * 根据id查找用户的用户名
     * @param id
     * @return
     */
    String findUsernameById(int id);

    /**
     * 根据id查询用户信息，并且封装到User对象中
     * @param id
     * @return
     */
    User findUserInfoById(int id);
}
