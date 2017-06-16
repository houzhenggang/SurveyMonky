package com.lmdestiny.surveypark.struts2.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lmdestiny.surveypark.model.Page;
import com.lmdestiny.surveypark.model.Survey;
import com.lmdestiny.surveypark.model.User;
import com.lmdestiny.surveypark.service.SurveyService;
import com.lmdestiny.surveypark.struts2.UserAware;

/**
 *移动复制页Action 
 */
@Controller("moveOrCopyPageAction")
@Scope("prototype")
public class MoveOrCopyPageAction extends BaseAction<Page> implements UserAware{
	
	private static final long serialVersionUID = -4381208188671327992L;
	
	private Integer srcPid;
	private Integer targPid;
	private Integer pos;
	private Integer sid;
	private List<Survey> mySurveys;
	private SurveyService surveyService;
	private User user;
	//进入
	public String toSelectTargetPage(){
		mySurveys = surveyService.getSurveyWithPages(user);
		return "moveOrCopyPageListPage";
	}
	
	//移动或复制页
	public String doMoveOrCopyPage(){
		surveyService.moveOrCopyPage(srcPid, targPid, pos);
		return "designSurveyAction"; 
	}
	
	
	
	public Integer getSrcPid() {
		return srcPid;
	}
	public void setSrcPid(Integer srcPid) {
		this.srcPid = srcPid;
	}
	/**
	 * 注入方式拿到user
	 */
	public void setUser(User user) {
		this.user = user;
	}





	public SurveyService getSurveyService() {
		return surveyService;
	}



	@Resource(name="surveyService")
	public void setSurveyService(SurveyService surveyService) {
		this.surveyService = surveyService;
	}




	public List<Survey> getMySurveys() {
		return mySurveys;
	}




	public void setMySurveys(List<Survey> mySurveys) {
		this.mySurveys = mySurveys;
	}

	public Integer getTargPid() {
		return targPid;
	}

	public void setTargPid(Integer targPid) {
		this.targPid = targPid;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}
}
