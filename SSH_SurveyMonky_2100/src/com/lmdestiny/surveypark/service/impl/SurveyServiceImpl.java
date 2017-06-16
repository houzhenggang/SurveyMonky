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
	 * 返回某一用户的所有调查
	 * 
	 * @param id
	 * @return
	 */
	public List<Survey> findMySurveys(Integer id) {
		String hql = "from Survey s where s.user.id =?";
		return surveyDao.findEntityByHQL(hql, id);
	}

	/**
	 * 新建调查 完成page survey的双向连接
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
	 * 更新调查
	 */
	public void updateSurvey(Survey model) {
		surveyDao.updateEntity(model);
	}

	@Override
	public void saveOrUpdate(Page model) {
		pageDao.saveOrUpdateEntity(model);
	}

	/**
	 * 获得要修改的页面
	 */
	@Override
	public Page getPage(Integer pid) {
		return pageDao.getEntity(pid);
	}

	/**
	 * 保存/修改问题
	 */
	public void saveOrUpdate(Question model) {
		questionDao.saveOrUpdateEntity(model);
	}

	/**
	 * 得到问题实体,回显
	 */
	public Question getQuestion(Integer qid) {
		return questionDao.getEntity(qid);
	}

	/**
	 * 删除页面
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
	 * 删除答案
	 */
	public void clearAnswers(int sid) {
		String hql = "delete from Answer a where a.surveyId=?";
		answerDao.batchEntityByHQL(hql, sid);
	}

	/**
	 * 删除问题
	 */
	public void deleteQuestion(Integer qid) {
		String hql = "delete from Question q where q.id =?";
		questionDao.batchEntityByHQL(hql, qid);
	}

	/**
	 * 删除调查
	 */
	public void deleteSurvey(int sid) {
		// delete answer
		String hql = "delete from Answer a where a.surveyId=?";
		answerDao.batchEntityByHQL(hql, sid);
		// delete question
		// hibernate 在写操作时不允许两级以上的连接
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
	 * 修改当前调查的状态
	 */
	public void toggleStatus(int sid) {
		Survey s = this.getSurver(sid);
		String hql = "update Survey s set s.closed =? where s.id =?";
		surveyDao.batchEntityByHQL(hql, !s.isClosed(), sid);
	}

	/**
	 * 更新数据库中的字段
	 */
	public void updatePhotoPath(int sid, String string) {
		String hql = "update Survey s set s.logoPhotoPath=? where s.id=?";
		System.out.println(hql);
		surveyDao.batchEntityByHQL(hql, string, sid);
	}

	/**
	 * 查询调查集合
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
	 * 移动或复制页
	 */
	public void moveOrCopyPage(Integer srcPid, Integer targPid, Integer pos) {
		Page srcPage = this.getPage(srcPid);
		Survey srcSurvey = srcPage.getSurvey();
		Page targPage = this.getPage(targPid);
		Survey targSurvey = targPage.getSurvey();
		if (srcSurvey.getId() == targSurvey.getId()) {
			setOrderno(srcPage, targPage, pos);
		} else {
			// 强行初始化问题集合,否则深度复制页面对象没有问题集合
			srcPage.getQuestions().size();
			System.out.println(srcPage + "***********************");
			// 深度复制
			Page copyPage = (Page) DataUtil.deeplyCopy(srcPage);
			copyPage.setSurvey(targSurvey);
			// 保存页面
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
		// 之前
		if (pos == 0) {
			// 判断目标页是不是首页
			if (isFirstPage(targPage)) {
				srcPage.setOrderno(targPage.getOrderno() - 0.01f);
			} else {
				Page prePage = getPrepage(targPage);
				srcPage.setOrderno((targPage.getOrderno() + prePage.getOrderno()) / 2);
			}
		} else {
			// 之后
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
	 * 查询所有调查
	 */
	public List<Survey> findAllAvailableSurveys() {
		String hql = "from Survey s where s.closed=?";
		return surveyDao.findEntityByHQL(hql, false);
	}

	/**
	 * 得到第一个页面
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
	 * 得到前一个页面
	 */
	public Page getPrePage(Integer currPid) {
		Page p = this.getPage(currPid);
		p = this.getPrepage(p);
		p.getQuestions().size();
		return p;
	}

	/**
	 * 得到下一个页面
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
