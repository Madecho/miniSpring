package com.mini.test;


import com.mini.beans.BeansException;
import com.mini.context.ClassPathXmlApplicationContext;

public class Test1 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new
                ClassPathXmlApplicationContext("beans.xml");
        AService aService = null;
        try {
            aService = (AService) ctx.getBean("aservice");
        } catch (BeansException e) {
            e.printStackTrace();
        }
        aService.sayHello();
    }
} 