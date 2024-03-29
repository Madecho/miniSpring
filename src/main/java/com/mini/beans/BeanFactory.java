package com.mini.beans;


/**
 * 拆出一个基础的容器来，刚才我们反复提到了 BeanFactory 这个词，现在我们正式引入 BeanFactory 这个接口，
 * 先让这个接口拥有两个特性：一是获取一个 Bean（getBean），二是注册一个 BeanDefinition（registerBeanDefinition）
 *
 */

public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    void registerBeanDefinition(BeanDefinition beanDefinition);
}