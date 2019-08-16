package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.OrderService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:45
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/report")
public class ReportController {


    @Reference
    MemberService memberService;

    @Reference
    OrderService orderService;

    // 按照月份统计会员的增值量
    @RequestMapping(value = "/getMemberReport")
    public Result upload(){
        try {
            Map<String,Object> map = memberService.findMemberCount();
            // 返回结果
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    // 按照月份统计会员的增值量
    @RequestMapping(value = "/findMemberReportByStartTimeAndEndTime")
    public Result findMemberReportByStartTimeAndEndTime(String startTime, String endTime){
        try {
            Map<String,Object> map = memberService.findMemberReportByStartTimeAndEndTime(startTime,endTime);
            // 返回结果
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    // 预约套餐占比饼状图
    @RequestMapping(value = "/getSetmealReport")
    public Result getSetmealReport(){
        try {
            List<Map<String,Object>> countMap = orderService.getSetmealReport();
            List<String> list = new ArrayList<>();
            for (Map<String, Object> stringIntegerMap : countMap) {
                String name = (String)stringIntegerMap.get("name");
                list.add(name);
            }
            Map<String,Object> map = new HashMap<>();
            // 组织数据
            map.put("setmealCount",countMap);
            map.put("setmealNames",list);
            // 返回结果
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @RequestMapping(value = "/getMemberSexReport")
    public Result getMemberSex(){
        try {
            List<Map<String,Object>> countMap = memberService.getMemberSexReport();
            List<String> list = new ArrayList<>();
            for (Map<String, Object> stringIntegerMap : countMap) {
                String name = (String)stringIntegerMap.get("name");
                list.add(name);
            }
            Map<String,Object> map = new HashMap<>();
            // 组织数据
            map.put("MemberCount",countMap);
            map.put("MemberSex",list);
            // 返回结果
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @RequestMapping(value = "/getMemberAgeReport")
    public Result getMemberAgeReport(){
        try {
            List<Map<String,Object>> countMap = memberService.getMemberAgeReport();
            List<String> list = new ArrayList<>();
            for (Map<String, Object> stringIntegerMap : countMap) {
                String name = (String)stringIntegerMap.get("name");
                list.add(name);
            }
            Map<String,Object> map = new HashMap<>();
            // 组织数据
            map.put("MemberAgeCount",countMap);
            map.put("MemberAge",list);
            // 返回结果
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    // 业务数据统计
    @RequestMapping(value = "/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String,Object> map = orderService.getBusinessReportData();
            // 返回结果
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    // 业务数据统计（excel报表导出）
    @RequestMapping(value = "/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> result = orderService.getBusinessReportData();
            // 返回结果
            // 返回时间
            String reportDate = (String)result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            // 生成excel，查找模板excel的位置
            // poi报表的核心api：XSSFWorkBook、XSSFSheet、XSSFRow、XSSFCell
            String xmlFile = request.getSession().getServletContext().getRealPath("template")+ File.separator+"report_template.xlsx";
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(xmlFile)));
            // 获取Sheet（第一个工作表）
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            // 插入：日期
            // 获取Row
            XSSFRow row = sheet.getRow(2);
            XSSFCell cell = row.getCell(5);
            cell.setCellValue(reportDate);

            //
            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for(Map map : hotSetmeal){//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum ++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            // 通过输出流输出
            ServletOutputStream outputStream = response.getOutputStream();
            // 设置响应的内容类型
            response.setContentType("application/vnd.ms-excel");
            // 设置以附件的形式下载（默认是内连inline，相当于在浏览器中直接打开）
            response.setHeader("content-Disposition","attachment;filename=report.xlsx");

            // 将当前的workbook写到输出流中
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            xssfWorkbook.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
