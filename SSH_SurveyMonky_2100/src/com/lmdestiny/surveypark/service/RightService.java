package com.lmdestiny.surveypark.service;

import java.util.List;
import java.util.Set;

import com.lmdestiny.surveypark.model.security.Right;

/**
 *权限service 
 */
public interface RightService  extends BaseService<Right>{
	/**
	 *保存或更新权限 
	 */
	public void saveOrUpdateRight(Right r);
	/**
	 *根据url追加权限 
	 */
	public void appendRightByURL(String url);
	/**
	 * 批量更新权限
	 */
	public void batchUpdateRights(List<Right> allRights); 
	/**
	 * 查询在指定范围内的权限
	 */
	public List<Right> findRightsInRange(Integer[] ids);
	/**
	 *查询不在制定范围内的权限 
	 */
	public List<Right> findRightsNotInRange(Set<Right> rights);
	/**
	 *得到权限位最大值,用于初始化user 
	 */
	public int getMaxRightPos();
}
