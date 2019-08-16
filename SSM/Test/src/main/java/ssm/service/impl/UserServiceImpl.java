package ssm.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ssm.dao.UserDao;
import ssm.domain.User;
import ssm.service.UserService;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/19 8:55
 * @Version V1.0
 */
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User loginUser(String username, String password) {
        User user= userDao.login(username,password);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
