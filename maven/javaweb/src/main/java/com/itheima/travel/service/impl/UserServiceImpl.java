package com.itheima.travel.service.impl;

import com.itheima.travel.constant.Constant;
import com.itheima.travel.dao.IUserDao;
import com.itheima.travel.domain.User;
import com.itheima.travel.exception.AlreadyActiveException;
import com.itheima.travel.exception.ErrorCodeException;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.IUserService;
import com.itheima.travel.utils.MailUtil;
import com.itheima.travel.utils.Md5Util;

/**
 * 包名:com.itheima.travel.service.impl
 * 作者:Leevi
 * 日期2019-05-28  09:54
 */
public class UserServiceImpl implements IUserService{
    private IUserDao dao = (IUserDao) BeanFactory.getBean("userDao");
    @Override
    public User findUserByUsername(String username) {
        return dao.findUserByUsername(username);
    }

    @Override
    public void doRegister(User user) throws Exception {
        //1.发送激活邮件
        //调用MailUtil的方法发送邮件
        MailUtil.sendMail(user.getEmail(),"欢迎注册黑马旅游网，我亲爱的"+user.getName()+"用户,请点击<a href='http://localhost:8080/user?action=active&code="+user.getCode()+"'>激活</a>");
        //2.调用dao层的方法，将数据保存到数据库
        dao.saveUser(user);
    }

    @Override
    public void doActive(String code) throws AlreadyActiveException, ErrorCodeException {
        //1.根据激活码，查找user
        User user = dao.findUserByCode(code);
        //2.判断user是否为null
        if (user != null) {
            //激活码正确
            //判断用户是否已激活
            if (user.getStatus().equals(Constant.ACTIVED)) {
                //表用户已经激活过了,告诉用户不能重复激活
                throw new AlreadyActiveException(Constant.ALREADY_ACTIVE);
            }else {
                //用户还未激活
                //到数据库修改用户信息
                user.setStatus(Constant.ACTIVED);
                dao.updateUser(user);
            }
        }else {
            //激活码被篡改
            throw new ErrorCodeException(Constant.ERROR_CODE);
        }
    }

    @Override
    public User checkLogin(String username, String password) {
        //1.校验用户是否正确
        User user = dao.findUserByUsername(username);
        if (user != null) {
            //用户名正确
            //2.校验密码
            String pwd = user.getPassword();
            //将password使用MD5加密后再校验
            try {
                password = Md5Util.encodeByMd5(password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pwd.equals(password)) {
                //密码正确
                //校验是否已激活
                if (user.getStatus().equals(Constant.ACTIVED)) {
                    //已激活
                    return user;
                }else {
                    //未激活
                    throw new RuntimeException(Constant.LOGINFAILED_UNACTIVE);
                }
            }else {
                //密码错误
                throw new RuntimeException(Constant.LOGINFAILED_ERROR_PASSWORD);
            }
        }else {
            //用户名错误
            throw new RuntimeException(Constant.LOGINFAILED_ERROR_USERNAME);
        }
    }
}
