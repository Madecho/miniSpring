package com.minis.core;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 *
 * 目前我们的数据来源比较单一，读取的都是 XML 文件配置，但是有了 Resource 这个接口后面我们就可以扩展，
 * 从数据库还有 Web 网络上面拿信息。
 *
 *
 * 这个类就是用来读取XML文件里的信息的
 */

public class ClassPathXmlResource implements Resource{
    Document document;
    Element rootElement;
    Iterator<Element> elementIterator;
    public ClassPathXmlResource(String fileName) {
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
        //将配置文件装载进来，生成一个迭代器，可以用于遍历
        try {
            this.document = saxReader.read(xmlPath);
            this.rootElement = document.getRootElement();
            this.elementIterator = this.rootElement.elementIterator();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }
    public Object next() {
        return this.elementIterator.next();
    }

    @Override
    public void remove() {
    }
}