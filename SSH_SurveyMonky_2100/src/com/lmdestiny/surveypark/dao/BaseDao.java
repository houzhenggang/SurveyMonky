package com.lmdestiny.surveypark.dao;

import java.util.List;

public interface BaseDao<T> {
	//写入
	public void saveEntity(T t);
	public void updateEntity(T t);
	public void deleteEntity(T t);
	public void saveOrUpdateEntity(T t);
	public void batchEntityByHQL(String hql,Object...objects );
	//读取
	public T getEntity(Integer id);
	public T loadEntity(Integer id);
	public List<T> findEntityByHQL(String hql,Object...objects);
	//单值检索
	public Object uniqueResult(String hql,Object...objects);
	//执行原生的sql查询(可以指定是否封装成实体)
	public List executeSQLQuery(Class clazz,String sql,Object...objects);
	//执行原生的sql语句
	public void executeSQL(String sql,Object...objects);
}
