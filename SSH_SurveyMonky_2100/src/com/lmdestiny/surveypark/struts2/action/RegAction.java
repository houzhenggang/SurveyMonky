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
		//������
		if(model.getEmail() ==null || "".equals(model.getEmail()))
			addFieldError("email", "���䲻��Ϊ��");
		if(model.getPassword()==null || "".equals(model.getPassword()))
			addFieldError("password", "���벻��Ϊ��");
		if(model.getNickName()==null || "".equals(model.getNickName()))
			addFieldError("nickName", "�ǳƲ���Ϊ��");
		if(hasErrors())
			return;
		//���벻һ��
		if(!model.getPassword().equals(confirmPassword)){
			addFieldError("password", "�������벻һ��");
			return;
		}

		//������ռ��
		if(userService.isRegisted(model.getEmail())){
			addFieldError("email", "�����ѱ�ע�ᣬ����������");
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
