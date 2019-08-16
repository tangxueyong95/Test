package com.day_26.Work_01;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;

public class aTest {
    public static void main(String[] args) throws DocumentException {
        Element root = getDocument().getRootElement();
        /*List<Element> elements = root.elements();
        for (Element element : elements) {
            String name = element.getName();
            System.out.println(name);
            List<Element> elements1= element.elements();
            for (Element element1 : elements1) {
                System.out.println(element1.getName()+"："+element1.getTextTrim());
            }
        }*/
        printXML(root);
    }

    public static void printXML(Element root) {
        Iterator<Element> iterator = root.elementIterator();
        while (iterator.hasNext()) {
            Element next = iterator.next();
            System.out.println(next.getName());
            Iterator<Element> iterator1 = next.elementIterator();
            while (iterator1.hasNext()) {
                Element next1 = iterator1.next();
                System.out.println(next1.getName() + "：" + next1.getTextTrim());
            }
        }
    }

    public static Document getDocument() throws DocumentException {
        InputStream is = aTest.class.getClassLoader().getResourceAsStream("a.xml");
        SAXReader reader = new SAXReader();
        Document read = reader.read(is);
        return read;
    }
}

