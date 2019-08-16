package com.itheima.health.dao;

import com.itheima.health.pojo.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao {

    Member findMemberByTelephone(String telephone);

    void add(Member member);

    Integer findCountByDate(String date);

    // 根据当前时间，统计当前时间注册的会员
    public Integer findMemberCountByDate(String date);
    // 根据当前时间，统计当前时间之后注册的会员数量
    public Integer findMemberCountAfterDate(String date);
    // 总会员数
    public Integer findMemberTotalCount();
}
