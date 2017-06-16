package com.lmdestiny.surveypark.struts2.interceptor;

import org.apache.struts2.ServletActionContext;

import com.lmdestiny.surveypark.model.User;
import com.lmdestiny.surveypark.struts2.UserAware;
import com.lmdestiny.surveypark.struts2.action.BaseAction;
import com.lmdestiny.surveypark.struts2.action.LoginAction;
import com.lmdestiny.surveypark.struts2.action.RegAction;
import com.lmdestiny.surveypark.util.ValidateUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class RightFilterInterceptor implements Interceptor {

	private static final long serialVersionUID = 4842929954379790157L;

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation arg0) throws Exception {
		BaseAction action = (BaseAction) arg0.getAction();
		ActionProxy proxy = arg0.getProxy();
		String ns = proxy.getNamespace();
		String actionName = proxy.getActionName();
		if(ValidateUtil.hasRight(ns, actionName, ServletActionContext.getRequest(),action)){
			return arg0.invoke();
		}
		return "login" ;
	}

}
