package com.lmdestiny.surveypark.datasource;
import com.lmdestiny.surveypark.model.Survey;

/**
 * ����
 */
public class SurveyparkToken {
	//
	private static 	ThreadLocal<SurveyparkToken> l = new ThreadLocal<SurveyparkToken>();
	
	private Survey survey ;

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	
	/**
	 * ��ָ�������ƶ���󶨵���ǰ�߳�
	 */
	public static void bindToken(SurveyparkToken token){
		l.set(token);
	}
	
	/**
	 * �����ǰ�̰߳󶨵�����
	 */
	public static void unbindToken(){
		l.remove();
	}
	
	/**
	 * �ӵ�ǰ�̻߳�ð󶨵�����
	 */
	public static SurveyparkToken getCurrentToken(){
		return l.get();
	}
}
