package com.itheima.health.service;

import com.itheima.health.pojo.Member;

import java.util.Map;

public interface MemberService {

    Member findMemeberByTelephone(String telephone);

    void add(Member member);

    Map<String, Object> findMemberCount();
}
