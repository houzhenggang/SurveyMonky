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
	 * SureyServiceע��
	 */
	private SurveyService surveyService;
	
	/**
	 * ���鼯��
	 */
	private List<Survey> mySurveys;
	//�����û������
	private User user;
	
	private int sid;
	private String inputPage ;
	
	/**
	 * �����ҵĵ���ҳ��
	 * @return
	 */
	public String mySurveys(){
		mySurveys = surveyService.findMySurveys(user.getId());
		return "mySurveyListPage";
	}
	/**
	 * �½�����
	 * ע��˫�����
	 */
	public String newSurvey(){
		this.model = surveyService.newSurvey(user);
		return "designSurveyPage";
	}
	/**
	 * ��Ƶ���
	 */
	public String designSurvey(){
		this.model =surveyService.getSurveyWithChildren(sid);
		return "designSurveyPage";
	}
	
	/**
	 *�༭����ҳ�� 
	 */
	public String editSurvey(){
		this.model = surveyService.getSurveyWithChildren(sid);
		return "editSurveyPage"; 
	}
	/**
	 * �༭ҳ������ύ  �ض���ʽ
	 */
	public String updateSurvey(){
		this.sid = model.getId();
		model.setUser(user);
		surveyService.updateSurvey(model);
		return "designSurveyAction";
	}
	/**
	 * ɾ������
	 */
	public String deleteSurvey(){
		surveyService.deleteSurvey(sid);
		return "findMySurveysAction"; 
	}
	/**
	 * �����
	 */
	public String clearAnswers(){
		surveyService.clearAnswers(sid);	
		return "findMySurveysAction";
	}
	/**
	 * �޸ĵ�ǰ�����״̬
	 */
	public String toggleStatus(){
		surveyService.toggleStatus(sid);
		return "findMySurveysAction" ;
	}
	/**
	 * ����logo�ϴ�ҳ��
	 */
	public String toAddLogoPage(){
		return "addLogoPage";
	}
	//�ϴ��ļ�
	private File logoPhoto;
	//�ļ�����
	private String logoPhotoFileName;
	//����servletContext
	private ServletContext sc;
	/**
	 *�ϴ�logo 
	 */
	public String doAddLogo(){
		if(logoPhotoFileName != null &&!"".equals(logoPhotoFileName.trim())){
			//У��ͨ����ʼ�ϴ�
			String dir = sc.getRealPath("/upload");
			//��չ��
			String ext =logoPhotoFileName.substring(logoPhotoFileName.lastIndexOf('.'));
			//����ʱ����Ϊ�ļ���
			long l = System.nanoTime();
			File newFile = new File(dir,l+ext);
			//������
			logoPhoto.renameTo(newFile);

			//�������ݿ��е�·��
			surveyService.updatePhotoPath(sid,"/upload/"+l+ext);
		}
		return "designSurveyAction";
	}
	
	public void prepareDoAddLogo(){
		inputPage = "/addLogo.jsp" ;
	}
	/**
	 *�ж�ͼƬ�Ƿ���� 
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
	//ͼ����ʽ��ʾ����
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
	 * ͨ��ע��ķ�ʽ��������� ��������ʵ��ע��
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
	

	//ע��ServletContext����
	public void setServletContext(ServletContext arg0) {
		this.sc = arg0 ;
	}
	
}
