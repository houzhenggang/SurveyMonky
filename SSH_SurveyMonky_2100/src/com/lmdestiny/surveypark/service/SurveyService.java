package com.lmdestiny.surveypark.service;

import java.util.List;

import com.lmdestiny.surveypark.model.Answer;
import com.lmdestiny.surveypark.model.Page;
import com.lmdestiny.surveypark.model.Question;
import com.lmdestiny.surveypark.model.Survey;
import com.lmdestiny.surveypark.model.User;

public interface SurveyService {

	/**
	 * ����ĳһ�û������е���
	 * @param id
	 * @return
	 */
	public List<Survey> findMySurveys(Integer id);

	/**
	 * �½�����
	 * ���page survey��˫������
	 * @param user
	 * @return
	 */
	public Survey newSurvey(User user);

	/**
	 * ����id��ѯSurvey
	 * @param sid
	 * @return
	 */
	public Survey getSurver(int sid);
	/*
	 * ���������
	 */
	public Survey getSurveyWithChildren(int sid);

	/**
	 *���µ��� 
	 */
	public void updateSurvey(Survey model);

	/**
	 *�༭ҳ�� 
	 */
	public void saveOrUpdate(Page model);

	/*
	 * �޸�ҳ��
	 */
	public Page getPage(Integer pid);

	/**
	 *����/�޸�����
	 */
	public void saveOrUpdate(Question model);

	/**
	 * �����޸Ľ���
	 */
	public Question getQuestion(Integer qid);

	/**
	 *ɾ������ 
	 */
	public void clearAnswers(int sid);

	/**
	 * ɾ��ҳ��
	 */
	public void deletePage(Integer pid);

	/**
	 * ɾ������
	 */
	public void deleteQuestion(Integer qid);

	/**
	 * ɾ������
	 */
	public void deleteSurvey(int sid);

	/**
	 * �޸ĵ�ǰ�����״̬
	 */
	public void toggleStatus(int sid);

	/**
	 *�������ݿ��е��ֶ� 
	 */
	public void updatePhotoPath(int sid, String string);

	/**
	 * ��ѯ���鼯��
	 */
	public List<Survey> getSurveyWithPages(User user);

	/**
	 *�ƶ�ҳ�� 
	 */
	public  void moveOrCopyPage(Integer srcPid, Integer targPid, Integer pos);

	/**
	 *��ѯ���е��� 
	 */
	public List<Survey> findAllAvailableSurveys();
	/**
	 *�õ���һ��ҳ�� 
	 */
	public Page getFirstPage(Integer sid);

	/**
	 *�õ�ǰһ��ҳ�� 
	 */
	public Page getPrePage(Integer currPid);

	/**
	 *�õ���һ��ҳ�� 
	 */
	public Page getNextPage(Integer currPid);

	public void saveAnswers(List<Answer> processAnswers);


	





}
