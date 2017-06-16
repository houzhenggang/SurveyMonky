package com.lmdestiny.surveypark.struts2;

import com.lmdestiny.surveypark.model.User;

public interface UserAware {
	//通过注入实现低耦合
	public void setUser(User user);

}
