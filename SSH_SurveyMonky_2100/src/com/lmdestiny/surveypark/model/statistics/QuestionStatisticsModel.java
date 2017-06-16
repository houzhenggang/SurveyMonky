package com.lmdestiny.surveypark.model.statistics;

import java.util.ArrayList;
import java.util.List;

import com.lmdestiny.surveypark.model.Question;

/**
 *问题统计模型 
 */
public class QuestionStatisticsModel {
	private Question question;
	//回答该问题的人数
	private int count;
	private List<OptionStatisticsModel> osms  = new ArrayList<OptionStatisticsModel>();
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<OptionStatisticsModel> getOsms() {
		return osms;
	}
	public void setOsms(List<OptionStatisticsModel> osms) {
		this.osms = osms;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
}
