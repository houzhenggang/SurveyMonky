package com.lmdestiny.serveypack.test;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lmdestiny.surveypark.model.User;
import com.lmdestiny.surveypark.service.StatisticsService;
import com.lmdestiny.surveypark.service.UserService;

public class TgetSurvey {
	private static StatisticsService ss;
	
	
	@BeforeClass
	public static void iniUserService(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		ss = (StatisticsService) ac.getBean("statisticsService");
	}

	@Test
	public void test() {
		ss.statistics(2);
	}

}
