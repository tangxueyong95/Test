package service.impl;



import service.AccountService;

import java.util.*;

public class AccountServiceImpl3 implements AccountService {

    Object[] arrays;
    List<Object> list;
    Set<Object> set;
    Map<String,Object> map;
    Properties properties;

    public void setArrays(Object[] arrays) {
        this.arrays = arrays;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public void setSet(Set<Object> set) {
        this.set = set;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void saveAccount() {
        System.out.println("执行AccountServiceImpl中的saveAccount方法！ arrays:"+Arrays.toString(arrays)+"        list:"+list+"      set:"+set+"        map:"+map+"         properties"+properties);
    }
}
