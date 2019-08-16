package com.service;


import com.dao.ContactDao;
import com.domain.Contact;
import com.domain.PageBean;

import java.util.List;

/**
 * 包名:com.itheima.service
 * 作者:Leevi
 * 日期2019-05-13  11:56
 */
public class ContactService {
    /**
     * 查看所有联系人的方法
     *
     * @return
     */
    private ContactDao dao = new ContactDao();

    public List<Contact> findAllContacts() {
        //调用dao层的方法，查询所有联系人的信息
        List<Contact> contacts = dao.findAllContacts();
        return contacts;
    }

    public boolean removeContacts(String id) {
        return dao.remove(id);
    }

    public boolean addContacts(Contact contact) {
        return dao.add(contact);
    }

    public boolean updateContact(Contact contact) {
        return dao.update(contact);
    }

    public PageBean findPageBean(Integer currentPage) {
        return dao.find(currentPage);
    }
}
