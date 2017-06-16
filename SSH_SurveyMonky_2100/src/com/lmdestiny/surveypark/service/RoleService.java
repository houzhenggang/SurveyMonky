package com.lmdestiny.surveypark.service;

import java.util.List;
import java.util.Set;

import com.lmdestiny.surveypark.model.security.Role;

/**
 *角色service 
 */
public interface RoleService  extends BaseService<Role>{
	/**
	 * 保存或更新角色
	 */
	public void saveOrUpdateRole(Role model, Integer[] ids);

	/**
	 *发现用户没有的角色 
	 */
	public List<Role> findRolesNotInRange(Set<Role> roles);
	/**
	 *发现用户存在的角色 
	 */
	public List<Role> findRolesInRange(Integer[] ownRoleIds);
	
}
