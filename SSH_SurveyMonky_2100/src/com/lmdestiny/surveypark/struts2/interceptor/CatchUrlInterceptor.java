package com.lmdestiny.surveypark.struts2.interceptor;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lmdestiny.surveypark.service.RightService;
import com.lmdestiny.surveypark.util.ValidateUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 *²¶»ñURLÀ¹½ØÆ÷
 */
public class CatchUrlInterceptor implements Interceptor {

	private static final long serialVersionUID = -2965448634229162211L;

	public void destroy() {
	}

	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		ActionProxy proxy = invocation.getProxy();
		//Ãû×Ö¿Õ¼ä
		String ns = proxy.getNamespace();
		//actionName
		String actionName = proxy.getActionName();
		if(!ValidateUtil.isValid(ns)
				||ns.equals("/")){ 
			ns = "" ;
		}
		String url = ns + "/" + actionName ;
		

		ServletContext sc = ServletActionContext.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
		RightService rs = (RightService) ac.getBean("rightService");
		
		rs.appendRightByURL(url);
		return invocation.invoke();
	}

}
