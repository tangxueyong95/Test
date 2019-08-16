package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @ClassName TestPOI
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/29 14:49
 * @Version V1.0
 */
public class TestPOI {

    // 读取excel   组织数据  导入数据库 --> 批量新增
    /**
     * 1：XSSFWorkBook：工作簿（1个）
     * 2：XSSFSheet：工作表（多个）
     * 3：XSSFRows：行（多个）
     * 4：XSSFCell：单元格（列）（多个）
     */
    // 方案一
    @Test
    public void importData() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook("D:/hello.xlsx");
        XSSFSheet sheet = workbook.getSheetAt(0);// 读取第一个Sheet（工作表）
        // 简化代码
        for (Row row : sheet) {
            for (Cell cell : row) {
                String s = cell.getStringCellValue();
                System.out.println(s);
                System.out.println("---------------------------------");
            }
        }
        // 关闭
        workbook.close();
    }
    // 方案二
    @Test
    public void importData2() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook("D:/hello.xlsx");
        XSSFSheet sheet = workbook.getSheetAt(0);// 读取第一个Sheet（工作表）
        int rownum = sheet.getLastRowNum(); // 读取最后一行（从0开始）
        for(int i=0;i<rownum;i++){
            XSSFRow row = sheet.getRow(i);
            short cellnum = row.getLastCellNum();
            for(int j=0;j<cellnum;j++){
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
                System.out.println("-------------------------------------");
            }
        }
        // 关闭
        workbook.close();
    }

    // 数据库   查询数据  导出excel    -->查看，打印
    /**
     * 1：XSSFWorkBook：工作簿（1个）
     * 2：XSSFSheet：工作表（多个）
     * 3：XSSFRows：行（多个）
     * 4：XSSFCell：单元格（列）（多个）
     */
    @Test
    public void exportExcel() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("传智播客");

        // 组织数据
        XSSFRow row1 = sheet.createRow(0);// 第1行
        row1.createCell(0).setCellValue("姓名");
        row1.createCell(1).setCellValue("年龄");
        row1.createCell(2).setCellValue("地址");

        XSSFRow row2 = sheet.createRow(1);// 第2行
        row2.createCell(0).setCellValue("张三");
        row2.createCell(1).setCellValue("22");
        row2.createCell(2).setCellValue("北京");

        XSSFRow row3 = sheet.createRow(2);// 第3行
        row3.createCell(0).setCellValue("李四");
        row3.createCell(1).setCellValue("20");
        row3.createCell(2).setCellValue("上海");

        XSSFRow row4 = sheet.createRow(3);// 第4行
        row4.createCell(0).setCellValue("王五");
        row4.createCell(1).setCellValue("25");
        row4.createCell(2).setCellValue("深圳");

        // 组织数据后，导出excel
        OutputStream os = new FileOutputStream("D:/itcast.xlsx");
        workbook.write(os);
        os.flush();
        os.close();
        workbook.close();

    }

}
