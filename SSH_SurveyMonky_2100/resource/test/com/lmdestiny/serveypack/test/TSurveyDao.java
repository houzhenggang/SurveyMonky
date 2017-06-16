package com.lmdestiny.serveypack.test;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lmdestiny.surveypark.model.User;
import com.lmdestiny.surveypark.service.UserService;

public class TSurveyDao {
	private static UserService userService;
	
	
	@BeforeClass
	public static void iniUserService(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		userService = (UserService) ac.getBean("userService");
	}

	@Test
	public void test() {
		User  u = new User();
		u.setEmail("1453926032@qq.com");
		u.setName("Íõ±ó");
		u.setNickName("NiHao");
		u.setPassword("123456");
		u.setRegDate(new Date());
		userService.saveEntity(u);
	}

}
