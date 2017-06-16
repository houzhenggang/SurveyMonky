package com.lmdestiny.surveypark.service;

import java.util.List;

public interface BaseService<T>{
	//写入
	public void saveEntity(T t);
	public void updateEntity(T t);
	public void deleteEntity(T t);
	public void saveOrUpdateEntity(T t);
	public void batchEntityByHQL(String hql,Object...objects );
	//执行原生的sql语句
	public void executeSQL(String sql,Object...objects);
	//读取
	public T getEntity(Integer id);
	public T loadEntity(Integer id);
	public List<T> findEntityByHQL(String hql,Object...objects);
	//单值检索
	public Object uniqueResult(String hql,Object...objects);
	//查询所有实体
	public List<T> findAllEntities();
	public List executeSQLQuery(Class clazz,String sql,Object...objects);
}
