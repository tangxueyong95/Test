package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import com.itheima.health.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:44
 * @Version V1.0
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Override
    public Member findMemeberByTelephone(String telephone) {
        return memberDao.findMemberByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        if(!StringUtils.isEmpty(member.getPassword())){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    // 查询每月的会员数据量统计
    @Override
    public Map<String, Object> findMemberCount() {
        Map<String,Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        // 获取当前时间之前的前12月
        calendar.add(Calendar.MONTH,-12);
        // 从2018-8   到 2019-7 每个月份
        List<String> datelist = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1); // +1  2018-8
            try {
                datelist.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("months",datelist);

        // 使用月份查询每个月份（截止到月底）会员的数量
        List<Integer> countList = new ArrayList<>();
        for (String s : datelist) {
            String date = s+"-31"; // 2019-07-31
            Integer count = memberDao.findCountByDate(date);
            countList.add(count);
        }
        map.put("memberCount",countList);
        return map;
    }
}
