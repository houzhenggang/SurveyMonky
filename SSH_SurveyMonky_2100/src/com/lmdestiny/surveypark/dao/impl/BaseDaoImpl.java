package com.lmdestiny.surveypark.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;

import com.lmdestiny.surveypark.dao.BaseDao;

@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	@Resource
	private SessionFactory sf;

	public void saveEntity(Object t) {
		sf.getCurrentSession().save(t);
	}

	public void updateEntity(Object t) {
		sf.getCurrentSession().update(t);

	}

	public void deleteEntity(Object t) {
		sf.getCurrentSession().delete(t);
	}

	public void saveOrUpdateEntity(Object t) {
		sf.getCurrentSession().saveOrUpdate(t);
	}

	/**
	 * 按照HQL语句批量更新
	 */
	public void batchEntityByHQL(String hql, Object... objects) {
		Query q = sf.getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		q.executeUpdate();

	}

	/**
	 * 读操作
	 */
	private Class<T> clazz;

	public BaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	public T getEntity(Integer id) {
		// 这是不行的，使用反射解决问题
		// return (T)sf.getCurrentSession().get(T, id);
		return (T) sf.getCurrentSession().get(clazz, id);
	}

	public T loadEntity(Integer id) {
		return (T) sf.getCurrentSession().load(clazz, id);
	}

	public List findEntityByHQL(String hql, Object... objects) {
		Query q = sf.getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		return q.list();
	}

	// 单值检索
	public Object uniqueResult(String hql, Object... objects) {
		Query q = sf.getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		return q.uniqueResult();
	}

	// 执行原生的sql查询
	public List executeSQLQuery(Class clazz, String sql, Object... objects) {
		SQLQuery q = sf.getCurrentSession().createSQLQuery(sql);
		// 添加实体类
		if (clazz != null) {
			q.addEntity(clazz);
		}
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		return q.list();
	}
	
	public void executeSQL(String sql,Object...objects){
		SQLQuery q = sf.getCurrentSession().createSQLQuery(sql);
		for(int i = 0 ; i < objects.length ; i ++){
			q.setParameter(i, objects[i]);
		}
		q.executeUpdate();
	}


}
