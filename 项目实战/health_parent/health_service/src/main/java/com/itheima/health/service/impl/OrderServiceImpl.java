package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderService;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:44
 * @Version V1.0
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderSettingDao orderSettingDao;

    @Autowired
    MemberDao memberDao;

    @Override
    public Result submit(Map<String, Object> map) throws Exception {
        /**
         * 1：使用预约日期查询“预约设置表”，判断当前预约日期是否存在
             如果不存在：返回错误提示信息”当前预约日期不存在“
             如果存在
             2：判断当前预约日期是否已经预约满
             如果已经预约满，此时不能再预约，返回错误信息“提示当前预约人数已满”
         */
        String orderDateString = (String)map.get("orderDate");
        // 将字符串类型，转换成日期类型（DateUtils工具类）
        Date orderDate = DateUtils.parseString2Date(orderDateString);
        // 1：使用预约日期查询“预约设置表”，判断当前预约日期是否存在
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByOrderDate(orderDate);
        if(orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        // 最多预约的人数
        int number = orderSetting.getNumber();
        // 已经预约的人数
        int reservations = orderSetting.getReservations();
        if(reservations>=number){
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        /**
         * 3：使用手机号查询“会员表”，判断当前手机号是否是会员
         如果“是”，判断当前会员，当前时间，套餐id是否已经存在（防止重复预约）
         使用当前会员，当前时间，套餐id作为查询条件，查询订单表
         如果存在数据：表示”重复预约”

         如果“不是”，先新增会员（会员id），

         得到会员id，再向订单表中新增数据
         */
        // 套餐id
        Integer setmealId =Integer.parseInt((String)map.get("setmealId"));
        // 电话
        String telephone = (String)map.get("telephone");
        Member member = memberDao.findMemberByTelephone(telephone);
        // 表示是“会员”，防止重复预约
        if(member!=null){
            // 使用当前会员id，当前时间，套餐id作为查询条件，查询订单表
            Order order = new Order(member.getId(),orderDate,null,null,setmealId);
            List<Order> orderList = orderDao.findOrderListByCondition(order);
            if(orderList!=null && orderList.size()>0){
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }
        /**
         *    如果“不是”，先新增会员（会员id），
              不管是否是会员，得到会员id，再向订单表中新增数据
         */
        // 不是会员，注册会员
        if(member==null){
            member = new Member();
            member.setIdCard((String)map.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setSex((String)map.get("sex"));
            member.setRegTime(new Date());
            member.setName((String)map.get("name"));
            memberDao.add(member);
        }
        // 不管是否是会员，得到会员id，再向订单表中新增数据
        Order order = new Order(member.getId(),orderDate,(String)map.get("orderType"),Order.ORDERSTATUS_NO,setmealId);
        orderDao.add(order);

        // 4：表示更新预约设置表，根据当前时间，更新reservations字段（已经预约的人数），值+1
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.update(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map<String, Object> findById(Integer id) throws Exception {
        Map<String,Object> map = orderDao.findById(id);
        if(map!=null){
            Date date = (Date)map.get("orderDate");
            String stringDate = DateUtils.parseDate2String(date);
            map.put("orderDate",stringDate);
        }
        return map;
    }


    // 饼状图统计报表
    @Override
    public List<Map<String,Object>>  getSetmealReport() {
        List<Map<String,Object>>  countMap  = orderDao.getSetmealReport();
        return countMap;
    }

    @Override
    public Map<String, Object> getBusinessReportData() {
        // 返回结果
        Map<String,Object> map = new HashMap<>();
        try {
            // 报表时间（当前时间）
            // 1：会员相关t_member
            // 2：预约相关t_order

            // 当前时间
            String today = DateUtils.parseDate2String(DateUtils.getToday());
            // 本周的第一天
            String weekFirstDay = DateUtils.parseDate2String(DateUtils.getThisWeekMonday(DateUtils.getToday()));
            // 本周的最后一天
            String weekLastDay =  DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
            // 本月的第一天
            String monthFirstDay = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
            // 本月的最后一天
            String monthLastDay = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());


            // 当天新增会员数
            Integer todayNewMember = memberDao.findMemberCountByDate(today);
            // 总会员数
            Integer totalMember = memberDao.findMemberTotalCount();
            // 本周新增会员数
            Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(weekFirstDay);
            // 本月新增会员数
            Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(monthFirstDay);

            // 当天预约订单数
            Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
            // 本周预约订单数
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("start",weekFirstDay);
            paramMap.put("end",weekLastDay);
            Integer thisWeekOrderNumber = orderDao.findOrderCountBetweenDate(paramMap);
            // 本月预约订单数
            Map<String,Object> monthMap = new HashMap<>();
            monthMap.put("start",monthFirstDay);
            monthMap.put("end",monthLastDay);
            Integer thisMonthOrderNumber = orderDao.findOrderCountBetweenDate(monthMap);

            // 当前时间到诊订单数
            Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
            // 本周到诊的订单数
            Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(weekFirstDay);
            // 本月到诊的订单数
            Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(monthFirstDay);

            // 热门套餐
            List<Map<String,Object>> hotSetmeal = orderDao.findHotSetmeal();

            map.put("reportDate",today);
            map.put("todayNewMember", todayNewMember);
            map.put("totalMember", totalMember);
            map.put("thisWeekNewMember", thisWeekNewMember);
            map.put("thisMonthNewMember", thisMonthNewMember);

            map.put("todayOrderNumber",todayOrderNumber);
            map.put("thisWeekOrderNumber",thisWeekOrderNumber);
            map.put("thisMonthOrderNumber",thisMonthOrderNumber);
            map.put("todayVisitsNumber",todayVisitsNumber);
            map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
            map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);

            map.put("hotSetmeal",hotSetmeal);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
