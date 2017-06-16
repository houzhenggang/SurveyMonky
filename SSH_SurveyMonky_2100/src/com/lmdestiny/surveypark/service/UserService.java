package com.lmdestiny.surveypark.service;

import com.lmdestiny.surveypark.model.User;
import com.lmdestiny.surveypark.service.BaseService;

public interface UserService extends BaseService<User> {

	//特殊的业务
	/**
	 * 判断邮箱是否已被注册
	 * @param email
	 * @return
	 */
	boolean isRegisted(String email);
	/**
	 * 登录验证
	 * @return
	 */
	User validateLoginInfo(String email,String password);
	/**
	 *更新角色 
	 */
	void updateAuthorize(User model, Integer[] ownRoleIds);
	
	void clearAuthorize(Integer userId);

}
