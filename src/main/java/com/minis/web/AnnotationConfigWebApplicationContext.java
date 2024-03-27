package com.minis.web;


import javax.servlet.ServletContext;

import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.web.context.WebApplicationContext;

/**
 * 该类其实质就是我们 IoC 容器中的 ClassPathXmlApplicationContext，只是在此基础上增加了
 * servletContext 的属性，这样就成了一个适用于 Web 场景的上下文
 */

public class AnnotationConfigWebApplicationContext 
					extends ClassPathXmlApplicationContext implements WebApplicationContext {
	private ServletContext servletContext;
	
	public AnnotationConfigWebApplicationContext(String fileName) {
		super(fileName);
	}

	@Override
	public ServletContext getServletContext() {
		return this.servletContext;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}