package ssm.service;


import ssm.domain.User;

import java.util.List;

public interface UserService {

    User loginUser(String username, String password);

    List<User> findAll();
}
