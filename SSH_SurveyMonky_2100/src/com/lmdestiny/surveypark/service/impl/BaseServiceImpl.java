package com.lmdestiny.surveypark.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import com.lmdestiny.surveypark.dao.BaseDao;
import com.lmdestiny.surveypark.service.BaseService;

/**
 * 用于继承
 * @author wangbin
 *
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
	private BaseDao<T> dao;
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseServiceImpl(){
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}
	
	public void saveEntity(T t) {
		dao.saveEntity(t);

	}

	public void updateEntity(T t) {
		dao.updateEntity(t);
	}

	public void deleteEntity(T t) {
		dao.deleteEntity(t);
	}

	public void saveOrUpdateEntity(T t) {
		dao.saveOrUpdateEntity(t);
	}

	public void batchEntityByHQL(String hql, Object... objects) {
		dao.batchEntityByHQL(hql, objects);
	}

	public T getEntity(Integer id) {
		return dao.getEntity(id);
	}

	public T loadEntity(Integer id) {
		return dao.loadEntity(id);
	}

	public List<T> findEntityByHQL(String hql, Object... objects) {
		return dao.findEntityByHQL(hql, objects);
	}
	
	public Object uniqueResult(String hql,Object...objects){
		return dao.uniqueResult(hql, objects);
	}
	
	public List<T> findAllEntities(){
		String hql ="from "+clazz.getSimpleName();
		return this.findEntityByHQL(hql);
	}
	//执行sql原生查询
	public List executeSQLQuery(Class clazz,String sql,Object...objects){
		return dao.executeSQLQuery(clazz,sql, objects);
	}
	//执行原生的sql语句
	public void executeSQL(String sql,Object...objects){
		dao.executeSQL(sql, objects);
	}
	public BaseDao<T> getDao() {
		return dao;
	}
	@Resource
	public void setDao(BaseDao<T> dao) {
		this.dao = dao;
	}
	

}
