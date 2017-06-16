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
	 * �����¼����
	 * @return
	 */
	public String toLoginPage(){
		return "loginPage";
	}
	/**
	 * ��¼����
	 * @return
	 */
	public String doLogin(){
		return "success";
	}
	
	/**
	 * ��¼У�� ������ validate
	 */
	public void validateDoLogin(){
		User user =userService.validateLoginInfo(model.getEmail() ,DataUtil.md5(model.getPassword()));
		if(user ==null){
			addActionError("email/password error!");
		}else{
			//��ʼ��Ȩ���ܺ�
			int maxPos = rightService.getMaxRightPos();
			user.setRightSum(new long[maxPos+1]);
			//�����û�Ȩ���ܺ�
			user.calculateRightSum();
			//��ʹ�� ��϶�̫�ߣ����������ʵ��SessionAware
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
	//ע��session��Map
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
