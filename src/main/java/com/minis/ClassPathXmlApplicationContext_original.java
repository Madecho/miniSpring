package com.minis;

import com.minis.beans.BeanDefinition;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 这时的 ClassPathXmlApplicationContext 承担了太多的功能，这并不符合我们常说的对象单一功能的原则。
 * 因此，我们需要做的优化扩展工作也就呼之欲出了：分解这个类，主要工作就是两个部分，一是提出一个最基础
 * 的核心容器，二是把 XML 这些外部配置信息的访问单独剥离出去，现在我们只有 XML 这一种方式，但是之后还
 * 有可能配置到 Web 或数据库文件里，拆解出去之后也便于扩展。
 */

/**
 * 从最初的简单ApplicationContext拆解成后面的复杂ApplicationContext，我理解起来还是有困难的，
 * 努力理解如下，大神勿喷：1 readxml方法从资源文件读取内容并存入beanDefinitions，
 * 这件事情有两个地方不确定，资源的来源不同、资源的格式不同，抽象的Resource的接口，它的不同子类从不同的来源读取，
 * 但是最终都是以Resource接口的形式提供给外部访问的，这样解决了第一个不确定来源的问题；但是resource接口中被迭代的object
 * 又是根据不同格式不同而不同的，element只是xml格式的，所以又定义了BeanDefinitionReader接口，它的不同子类可以读取不同
 * 格式的资源来形成beanDefinition 。 2 . instanceBeans方法取消了 。  3. getBean方法功能增强了，不仅是获得bean，
 * 对于未创建的bean还要创建bean  4 新的applicationContext负责组装，可以根据它的名字来体现它的组装功能，
 * 例如ClassPathXmlApplicationContext  它组装的Resource的实现类是ClassPathXmlResource  ，然后因为是xml的，
 * 所以需要BeanDefinitionReader的实现类XmlBeanDefinitionReader来读取并注册进beanFactory，同时ApplicationContext也
 * 提供了getBean底层调用beanfactory的实现，提供了registerBeanDefinition  来向底层的beanFactory注册bean。
 * 5 beanFactory 提供了registerBeanDefinition和getBean接口，这样无论是applicationContext还是beanDefinitionReader都
 * 可以向它注册beanDefinition，只要向它注册了，就可以调用它的getBean方法，我一直很纠结为什么不是beanfactory调用不同的
 * beanDefinitionReader，写完这些，好像有点理解了，这样beanfactory就很专注自己的getBean方法，别的组件要怎么注入，
 * 它都不管了。
 */

public class ClassPathXmlApplicationContext_original {
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();

    //构造器获取外部配置，解析出Bean的定义，形成内存映像
    public ClassPathXmlApplicationContext_original(String fileName) {
        this.readXml(fileName);
        this.instanceBeans();
    }

    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();

        URL xmlPath =
                this.getClass().getClassLoader().getResource(fileName);
        Document document = null;
        try {
            document = saxReader.read(xmlPath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        //对配置文件中的每一个<bean>，进行处理
        for (Element element : (List<Element>) rootElement.elements()) {
            //获取Bean的基本信息
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID,
                    beanClassName);
            //将Bean的定义存放到beanDefinitions
            beanDefinitions.add(beanDefinition);
        }

    }

    //利用反射创建Bean实例，并存储在singletons中
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                singletons.put(beanDefinition.getId(),
                        Class.forName(beanDefinition.getClassName()).newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //这是对外的一个方法，让外部程序从容器中获取Bean实例，会逐步演化成核心方法
    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }
}