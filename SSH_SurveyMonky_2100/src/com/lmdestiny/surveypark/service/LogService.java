package com.lmdestiny.surveypark.service;

import java.util.List;

import com.lmdestiny.surveypark.model.Log;

/**
 * LogService
 */
public interface LogService extends BaseService<Log> {

	/**
	 * 通过表明创建日志表
	 */
	public void createLogTable(String tableName);

	/**
	 * 查询最近指定月份数的日志
	 */
	public List<Log> findNearestLogs(int i);
	
}
