package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import com.itheima.health.utils.DateUtils;
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
        List<Integer> countList = new ArrayList<>();
        for (String s : datelist) {
            String date = s+"-31"; // 2019-07-31
            Integer count = memberDao.findCountByDate(date);
            countList.add(count);
        }
        map.put("memberCount",countList);
        return map;
    }

    @Override
    public List<Map<String, Object>> getMemberSexReport() {
        List<Map<String, Object>> list = memberDao.getMemberSexReport();
        return list;
    }

    @Override
    public List<Map<String, Object>> getMemberAgeReport() {
        return memberDao.getMemberAgeReport();
    }

    @Override
    public Map<String, Object> findMemberReportByStartTimeAndEndTime(String startTime, String endTime) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> dateList = new ArrayList<>();


        Date startDate = DateUtils.parseString2Date(startTime);
        Date endDate = DateUtils.parseString2Date(endTime);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        //获取月份
        while (true) {
            Date time = startCalendar.getTime();
            if (time.getTime() <= endDate.getTime()) {
                //修改成yyyy-MM-dd形式  以一个月为跨度  小于一个月则显示起始时间和终止时间:
                String date = DateUtils.parseDate2String(time);
                dateList.add(date);
            } else {
                //Calendar endCalendar = Calendar.getInstance();
                //endCalendar.setTime(endDate);
                //如果开始日大于终止日  在加一次月份  例如:查询2019-06-20 到2019-07-15 :查询六月和七月
/*
                if (startCalendar.get(Calendar.DAY_OF_MONTH) > endCalendar.get(Calendar.DAY_OF_MONTH)) {
                    dateList.add(DateUtils.parseDate2String(time));
                    break;
                }
*/
                dateList.add(endTime);
                break;
            }
            startCalendar.add(Calendar.MONTH,1);
        }

        resultMap.put("months",dateList);

        //使用月份查询每个月会员数:
        List<Integer> countList = new ArrayList<>();
/*
        for (int i = 0; i < dateList.size(); i++) {
            //如果是最后一天 就按最后一天日期查询
            if (i == dateList.size() - 1) {
                Integer count = memberDao.findCountByDate(endTime);
                countList.add(count);
            } else {
                String date = dateList.get(i)+"-31"; // 2019-07-31
                Integer count = memberDao.findCountByDate(date);
                countList.add(count);
            }
        }
*/
        for (String date : dateList) {
            Integer count = memberDao.findCountByDate(date);
            countList.add(count);
        }
        resultMap.put("memberCount",countList);
        return resultMap;
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize); // 底层用mysql计算分页
        Page<Member> page = memberDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Member findById(Integer id) throws Exception {
        Member member = memberDao.findById(id);
        return member;
    }

    @Override
    public void edit(Member member) {
        if (member.getPassword() != null && member.getPassword() != "") {
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.edit(member);

    }

    @Override
    public void deleteById(Integer id) {
        // 1：使用会员id，查询t_order是否有预约，如果存在数据，则不能删除
        long count = memberDao.findCountByMemberId(id);
        // 有数据
        if(count>0){
            throw new RuntimeException(MessageConstant.DELETE_MEMBER_RELATION);
        }
        // 2：删除检查项
        memberDao.deleteById(id);
    }
}
