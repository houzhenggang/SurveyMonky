package com.lmdestiny.surveypark.util;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lmdestiny.surveypark.service.RightService;

public class ExtractAllRightsUtil {
	public static void main(String[] args) throws URISyntaxException, ClassNotFoundException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		RightService rs = (RightService) ac.getBean("rightService");
		ClassLoader loader = ExtractAllRightsUtil.class.getClassLoader();
		URL url = loader.getResource("com/lmdestiny/surveypark/struts2/action");
		File dir = new File(url.toURI());
		File[] files = dir.listFiles();
		String fname = "" ;
		for(File f : files){
			fname = f.getName();
			if(fname.endsWith(".class")
					&& !fname.equals("BaseAction.class")){
				processAction(fname,rs);
			}
		}
		
	}

	/**
	 * �õ�����Action ,�γ�Ȩ��
	 */
	private static void processAction(String fname, RightService rs) throws ClassNotFoundException {
		String pack = "com.lmdestiny.surveypark.struts2.action";
		fname = fname.substring(0, fname.indexOf(".class"));
		String className = pack + "." + fname;
		Class clazz = Class.forName(className);
		Method[] method = clazz.getDeclaredMethods();
		Class retype = null;
		Class[] paratype = null;
		String mname = null;
		String url = null;
		for (Method m : method) {
			retype = m.getReturnType();// ����ֵ����
			mname = m.getName();// ��÷�������
			paratype = m.getParameterTypes();
			if (retype == String.class && !ValidateUtil.isValid(paratype) && Modifier.isPublic(m.getModifiers())) {
				if (mname.equals("execute")) {
					url = "/" + fname;
				} else {
					url = "/" + fname + "_" + mname;
				}
				rs.appendRightByURL(url);
			}
		}
	}

}
