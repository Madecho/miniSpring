package com.mini.context;

import com.mini.beans.BeanDefinition;
import com.mini.beans.BeanFactory;
import com.mini.beans.BeansException;
import com.mini.beans.SimpleBeanFactory;
import com.mini.beans.XmlBeanDefinitionReader;
import com.mini.core.ClassPathXmlResource;
import com.mini.core.Resource;

/**
 * 当前的 ClassPathXmlApplicationContext 在实例化的过程中做了三件事。
 * 解析 XML 文件中的内容。
 * 加载解析的内容，构建 BeanDefinition。
 * 读取 BeanDefinition 的配置信息，实例化 Bean，然后把它注入到 BeanFactory 容器中。
 */

public class ClassPathXmlApplicationContext implements BeanFactory {
    BeanFactory beanFactory;

    //context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        BeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        /**
         *reader.loadBeanDefinitions(resource) 就是去把resource里的东西
         * 注册BeanDefinition到BeanFactory中
         */
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    //context再对外提供一个getBean，底下就是调用的BeanFactory对应的方法
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    //  这个方法目前还没调用过，为什么还要呢？ 仅仅因为是implements BeanFactory吗？
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
