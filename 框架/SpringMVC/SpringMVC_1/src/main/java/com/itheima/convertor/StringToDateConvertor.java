package com.itheima.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName StringToDateConvertor
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/16 10:47
 * @Version V1.0
 */

public class StringToDateConvertor implements Converter<String,Date> {

    // 只要表单是字符串，在Controller中定义日期的属性才会执行
    @Nullable
    @Override
    public Date convert(String s) {
        if(s==null){
            return null; // throw new RuntimeException
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
