package com.lmdestiny.surveypark.service;

import java.util.List;
import java.util.Set;

import com.lmdestiny.surveypark.model.security.Role;

/**
 *��ɫservice 
 */
public interface RoleService  extends BaseService<Role>{
	/**
	 * �������½�ɫ
	 */
	public void saveOrUpdateRole(Role model, Integer[] ids);

	/**
	 *�����û�û�еĽ�ɫ 
	 */
	public List<Role> findRolesNotInRange(Set<Role> roles);
	/**
	 *�����û����ڵĽ�ɫ 
	 */
	public List<Role> findRolesInRange(Integer[] ownRoleIds);
	
}
