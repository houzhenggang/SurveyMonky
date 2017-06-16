package com.lmdestiny.surveypark.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lmdestiny.surveypark.dao.BaseDao;
import com.lmdestiny.surveypark.model.Answer;
import com.lmdestiny.surveypark.model.Page;
import com.lmdestiny.surveypark.model.Question;
import com.lmdestiny.surveypark.model.Survey;
import com.lmdestiny.surveypark.model.User;
import com.lmdestiny.surveypark.service.SurveyService;
import com.lmdestiny.surveypark.util.DataUtil;

@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

	@Resource(name = "surveyDao")
	private BaseDao<Survey> surveyDao;
	@Resource(name = "pageDao")
	private BaseDao<Page> pageDao;
	@Resource(name = "questionDao")
	private BaseDao<Question> questionDao;
	@Resource(name = "answerDao")
	private BaseDao<Answer> answerDao;

	/**
	 * ����ĳһ�û������е���
	 * 
	 * @param id
	 * @return
	 */
	public List<Survey> findMySurveys(Integer id) {
		String hql = "from Survey s where s.user.id =?";
		return surveyDao.findEntityByHQL(hql, id);
	}

	/**
	 * �½����� ���page survey��˫������
	 * 
	 * @param user
	 * @return
	 */
	public Survey newSurvey(User user) {
		Page p = new Page();
		Survey s = new Survey();
		s.setUser(user);
		p.setSurvey(s);
		s.getPages().add(p);
		surveyDao.saveEntity(s);
		pageDao.saveEntity(p);
		return s;

	}

	public Survey getSurver(int sid) {
		// TODO Auto-generated method stub
		return surveyDao.getEntity(sid);
	}

	public Survey getSurveyWithChildren(int sid) {
		Survey survey = this.getSurver(sid);
		for (Page p : survey.getPages()) {
			p.getQuestions().size();
		}
		return survey;
	}

	/*
	 * ���µ���
	 */
	public void updateSurvey(Survey model) {
		surveyDao.updateEntity(model);
	}

	@Override
	public void saveOrUpdate(Page model) {
		pageDao.saveOrUpdateEntity(model);
	}

	/**
	 * ���Ҫ�޸ĵ�ҳ��
	 */
	@Override
	public Page getPage(Integer pid) {
		return pageDao.getEntity(pid);
	}

	/**
	 * ����/�޸�����
	 */
	public void saveOrUpdate(Question model) {
		questionDao.saveOrUpdateEntity(model);
	}

	/**
	 * �õ�����ʵ��,����
	 */
	public Question getQuestion(Integer qid) {
		return questionDao.getEntity(qid);
	}

	/**
	 * ɾ��ҳ��
	 */
	public void deletePage(Integer pid) {
		// delete answer
		String hql = "delete from Answer a where a.questionId in(select q.id from Question q where q.page.id =?)";
		answerDao.batchEntityByHQL(hql, pid);
		// delete question
		hql = "delete from Question q where q.page.id =?";
		questionDao.batchEntityByHQL(hql, pid);
		// delete page
		hql = "delete from Page p where p.id=?";
		pageDao.batchEntityByHQL(hql, pid);
	}

	/**
	 * ɾ����
	 */
	public void clearAnswers(int sid) {
		String hql = "delete from Answer a where a.surveyId=?";
		answerDao.batchEntityByHQL(hql, sid);
	}

	/**
	 * ɾ������
	 */
	public void deleteQuestion(Integer qid) {
		String hql = "delete from Question q where q.id =?";
		questionDao.batchEntityByHQL(hql, qid);
	}

	/**
	 * ɾ������
	 */
	public void deleteSurvey(int sid) {
		// delete answer
		String hql = "delete from Answer a where a.surveyId=?";
		answerDao.batchEntityByHQL(hql, sid);
		// delete question
		// hibernate ��д����ʱ�������������ϵ�����
		// hql = "delete from question q where q.page.survey.id=?"
		hql = "delete from Question q where q.page.id in(select p.id from Page p where p.survey.id=?)";
		questionDao.batchEntityByHQL(hql, sid);
		// delete Page
		hql = "delete from Page p where p.survey.id=?";
		pageDao.batchEntityByHQL(hql, sid);
		// delete Survey
		hql = "delete from Survey s where s.id=?";
		surveyDao.batchEntityByHQL(hql, sid);
	}

	/**
	 * �޸ĵ�ǰ�����״̬
	 */
	public void toggleStatus(int sid) {
		Survey s = this.getSurver(sid);
		String hql = "update Survey s set s.closed =? where s.id =?";
		surveyDao.batchEntityByHQL(hql, !s.isClosed(), sid);
	}

	/**
	 * �������ݿ��е��ֶ�
	 */
	public void updatePhotoPath(int sid, String string) {
		String hql = "update Survey s set s.logoPhotoPath=? where s.id=?";
		System.out.println(hql);
		surveyDao.batchEntityByHQL(hql, string, sid);
	}

	/**
	 * ��ѯ���鼯��
	 */
	public List<Survey> getSurveyWithPages(User user) {
		String hql = "from Survey s where s.user.id =?";
		List<Survey> surveys = surveyDao.findEntityByHQL(hql, user.getId());
		surveys.stream().forEach((Survey s) -> {
			s.getPages().size();
		});
		System.out.println(surveys);
		return surveys;
	}

	/**
	 * �ƶ�����ҳ
	 */
	public void moveOrCopyPage(Integer srcPid, Integer targPid, Integer pos) {
		Page srcPage = this.getPage(srcPid);
		Survey srcSurvey = srcPage.getSurvey();
		Page targPage = this.getPage(targPid);
		Survey targSurvey = targPage.getSurvey();
		if (srcSurvey.getId() == targSurvey.getId()) {
			setOrderno(srcPage, targPage, pos);
		} else {
			// ǿ�г�ʼ�����⼯��,������ȸ���ҳ�����û�����⼯��
			srcPage.getQuestions().size();
			System.out.println(srcPage + "***********************");
			// ��ȸ���
			Page copyPage = (Page) DataUtil.deeplyCopy(srcPage);
			copyPage.setSurvey(targSurvey);
			// ����ҳ��
			pageDao.saveEntity(copyPage);
			// jdk 1.8 lambda
			copyPage.getQuestions().stream().forEach((Question q) -> {
				questionDao.saveEntity(q);
			});
			setOrderno(copyPage, targPage, pos);
		}

	}

	private void setOrderno(Page srcPage, Page targPage, Integer pos) {
		System.out.println(pos);
		// ֮ǰ
		if (pos == 0) {
			// �ж�Ŀ��ҳ�ǲ�����ҳ
			if (isFirstPage(targPage)) {
				srcPage.setOrderno(targPage.getOrderno() - 0.01f);
			} else {
				Page prePage = getPrepage(targPage);
				srcPage.setOrderno((targPage.getOrderno() + prePage.getOrderno()) / 2);
			}
		} else {
			// ֮��
			if (isLastPage(targPage)) {
				srcPage.setOrderno(targPage.getOrderno() + 0.01f);
			} else {
				Page nextPage = getNextPage(targPage);
				srcPage.setOrderno((targPage.getOrderno() + nextPage.getOrderno()) / 2);
			}

		}

	}

	private Page getNextPage(Page targPage) {
		String hql = "from Page p where p.survey.id=? and p.orderno > ? order by p.orderno asc";
		List<Page> lists = pageDao.findEntityByHQL(hql, targPage.getSurvey().getId(), targPage.getOrderno());
		return lists.get(0);
	}

	private boolean isLastPage(Page targPage) {
		String hql = "select count(*) from Page p where p.survey.id=? and p.orderno >?";
		Long count = (Long) pageDao.uniqueResult(hql, targPage.getSurvey().getId(), targPage.getOrderno());
		return count == 0;
	}

	private boolean isFirstPage(Page targPage) {
		String hql = "select count(*) from Page p where p.survey.id=? and p.orderno < ?";
		Long count = (Long) pageDao.uniqueResult(hql, targPage.getSurvey().getId(), targPage.getOrderno());
		return count == 0;
	}

	private Page getPrepage(Page targPage) {
		String hql = "from Page p where p.survey.id=? and p.orderno <? order by p.orderno desc";
		List<Page> lists = pageDao.findEntityByHQL(hql, targPage.getSurvey().getId(), targPage.getOrderno());
		return lists.get(0);
	}

	/**
	 * ��ѯ���е���
	 */
	public List<Survey> findAllAvailableSurveys() {
		String hql = "from Survey s where s.closed=?";
		return surveyDao.findEntityByHQL(hql, false);
	}

	/**
	 * �õ���һ��ҳ��
	 */
	public Page getFirstPage(Integer sid) {
		String hql = "from Page p where p.survey.id=? order by p.orderno asc";
		List<Page> list = pageDao.findEntityByHQL(hql, sid);
		Page p = list.get(0);
		p.getQuestions().size();
		p.getSurvey().getTitle();
		return p;
	}

	/**
	 * �õ�ǰһ��ҳ��
	 */
	public Page getPrePage(Integer currPid) {
		Page p = this.getPage(currPid);
		p = this.getPrepage(p);
		p.getQuestions().size();
		return p;
	}

	/**
	 * �õ���һ��ҳ��
	 */
	public Page getNextPage(Integer currPid) {
		Page p = this.getPage(currPid);
		p = this.getNextPage(p);
		p.getQuestions().size();
		return p;
	}

	@Override
	public void saveAnswers(List<Answer> list) {
		Date date = new Date();
		String uuid = UUID.randomUUID().toString();
		for (Answer a : list) {
			a.setUuid(uuid);
			a.setAnswerTime(date);
			answerDao.saveEntity(a);
		}
	}

}
