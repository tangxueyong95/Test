package com.itheima.travel.factory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * 包名:com.itheima.travel.factory
 * 作者:Leevi
 * 日期2019-05-28  09:20
 */
public class BeanFactory {
    private static Document document;
    static {
        //1.根据id属性查找对应的bean标签
        SAXReader reader = new SAXReader();
        //2.将配置文件转换成流
        InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        try {
            document = reader.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取配置文件中的数据，并且根据类的全限定名创建对象
     * @param id
     * @return
     */
    public static Object getBean(String id){
        //获取id属性对应的bean标签
        Element element = (Element) document.selectSingleNode("//bean[@id='" + id + "']");
        //获取element的class属性的值
        String className = element.attributeValue("class");
        //使用反射创建对象
        try {
            Class clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
