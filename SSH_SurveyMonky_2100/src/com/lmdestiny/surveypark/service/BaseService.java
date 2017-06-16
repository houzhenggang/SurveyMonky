package com.lmdestiny.surveypark.service;

import java.util.List;

public interface BaseService<T>{
	//д��
	public void saveEntity(T t);
	public void updateEntity(T t);
	public void deleteEntity(T t);
	public void saveOrUpdateEntity(T t);
	public void batchEntityByHQL(String hql,Object...objects );
	//ִ��ԭ����sql���
	public void executeSQL(String sql,Object...objects);
	//��ȡ
	public T getEntity(Integer id);
	public T loadEntity(Integer id);
	public List<T> findEntityByHQL(String hql,Object...objects);
	//��ֵ����
	public Object uniqueResult(String hql,Object...objects);
	//��ѯ����ʵ��
	public List<T> findAllEntities();
	public List executeSQLQuery(Class clazz,String sql,Object...objects);
}
