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
 *统计服务实现 
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService{

	private BaseDao<Question> questionDao;
	private BaseDao<Answer> answerDao;
	
	public QuestionStatisticsModel statistics(Integer qid) {
		QuestionStatisticsModel qsm = new QuestionStatisticsModel();
		Question q = questionDao.getEntity(qid);
		qsm.setQuestion(q);
		//统计回答问题的总人数
		String qhql ="select count(*) from Answer a where a.questionId =?";
		Long count = (Long) answerDao.uniqueResult(qhql, qid);
		qsm.setCount(count.intValue());
		//统计回答某个问题的人数
		String ohql ="select count(*) from Answer a where a.questionId= ? and concat(',',a.answerIds,',') like ? ";
		Long ocount =null;
		//统计每个选项的情况
		int qt =q.getQuestionType();
		switch(qt){
			//非矩阵问题
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
				//选项型其它问题
				if(q.isOther()){
					osm = new OptionStatisticsModel();
					osm.setOptionLabel("其他");
					ocount = (Long)answerDao.uniqueResult(ohql, qid,"%other%");
					osm.setCount(ocount.intValue());
					qsm.getOsms().add(osm);
				}
				break;
			//第五个选项是文本框,不参与统计	
			//矩阵问题
			case 6:	
			case 7:	
			case 8:	
				String[] rows = q.getMatrixRowTitleArr();
				String[] cols = q.getMatrixColTitleArr();
				String[] opts =q.getMatrixSelectOptionArr();
				for(int i=0;i<rows.length;i++){
					for(int j=0;j<cols.length;j++){
						//单选或多选
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
						//下拉选
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
