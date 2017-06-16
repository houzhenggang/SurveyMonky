package com.lmdestiny.surveypark.struts2.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lmdestiny.surveypark.model.Answer;
import com.lmdestiny.surveypark.model.Page;
import com.lmdestiny.surveypark.model.Survey;
import com.lmdestiny.surveypark.service.SurveyService;
import com.lmdestiny.surveypark.util.StringUtil;

/**
 *参与调查 
 */
@Controller("engageSurveyAction")
@Scope("prototype")
public class EngageSurveyAction extends BaseAction<Survey> implements ServletContextAware,SessionAware,ParameterAware{

	private static final long serialVersionUID = 1605767357059864408L;
	private static final String  CURRENT_SURVEY="current_survey";
	private static final String ALL_PARAMS_MAP ="all_params_map";
	private List<Survey> surveys; 
	private SurveyService surveyService;
	private ServletContext sc;
	private Integer sid;
	private Page currPage;
	private Map<String, Object> sessionMap;
	private Map<String, String[]> paramsMap;
	//当前页面
	
	private Integer currPid ;
	
	/**
	 * 查询所有可用调查
	 */
	public String findAllAvailableSurveys(){
		this.surveys = surveyService.findAllAvailableSurveys();
		return "engageSurveyListPage";
	}
	/**
	 *获得图片的URL地址 
	 */
	public String getImageUrl(String path){
		if(path != null && !"".equals(path)){
			String absPath = sc.getRealPath(path);
			File f = new File(absPath);
			if(f.exists()){
				return sc.getContextPath()+path;
			}
		}
		return sc.getContextPath()+"/question.bmp";
	}
	/**
	 *首次进入参与调查 
	 */
	public String entry(){
		//查询首页
		this.currPage = surveyService.getFirstPage(sid);
		//存放survey-->session
		sessionMap.put(CURRENT_SURVEY, currPage.getSurvey());
		//将答案放到大MAP中,用于回显和提交
		sessionMap.put(ALL_PARAMS_MAP,new HashMap<Integer,Map<String,String[]>>());
		return "engageSurveyPage";
	}
	
	/**
	 * 处理参与调查 
	 */
	public String doEngageSurvey(){
		String submitName = getSubmitName();
		//上一步
		if(submitName.endsWith("pre")){
			mergeParamsIntoSession();
			this.currPage = surveyService.getPrePage(currPid);
			return "engageSurveyPage" ;
		}
		//下一步
		else if(submitName.endsWith("next")){
			mergeParamsIntoSession();
			this.currPage = surveyService.getNextPage(currPid);
			return "engageSurveyPage" ;
		}
		//完成
		else if(submitName.endsWith("done")){
			mergeParamsIntoSession();
			surveyService.saveAnswers(processAnswers());
			clearSessionData();
			return "engageSurveyAction" ;
		}
		//取消
		else if(submitName.endsWith("exit")){
			clearSessionData();
			return "engageSurveyAction" ;
		}
		return null ;
	}


	/**
	 * 处理答案
	 */
	private List<Answer> processAnswers() {
		//矩阵式单选按钮的map
		Map<Integer, String> matrixRadioMap = new HashMap<Integer,String>();
		//所有答案的集合
		List<Answer> answers = new ArrayList<Answer>();
		Answer a = null ;
		String key = null ;
		String[] value = null ;
		Map<Integer, Map<String,String[]>> allMap = getAllParamsMap();
		for(Map<String,String[]> map : allMap.values()){
			for(Entry<String, String[]> entry : map.entrySet()){
				key = entry.getKey();
				value = entry.getValue();
				//处理所有q开头的参数
				if(key.startsWith("q")){
					//常规参数
					if(!key.contains("other") && !key.contains("_")){
						a = new Answer();
						a.setAnswerIds(StringUtil.arr2Str(value));//answerids
						a.setQuestionId(getQid(key));//qid
						a.setSurveyId(getCurrentSurvey().getId());//sid
						a.setOtherAnswer(StringUtil.arr2Str(map.get(key + "other")));//otherAnswer
						answers.add(a);
					}
					//矩阵式单选按钮
					else if(key.contains("_")){
						Integer radioQid = getMatrixRadaioQid(key);
						String oldValue = matrixRadioMap.get(radioQid);
						if(oldValue == null){
							matrixRadioMap.put(radioQid, StringUtil.arr2Str(value));
						}
						else{
							matrixRadioMap.put(radioQid, oldValue + "," + StringUtil.arr2Str(value));
						}
					}
				}
			}
		}
		
		//单独矩阵式单选按钮
		processMatrixRadioMap(matrixRadioMap,answers);
		return answers; 
	}


	/**
	 * 单独矩阵式单选按钮
	 */
	private void processMatrixRadioMap(Map<Integer, String> map,
			List<Answer> answers) {
		Answer a = null ;
		Integer key = null ;
		String value  = null ;
		for(Entry<Integer,String> entry : map.entrySet()){
			key = entry.getKey();
			value = entry.getValue();
			a = new Answer();
			a.setAnswerIds(value);//answerids
			a.setQuestionId(key);//qid
			a.setSurveyId(getCurrentSurvey().getId());
			answers.add(a);
		}
	}


	/**
	 * 获取矩阵式问题id:q12_0 ---> 12
	 */
	private Integer getMatrixRadaioQid(String key) {
		return Integer.parseInt(key.substring(1, key.lastIndexOf("_")));
	}


	/**
	 * 获取当前调查对象
	 */
	private Survey getCurrentSurvey() {
		return (Survey)sessionMap.get(CURRENT_SURVEY);
	}


	/**
	 * 提取问题id--> q12 --> 12
	 */
	private Integer getQid(String key) {
		return Integer.parseInt(key.substring(1));
	}


	/**
	 * 清除session数据
	 */
	private void clearSessionData() {
		sessionMap.remove(CURRENT_SURVEY);
		sessionMap.remove(ALL_PARAMS_MAP);
	}


	/**
	 * 合并参数到session中
	 */
	private void mergeParamsIntoSession() {
		Map<Integer,Map<String,String[]>> allParamsMap = getAllParamsMap();
		allParamsMap.put(currPid, paramsMap);
	}


	/**
	 * 获取session存放所有参数的map
	 */
	@SuppressWarnings("unchecked")
	private Map<Integer, Map<String, String[]>> getAllParamsMap() {
		return (Map<Integer, Map<String, String[]>>)sessionMap.get(ALL_PARAMS_MAP);
	}


	/**
	 * 获得提交按钮的名称
	 */
	private String getSubmitName() {
		for(String key : paramsMap.keySet()){
			if(key.startsWith("submit_")){
				return key ;
			}
		}
		return null;
	}
	
	
	/**
	 * 注入提交的所有参数的map
	 */
	public void setParameters(Map<String, String[]> parameters) {
		this.paramsMap = parameters ;
	}
	
	/**
	 * 设置标记,用于答案回显,主要用于radio|checkbox|select的选中标记
	 */
	public String setTag(String name,String value,String selTag){
		Map<String, String[]> map = getAllParamsMap().get(currPage.getId());
		String[] values = map.get(name);
		if(StringUtil.contains(values,value)){
			return selTag;
		}
		return "" ;
	}
	
	/**
	 * 设置标记,用于答案回显,设置文本框回显
	 */
	public String setText(String name){
		Map<String, String[]> map = getAllParamsMap().get(currPage.getId());
		String[] values = map.get(name);
		return "value='"+values[0]+"'" ;
	}
	
	@Resource(name="surveyService")
	public void setSurveyService(SurveyService surveyService) {
		this.surveyService = surveyService;
	}
	//注入 ServletContext 对象 
	public void setServletContext(ServletContext arg0) {
		this.sc = arg0;
	}	
	
	public List<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	public SurveyService getSurveyService() {
		return surveyService;
	}

	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Page getCurrPage() {
		return currPage;
	}
	public void setCurrPage(Page currPage) {
		this.currPage = currPage;
	}
	//注入session
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}
	public Map<String, String[]> getParamsMap() {
		return paramsMap;
	}
	public void setParamsMap(Map<String, String[]> paramsMap) {
		this.paramsMap = paramsMap;
	}
	public Integer getCurrPid() {
		return currPid;
	}
	public void setCurrPid(Integer currPid) {
		this.currPid = currPid;
	}

}
