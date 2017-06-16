package com.lmdestiny.surveypark.struts2.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lmdestiny.surveypark.model.Page;
import com.lmdestiny.surveypark.model.Survey;
import com.lmdestiny.surveypark.service.SurveyService;
@Controller("pageAction")
@Scope("prototype")
public class PageAction extends BaseAction<Page> {
	
	private static final long serialVersionUID = 2269867644531674386L;
	private Integer sid;
	private Integer pid;
	private SurveyService surveyService;
	
	public String toAddPage(){
		return "addPagePage";
	}
	
	public String saveOrUpdatePage(){
		Survey s = new Survey();
		s.setId(sid);
		model.setSurvey(s);
		surveyService.saveOrUpdate(model);
		return "designSurveyAction";
	}
	
	public String editPage(){
		this.model = surveyService.getPage(pid);
		return "editPagePage";
	}
	
	
	/**
	 * É¾³ýÒ³Ãæ
	 */
	public String deletePage(){
		surveyService.deletePage(pid);
		return "designSurveyAction";
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public SurveyService getSurveyService() {
		return surveyService;
	}
	@Resource(name="surveyService")
	public void setSurveyService(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

}
