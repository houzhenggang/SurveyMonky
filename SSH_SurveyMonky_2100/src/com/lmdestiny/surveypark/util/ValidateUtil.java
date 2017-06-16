package com.lmdestiny.surveypark.util;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.lmdestiny.surveypark.struts2.action.BaseAction;

/**
 * 校验工具类
 */
public class ValidateUtil {
	
	/**
	 * 判断字符串有效性
	 */
	public static boolean isValid(String src){
		if(src == null || "".equals(src.trim())){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 判断集合的有效性 
	 */
	public static boolean isValid(Collection col){
		if(col == null || col.isEmpty()){			
			return false ;
		}
		return true ;
	}
	
	/**
	 * 判断数组是否有效
	 */
	public static boolean isValid(Object[] arr){
		if(arr == null || arr.length == 0){
			return false ;
		}
		return true ;
	}
	/**
	 *判断是否有权限 
	 */
	public static boolean hasRight(String namespace,String actionName,HttpServletRequest req,BaseAction action){
		if(!ValidateUtil.isValid(namespace)||"/".equals(namespace)){
			namespace="";
		}
		//得到请求参数
		
		return false;
	}
}
