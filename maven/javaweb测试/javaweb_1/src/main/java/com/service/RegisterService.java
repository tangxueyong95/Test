package com.service;

import com.dao.DaoUser;
import com.domain.User;

public class RegisterService {
    private DaoUser dao = new DaoUser();
    public boolean register1Service(String name) {
        return dao.register1(name);
    }

    public boolean registerService(String name, String password) {
        return dao.register(name,password);
    }

    public User loginService(String name, String password) {
        return dao.login(name,password);
    }
}
