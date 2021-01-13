package com.example.test.util;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Cya
 * Date: 2019/9/25
 * Time: 9:47
 * Description: 根据所填参数和值，生成签名
 */
public class SignMsgUtil {


    public static String generateSignMsg(Object object,String key){
        String tempCompose = compose(object);
        return MD5.encodeSignatureMD5(tempCompose,key);
    }

    public static String generateSignMsg2(String tempStr,String key){
        return MD5.encodeSignatureMD5(tempStr,key);
    }



    public static String compose(Object o){
        return compose0(o.getClass(),o);
    }
    /**
     * 此方法将会返回符合传输协议的字符串 key={param}&key={param}
     * @return
     */
    private static String compose(Class clazz,Object o){
        StringBuilder body = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        String unit = "={%s}&";
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if("signMsg".equals(fieldName)||
                        "serialVersionUID".equals(fieldName)){continue;}
                Object value = field.get(o);
                if(StringUtils.isEmpty(value)){continue;}
                body.append(fieldName).append(String.format(unit,value));
            }
            String src = body.toString();
            String tempStr = src.substring(0,src.length()-1);
            return src.substring(0,src.length()-1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return o.toString();
    }

    private static String compose0(Class clazz, Object o) {
        StringBuilder body = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        String unit = "={%s}&";
        Comparator<Field> fieldComparator = (field1, field2)->{ return field1.getName().compareTo(field2.getName());};
        List<Field> list= Arrays.stream(fields).filter(field -> {
            String fieldName = field.getName();
            if ("signMsg".equals(fieldName) ||
                    "serialVersionUID".equals(fieldName) ||
                    "remoteAddr".equals(fieldName)) {
                return false;
            }else {
                return true;
            }
        }).collect(Collectors.toList());
        list.sort(fieldComparator);
        try {
            for (Field field : list) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(o);
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                body.append(fieldName).append(String.format(unit, value));
            }
            String src = body.toString();
            return src.substring(0, src.length() - 1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return o.toString();
    }

}
