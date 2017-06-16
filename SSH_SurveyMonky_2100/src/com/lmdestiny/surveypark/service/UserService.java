package com.lmdestiny.surveypark.service;

import com.lmdestiny.surveypark.model.User;
import com.lmdestiny.surveypark.service.BaseService;

public interface UserService extends BaseService<User> {

	//�����ҵ��
	/**
	 * �ж������Ƿ��ѱ�ע��
	 * @param email
	 * @return
	 */
	boolean isRegisted(String email);
	/**
	 * ��¼��֤
	 * @return
	 */
	User validateLoginInfo(String email,String password);
	/**
	 *���½�ɫ 
	 */
	void updateAuthorize(User model, Integer[] ownRoleIds);
	
	void clearAuthorize(Integer userId);

}
