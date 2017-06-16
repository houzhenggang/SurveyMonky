package com.lmdestiny.surveypark.util;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.lmdestiny.surveypark.struts2.action.BaseAction;

/**
 * У�鹤����
 */
public class ValidateUtil {
	
	/**
	 * �ж��ַ�����Ч��
	 */
	public static boolean isValid(String src){
		if(src == null || "".equals(src.trim())){
			return false ;
		}
		return true ;
	}
	
	/**
	 * �жϼ��ϵ���Ч�� 
	 */
	public static boolean isValid(Collection col){
		if(col == null || col.isEmpty()){			
			return false ;
		}
		return true ;
	}
	
	/**
	 * �ж������Ƿ���Ч
	 */
	public static boolean isValid(Object[] arr){
		if(arr == null || arr.length == 0){
			return false ;
		}
		return true ;
	}
	/**
	 *�ж��Ƿ���Ȩ�� 
	 */
	public static boolean hasRight(String namespace,String actionName,HttpServletRequest req,BaseAction action){
		if(!ValidateUtil.isValid(namespace)||"/".equals(namespace)){
			namespace="";
		}
		//�õ��������
		
		return false;
	}
}
