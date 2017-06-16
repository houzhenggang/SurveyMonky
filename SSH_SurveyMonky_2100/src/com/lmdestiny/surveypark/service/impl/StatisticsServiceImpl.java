package com.lmdestiny.surveypark.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lmdestiny.surveypark.dao.BaseDao;
import com.lmdestiny.surveypark.model.Answer;
import com.lmdestiny.surveypark.model.Question;
import com.lmdestiny.surveypark.model.statistics.OptionStatisticsModel;
import com.lmdestiny.surveypark.model.statistics.QuestionStatisticsModel;
import com.lmdestiny.surveypark.service.StatisticsService;

/**
 *ͳ�Ʒ���ʵ�� 
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService{

	private BaseDao<Question> questionDao;
	private BaseDao<Answer> answerDao;
	
	public QuestionStatisticsModel statistics(Integer qid) {
		QuestionStatisticsModel qsm = new QuestionStatisticsModel();
		Question q = questionDao.getEntity(qid);
		qsm.setQuestion(q);
		//ͳ�ƻش������������
		String qhql ="select count(*) from Answer a where a.questionId =?";
		Long count = (Long) answerDao.uniqueResult(qhql, qid);
		qsm.setCount(count.intValue());
		//ͳ�ƻش�ĳ�����������
		String ohql ="select count(*) from Answer a where a.questionId= ? and concat(',',a.answerIds,',') like ? ";
		Long ocount =null;
		//ͳ��ÿ��ѡ������
		int qt =q.getQuestionType();
		switch(qt){
			//�Ǿ�������
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				String[] arr = q.getOptionArr();
				OptionStatisticsModel osm =null;
				for(int i=0;i<arr.length;i++){
					osm = new OptionStatisticsModel();
					ocount = (Long) answerDao.uniqueResult(ohql,qid,","+i+",");
					osm.setCount(ocount.intValue());
					osm.setOptionLabel(arr[i]);
					osm.setOptionIndex(i);
					qsm.getOsms().add(osm);
				}
				//ѡ������������
				if(q.isOther()){
					osm = new OptionStatisticsModel();
					osm.setOptionLabel("����");
					ocount = (Long)answerDao.uniqueResult(ohql, qid,"%other%");
					osm.setCount(ocount.intValue());
					qsm.getOsms().add(osm);
				}
				break;
			//�����ѡ�����ı���,������ͳ��	
			//��������
			case 6:	
			case 7:	
			case 8:	
				String[] rows = q.getMatrixRowTitleArr();
				String[] cols = q.getMatrixColTitleArr();
				String[] opts =q.getMatrixSelectOptionArr();
				for(int i=0;i<rows.length;i++){
					for(int j=0;j<cols.length;j++){
						//��ѡ���ѡ
						if(qt !=8){
							osm =new OptionStatisticsModel();
							osm.setMatrixRowIndex(i);
							osm.setMatrixRowLable(rows[i]);
							osm.setMatrixColIndex(j);
							osm.setMatrixColLabel(cols[j]);
							ocount = (Long) answerDao.uniqueResult(ohql, qid,"%,"+i+"_"+j+",%");
							osm.setCount(ocount.intValue());
							qsm.getOsms().add(osm);
							
						}
						//����ѡ
						else{
							for(int k=0;k<opts.length;k++){
								osm = new OptionStatisticsModel();
								osm.setMatrixRowIndex(i);
								osm.setMatrixRowLable(rows[i]);
								osm.setMatrixColIndex(j);
								osm.setMatrixColLabel(cols[j]);
								ocount = (Long) answerDao.uniqueResult(ohql, qid,"%,"+i+"_"+j+"_"+k+",%");
								osm.setCount(ocount.intValue());
								qsm.getOsms().add(osm);
							}
						}
					}
				}
			
		
		}
		return qsm;
	}
	
	
	
	
	
	
	
	
	
	
	public BaseDao<Question> getQuestionDao() {
		return questionDao;
	}
	@Resource(name="questionDao")
	public void setQuestionDao(BaseDao<Question> questionDao) {
		this.questionDao = questionDao;
	}
	public BaseDao<Answer> getAnswerDao() {
		return answerDao;
	}
	@Resource(name="answerDao")
	public void setAnswerDao(BaseDao<Answer> answerDao) {
		this.answerDao = answerDao;
	}

	
}
