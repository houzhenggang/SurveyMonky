package com.lmdestiny.surveypark.service;

import java.util.List;

import com.lmdestiny.surveypark.model.Answer;
import com.lmdestiny.surveypark.model.Page;
import com.lmdestiny.surveypark.model.Question;
import com.lmdestiny.surveypark.model.Survey;
import com.lmdestiny.surveypark.model.User;

public interface SurveyService {

	/**
	 * 返回某一用户的所有调查
	 * @param id
	 * @return
	 */
	public List<Survey> findMySurveys(Integer id);

	/**
	 * 新建调查
	 * 完成page survey的双向连接
	 * @param user
	 * @return
	 */
	public Survey newSurvey(User user);

	/**
	 * 按照id查询Survey
	 * @param sid
	 * @return
	 */
	public Survey getSurver(int sid);
	/*
	 * 解决懒加载
	 */
	public Survey getSurveyWithChildren(int sid);

	/**
	 *更新调查 
	 */
	public void updateSurvey(Survey model);

	/**
	 *编辑页面 
	 */
	public void saveOrUpdate(Page model);

	/*
	 * 修改页面
	 */
	public Page getPage(Integer pid);

	/**
	 *保存/修改问题
	 */
	public void saveOrUpdate(Question model);

	/**
	 * 到达修改界面
	 */
	public Question getQuestion(Integer qid);

	/**
	 *删除问题 
	 */
	public void clearAnswers(int sid);

	/**
	 * 删除页面
	 */
	public void deletePage(Integer pid);

	/**
	 * 删除问题
	 */
	public void deleteQuestion(Integer qid);

	/**
	 * 删除调查
	 */
	public void deleteSurvey(int sid);

	/**
	 * 修改当前调查的状态
	 */
	public void toggleStatus(int sid);

	/**
	 *更新数据库中的字段 
	 */
	public void updatePhotoPath(int sid, String string);

	/**
	 * 查询调查集合
	 */
	public List<Survey> getSurveyWithPages(User user);

	/**
	 *移动页面 
	 */
	public  void moveOrCopyPage(Integer srcPid, Integer targPid, Integer pos);

	/**
	 *查询所有调查 
	 */
	public List<Survey> findAllAvailableSurveys();
	/**
	 *得到第一个页面 
	 */
	public Page getFirstPage(Integer sid);

	/**
	 *得到前一个页面 
	 */
	public Page getPrePage(Integer currPid);

	/**
	 *得到下一个页面 
	 */
	public Page getNextPage(Integer currPid);

	public void saveAnswers(List<Answer> processAnswers);


	





}
