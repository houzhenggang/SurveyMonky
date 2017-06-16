package com.lmdestiny.surveypark.service;

import java.util.List;
import java.util.Set;

import com.lmdestiny.surveypark.model.security.Right;

/**
 *Ȩ��service 
 */
public interface RightService  extends BaseService<Right>{
	/**
	 *��������Ȩ�� 
	 */
	public void saveOrUpdateRight(Right r);
	/**
	 *����url׷��Ȩ�� 
	 */
	public void appendRightByURL(String url);
	/**
	 * ��������Ȩ��
	 */
	public void batchUpdateRights(List<Right> allRights); 
	/**
	 * ��ѯ��ָ����Χ�ڵ�Ȩ��
	 */
	public List<Right> findRightsInRange(Integer[] ids);
	/**
	 *��ѯ�����ƶ���Χ�ڵ�Ȩ�� 
	 */
	public List<Right> findRightsNotInRange(Set<Right> rights);
	/**
	 *�õ�Ȩ��λ���ֵ,���ڳ�ʼ��user 
	 */
	public int getMaxRightPos();
}
