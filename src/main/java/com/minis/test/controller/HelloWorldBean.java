package com.minis.test.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.web.bind.annotation.RequestMapping;
import com.minis.web.bind.annotation.ResponseBody;
import com.minis.web.servlet.ModelAndView;
import com.minis.test.entity.User;
import com.minis.test.service.BaseService;


public class HelloWorldBean {

    /**
     * 在 MVC 中，“Bean”通常指代模型对象。模型对象是业务逻辑层的核心，用于实现数据访问和业务逻辑处理等功能。
     * 在 MVC 中，模型对象通常是由控制器（Controller）创建并向视图（View）传递的
     * 从 MVC 中可以访问到 IoC 容器中的 Bean。
     */

    @Autowired
    BaseService baseservice;

    @RequestMapping("/test2")
    public void doTest2(HttpServletRequest request, HttpServletResponse response) {
        String str = "test 2, hello world!";
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @RequestMapping("/test5")
    public ModelAndView doTest5(User user) {
        ModelAndView mav = new ModelAndView("test","msg",user.getName());
        return mav;
    }
    @RequestMapping("/test6")
    public String doTest6(User user) {
        return "error";
    }

    @RequestMapping("/test7")
    @ResponseBody
    // 测试 ： http://localhost:8080/test7?name=yourname&id=2&birthday=2023-05-16
    // 把 Web 请求的传入参数，自动地从文本转换成对象，实现数据绑定功能
    public User doTest7(User user) {
        System.out.println(user.getBirthday());
        user.setName(user.getName() + "---");
        //user.setBirthday(new Date());
        return user;
    }
}