package com.lmdestiny.surveypark.struts2.action;

import java.lang.reflect.ParameterizedType;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 抽象action  专门用于继承
 * @author wangbin
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>,Preparable {

	private static final long serialVersionUID = 1L;
	
	public T model;
	
	public BaseAction(){
		java.lang.reflect.ParameterizedType type=(ParameterizedType) this.getClass().getGenericSuperclass();
		Class clazz = (Class) type.getActualTypeArguments()[0];
		try {
			model = (T)clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
	}

	public T getModel(){
		return model;
	}

}
