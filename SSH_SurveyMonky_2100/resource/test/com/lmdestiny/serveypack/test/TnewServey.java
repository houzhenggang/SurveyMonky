package com.lmdestiny.serveypack.test;


import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lmdestiny.surveypark.model.User;
import com.lmdestiny.surveypark.service.SurveyService;

public class TnewServey {
	private static SurveyService surveyService;
	
	
	@BeforeClass
	public static void iniNewServey(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		surveyService = (SurveyService) ac.getBean("surveyService");
	}

	@Test
	public void test() {
		User u = new User();
		u.setId(1);
		surveyService.newSurvey(u);
	}
	
	@Test
	
	public void getSurvey() throws Exception{
		surveyService.getSurver(18);
	}

}
