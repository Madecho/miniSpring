package com.minis.beans;


/**
 * 将管理单例 Bean 的方法规范好
 *
 * 分别对应单例的注册、获取、判断是否存在，以及获取所有的单例 Bean 等操作
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);
    Object getSingleton(String beanName);
    boolean containsSingleton(String beanName);
    String[] getSingletonNames();
}