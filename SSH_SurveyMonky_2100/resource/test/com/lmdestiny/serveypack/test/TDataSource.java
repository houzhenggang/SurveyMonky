package com.lmdestiny.serveypack.test;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TDataSource {

	@Test
	public void getConnection() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		DataSource ds = (DataSource) ac.getBean("dataSource");
		System.out.println(ds);
	}

}
