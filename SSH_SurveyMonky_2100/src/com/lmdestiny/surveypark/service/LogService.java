package com.lmdestiny.surveypark.service;

import java.util.List;

import com.lmdestiny.surveypark.model.Log;

/**
 * LogService
 */
public interface LogService extends BaseService<Log> {

	/**
	 * ͨ������������־��
	 */
	public void createLogTable(String tableName);

	/**
	 * ��ѯ���ָ���·�������־
	 */
	public List<Log> findNearestLogs(int i);
	
}
