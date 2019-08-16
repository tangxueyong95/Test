package com.day_26.Work_05;

import com.day_26.Work_01.aTest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.List;

public class aTest2 {
    public static void main(String[] args) throws DocumentException {
        Document document = aTest.getDocument();
        List<Element> list = document.selectNodes("//*");
        for (Element element : list) {
            System.out.println(element.getName() + "：" + element.getTextTrim());
        }
    }
}
