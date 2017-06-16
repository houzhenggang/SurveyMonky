package com.lmdestiny.surveypark.dao;

import java.util.List;

public interface BaseDao<T> {
	//д��
	public void saveEntity(T t);
	public void updateEntity(T t);
	public void deleteEntity(T t);
	public void saveOrUpdateEntity(T t);
	public void batchEntityByHQL(String hql,Object...objects );
	//��ȡ
	public T getEntity(Integer id);
	public T loadEntity(Integer id);
	public List<T> findEntityByHQL(String hql,Object...objects);
	//��ֵ����
	public Object uniqueResult(String hql,Object...objects);
	//ִ��ԭ����sql��ѯ(����ָ���Ƿ��װ��ʵ��)
	public List executeSQLQuery(Class clazz,String sql,Object...objects);
	//ִ��ԭ����sql���
	public void executeSQL(String sql,Object...objects);
}
