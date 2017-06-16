package com.lmdestiny.surveypark.struts2.action;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lmdestiny.surveypark.model.Page;
import com.lmdestiny.surveypark.model.Question;
import com.lmdestiny.surveypark.service.SurveyService;
@Controller("questionAction")
@Scope("prototype")
public class QuestionAction extends BaseAction<Question> implements Serializable{

	private static final long serialVersionUID = 3146502124970526885L;
	private Integer sid;
	private Integer pid;
	private Integer qid;
	private SurveyService surveyService;
	
	/**
	 *进入到选择类型界面 
	 */
	public String toSelectQuestionType(){
		return "SelectQuestionTypePage";
	}
	
	/**
	 * 根据题型动态选择结果
	 */
	public String toDesignQuestionPage(){
		return model.getQuestionType()+"";
	}
	
	public String saveOrUpdateQuestion(){
		Page p = new Page();
		p.setId(pid);
		this.model.setPage(p);
		surveyService.saveOrUpdate(model);
		return "designSurveyAction";
	}
	/**
	 * 编辑问题
	 */
	public String editQuestion(){
		this.model = surveyService.getQuestion(qid);
		return ""+model.getQuestionType();
	}
	
	/**
	 * 删除问题
	 */
	public String deleteQuestion(){
		surveyService.deleteQuestion(qid);
		return "designSurveyAction";
	}
	

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public SurveyService getSurveyService() {
		return surveyService;
	}
	@Resource(name="surveyService")
	public void setSurveyService(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}
}
