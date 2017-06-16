package com.lmdestiny.surveypark.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Synchronization;

/**
 * ҳ����
 * @author wangbin
 *
 */
public class Page implements Serializable{

	private static final long serialVersionUID = 3232636002964741127L;
	
	private Integer id;
	private String title;
	private String description;
	//��page��survey֮��Ķ��һ������ϵ
	private transient Survey survey;
	//��page��Question֮���һ�Զ������ϵ
	private Set<Question> questions = new HashSet<Question>();
	//ҳ��
	private float orderno;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
		if(id!=null){
			this.orderno =id;
		}
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Survey getSurvey() {
		return survey;
	}
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	public Set<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}
	public float getOrderno() {
		return orderno;
	}
	public void setOrderno(float orderno) {
		this.orderno = orderno;
	}

}
