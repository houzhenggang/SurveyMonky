package com.lmdesinty.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
public static void main(String[] args) {
	 ApplicationContext context = new ClassPathXmlApplicationContext("schedules.xml");
	 System.out.print("С���� �����");
	 context.getBean("start");
	 System.out.println("���");
}
}
