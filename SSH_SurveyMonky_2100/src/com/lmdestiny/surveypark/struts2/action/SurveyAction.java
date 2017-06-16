package com.lmdestiny.surveypark.struts2.action;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lmdestiny.surveypark.model.Survey;
import com.lmdestiny.surveypark.model.User;
import com.lmdestiny.surveypark.service.SurveyService;
import com.lmdestiny.surveypark.struts2.UserAware;

@Controller("surveyAction")
@Scope("prototype")
public class SurveyAction  extends BaseAction<Survey> implements UserAware,ServletContextAware{

	private static final long serialVersionUID = 7978551832759459189L;
	/**
	 * SureyService注入
	 */
	private SurveyService surveyService;
	
	/**
	 * 调查集合
	 */
	private List<Survey> mySurveys;
	//接收用户对象的
	private User user;
	
	private int sid;
	private String inputPage ;
	
	/**
	 * 进入我的调查页面
	 * @return
	 */
	public String mySurveys(){
		mySurveys = surveyService.findMySurveys(user.getId());
		return "mySurveyListPage";
	}
	/**
	 * 新建调查
	 * 注意双向关联
	 */
	public String newSurvey(){
		this.model = surveyService.newSurvey(user);
		return "designSurveyPage";
	}
	/**
	 * 设计调查
	 */
	public String designSurvey(){
		this.model =surveyService.getSurveyWithChildren(sid);
		return "designSurveyPage";
	}
	
	/**
	 *编辑调查页面 
	 */
	public String editSurvey(){
		this.model = surveyService.getSurveyWithChildren(sid);
		return "editSurveyPage"; 
	}
	/**
	 * 编辑页面完成提交  重定向方式
	 */
	public String updateSurvey(){
		this.sid = model.getId();
		model.setUser(user);
		surveyService.updateSurvey(model);
		return "designSurveyAction";
	}
	/**
	 * 删除调查
	 */
	public String deleteSurvey(){
		surveyService.deleteSurvey(sid);
		return "findMySurveysAction"; 
	}
	/**
	 * 清除答案
	 */
	public String clearAnswers(){
		surveyService.clearAnswers(sid);	
		return "findMySurveysAction";
	}
	/**
	 * 修改当前调查的状态
	 */
	public String toggleStatus(){
		surveyService.toggleStatus(sid);
		return "findMySurveysAction" ;
	}
	/**
	 * 到达logo上传页面
	 */
	public String toAddLogoPage(){
		return "addLogoPage";
	}
	//上传文件
	private File logoPhoto;
	//文件名称
	private String logoPhotoFileName;
	//接受servletContext
	private ServletContext sc;
	/**
	 *上传logo 
	 */
	public String doAddLogo(){
		if(logoPhotoFileName != null &&!"".equals(logoPhotoFileName.trim())){
			//校验通过开始上传
			String dir = sc.getRealPath("/upload");
			//扩展名
			String ext =logoPhotoFileName.substring(logoPhotoFileName.lastIndexOf('.'));
			//纳秒时间作为文件名
			long l = System.nanoTime();
			File newFile = new File(dir,l+ext);
			//重命名
			logoPhoto.renameTo(newFile);

			//更新数据库中的路径
			surveyService.updatePhotoPath(sid,"/upload/"+l+ext);
		}
		return "designSurveyAction";
	}
	
	public void prepareDoAddLogo(){
		inputPage = "/addLogo.jsp" ;
	}
	/**
	 *判断图片是否存在 
	 */
	public boolean photoExists(){
		String path = model.getLogoPhotoPath();
		if(path!=null &&!"".equals(path.trim())){
			String absPath = sc.getRealPath(path);
			File f = new File(absPath);
			return f.exists();
		}
		return false;
	}
	//图表形式显示调查
	public String analyzeSurvey(){
		this.model = surveyService.getSurveyWithChildren(sid);
		return "analyzeSurveyListPage";
	}
	
	
	
	
	


	public List<Survey> getMySurveys() {
		return mySurveys;
	}
	public void setMySurveys(List<Survey> mySurveys) {
		this.mySurveys = mySurveys;
	}
	public SurveyService getSurveyService() {
		return surveyService;
	}
	@Resource(name="surveyService")
	public void setSurveyService(SurveyService surveyService) {
		this.surveyService = surveyService;
	}
	/**
	 * 通过注入的方式，降低耦合 在拦截器实现注入
	 */
	@Override
	public void setUser(User user) {
		this.user = user;
		}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public File getLogoPhoto() {
		return logoPhoto;
	}
	public void setLogoPhoto(File logoPhoto) {
		this.logoPhoto = logoPhoto;
	}
	public String getLogoPhotoFileName() {
		return logoPhotoFileName;
	}
	public void setLogoPhotoFileName(String logoPhotoFileName) {
		this.logoPhotoFileName = logoPhotoFileName;
	}
	public String getInputPage() {
		return inputPage;
	}
	public void setInputPage(String inputPage) {
		this.inputPage = inputPage;
	}
	

	//注入ServletContext对象
	public void setServletContext(ServletContext arg0) {
		this.sc = arg0 ;
	}
	
}
