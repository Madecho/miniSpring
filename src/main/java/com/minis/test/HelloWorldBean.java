package com.minis.test;

import com.minis.web.RequestMapping;

import java.util.Date;


public class HelloWorldBean {

//	@RequestMapping("/test4")
//	public String doTest4(CustomDateEditor date) {
//		return "Hello Madecho";
//	}

	@RequestMapping("/test3")
	public String doTest3() {
		return "Hello Madecho";
	}

	@RequestMapping("/test1")
	public String doTest1() {
		return "test 1, hello world!";
	}
	@RequestMapping("/test2")
	public String doTest2() {
		return "test 2, hello world!";
	}
}