package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {

    Member findMemeberByTelephone(String telephone);

    void add(Member member);

    Map<String, Object> findMemberCount();

    List<Map<String, Object>> getMemberSexReport();

    List<Map<String, Object>> getMemberAgeReport();

    Map<String, Object> findMemberReportByStartTimeAndEndTime(String startTime, String endTime) throws Exception;

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    Member findById(Integer id) throws Exception;

    void edit(Member member);

    void deleteById(Integer id);
}
