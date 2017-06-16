package com.lmdestiny.surveypark.struts2.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lmdestiny.surveypark.model.User;
import com.lmdestiny.surveypark.service.RightService;
import com.lmdestiny.surveypark.service.UserService;
import com.lmdestiny.surveypark.util.DataUtil;
@Controller("loginAction")
@Scope("prototype")
public class LoginAction extends BaseAction<User> implements SessionAware{
	
	private static final long serialVersionUID = -5291728767865922369L;
	
	private UserService userService;
	private RightService rightService;
	
	private Map<String,Object> sessionMap;
	
	/**
	 * 进入登录界面
	 * @return
	 */
	public String toLoginPage(){
		return "loginPage";
	}
	/**
	 * 登录操作
	 * @return
	 */
	public String doLogin(){
		return "success";
	}
	
	/**
	 * 登录校验 拦截器 validate
	 */
	public void validateDoLogin(){
		User user =userService.validateLoginInfo(model.getEmail() ,DataUtil.md5(model.getPassword()));
		if(user ==null){
			addActionError("email/password error!");
		}else{
			//初始化权限总和
			int maxPos = rightService.getMaxRightPos();
			user.setRightSum(new long[maxPos+1]);
			//计算用户权限总和
			user.calculateRightSum();
			//不使用 耦合度太高，解决方案，实现SessionAware
			//ServletActionContext.getRequest().getSession().setAttribute("user", user);
			sessionMap.put("user", user);
		}

	}

	
	
	

	public UserService getUserService() {
		return userService;
	}
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	//注入session的Map
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.sessionMap=arg0;
		
	}
	public RightService getRightService() {
		return rightService;
	}
	@Resource(name="rightService")
	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}
	
	
	
}
