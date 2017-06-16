package com.lmdestiny.surveypark.service;

import com.lmdestiny.surveypark.model.statistics.QuestionStatisticsModel;

/**
 *统计服务 
 */
public interface StatisticsService {
	public QuestionStatisticsModel statistics(Integer qid);
	
}
