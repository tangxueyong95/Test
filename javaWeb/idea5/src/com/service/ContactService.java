package com.service;

import com.dao.ContactDao;
import com.domain.User;
import com.utils.DateUtil;

public class ContactService {
    private ContactDao dao = new ContactDao();

    public User longinContact(String name, String password) {
        User user = dao.longin(name);
        if (user != null) {
            String currentDate = DateUtil.getCurrentDate();
            String time = user.getTime();
            Integer lock = user.getSuo();
            Integer number = user.getNumber();
            if (!currentDate.equals(time)) {
                lock = 0;
                number = 0;
                time = currentDate;
            }
            String msg = "";
            if (lock == 1) {
                msg = "登录失败，该账号已被锁定，请明天再试";
            } else {
                if (password.equals(user.getPassword())) {
                    user.setNumber(0);
                    dao.updateUser(user);
                    return user;
                } else {
                    number++;
                    if (number == 3) {
                        lock = 1;
                        msg = "密码错误,该账号被锁定，请明天再试";
                    } else {
                        msg = "密码错误，今天还有" + (3 - number) + "次机会";
                    }
                    user.setNumber(number);
                    user.setSuo(lock);
                    user.setTime(time);
                    dao.updateUser(user);
                }
            }
            throw new RuntimeException(msg);
        } else {
            throw new RuntimeException("用户名错误");
        }
    }

    public boolean addUser(String name, String password) {
        return dao.add(name,password);
    }
}
