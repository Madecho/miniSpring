package com.mini.beans;

import com.mini.core.Resource;
import org.dom4j.Element;

/**
 * 已经解析好了 XML 文件，但解析好的 XML 如何转换成我们需要的 BeanDefinition 呢？
 * 这时 XmlBeanDefinitionReader 就派上用场了
 *
 * loadBeanDefinitions 方法会把解析的 XML 内容转换成 BeanDefinition，并加载到 BeanFactory 中
 */
public class XmlBeanDefinitionReader {
    BeanFactory beanFactory;
    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}