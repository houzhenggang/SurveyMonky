package com.lmdestiny.surveypark.struts2.action;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lmdestiny.surveypark.model.User;
import com.lmdestiny.surveypark.service.UserService;
import com.lmdestiny.surveypark.util.DataUtil;
@Controller("regAction")
@Scope("prototype")
public class RegAction extends BaseAction<User> {
	
	private static final long serialVersionUID = -7091919822723103518L;

	private UserService userService;
	
	private String confirmPassword; 
	
	@SkipValidation
	public String toRegPage(){
		return "regPage";
	}
	public String doReg(){
		model.setPassword(DataUtil.md5(model.getPassword()));
		userService.saveEntity(model);
		return SUCCESS;
	} 
	@Override
	public void validate(){
		//必填项
		if(model.getEmail() ==null || "".equals(model.getEmail()))
			addFieldError("email", "邮箱不能为空");
		if(model.getPassword()==null || "".equals(model.getPassword()))
			addFieldError("password", "密码不能为空");
		if(model.getNickName()==null || "".equals(model.getNickName()))
			addFieldError("nickName", "昵称不能为空");
		if(hasErrors())
			return;
		//密码不一致
		if(!model.getPassword().equals(confirmPassword)){
			addFieldError("password", "两次密码不一致");
			return;
		}

		//邮箱已占用
		if(userService.isRegisted(model.getEmail())){
			addFieldError("email", "邮箱已被注册，请重新输入");
		}
		
	}
	
	
	
	
	
	
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public UserService getUserService() {
		return userService;
	}
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	} 

}
