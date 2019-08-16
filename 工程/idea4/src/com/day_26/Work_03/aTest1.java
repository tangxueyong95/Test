package com.day_26.Work_03;

import com.day_26.Work_01.aTest;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.List;

public class aTest1 {
    public static void main(String[] args) throws DocumentException {
        Element root = aTest.getDocument().getRootElement();
        Element element = root.element("书");
        Attribute attribute = element.attribute("出版社");
        System.out.println(attribute.getValue());
    }
    public void test05() throws DocumentException {
        //获取第二本书的出版社属性
        List<Element> elements = aTest.getDocument().getRootElement().elements("书");
        Element element = elements.get(1);

        //获取其"出版社属性"
        String value = element.attributeValue("出版社");//直接获取属性值

        /*Attribute attribute = element.attribute("出版社");//获取属性对象
        String value = attribute.getValue();*/

        System.out.println(value);
    }
    public void test06() throws DocumentException {
        //XPATH中有俩方法:1.selectSingleNode获取单个节点    2.selectNodes获取多个节点
        //什么是节点(Node):标签、属性都叫做节点
        Element element = (Element) aTest.getDocument().selectSingleNode("/书架/书[2]");
        String text = element.attributeValue("出版社");
        System.out.println(text);
    }
}
