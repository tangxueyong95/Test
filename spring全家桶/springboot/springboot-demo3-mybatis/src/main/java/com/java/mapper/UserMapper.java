package com.java.mapper;

import com.java.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*****
 * @Author: tangxueyong
 * @Date: 2019/7/14 11:06
 * @Description: com.java.mapper
 * @Mapper:表示当前接口属于Dao接口，会给他创建代理对象
 ****/
@Mapper     // 表示告诉SpringBoot该对象属于MyBatis的Dao对象，会给他生成一个代理对象  SqlSession.getMapper(UserMapper.class)
//@Repository  //表示将对象给SpringIOC容器管理
public interface UserMapper {

    /***
     * 集合查询
     */
    List<User> findAll();

}
