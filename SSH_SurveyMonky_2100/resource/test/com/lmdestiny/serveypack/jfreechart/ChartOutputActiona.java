package com.lmdestiny.serveypack.jfreechart;

import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lmdestiny.surveypark.model.Page;
import com.lmdestiny.surveypark.model.statistics.OptionStatisticsModel;
import com.lmdestiny.surveypark.model.statistics.QuestionStatisticsModel;
import com.lmdestiny.surveypark.service.StatisticsService;
import com.lmdestiny.surveypark.struts2.action.BaseAction;

/**
 *生成图表action 
 */
@Controller("chartOutputAction")
@Scope("prototype")
public class ChartOutputActiona extends BaseAction<Page> {
	
	private static final long serialVersionUID = 9063922991318072178L;
	private StatisticsService ss;
	private Integer qid;
	public String execute(){
		return "success";
	}
	public InputStream getIs(){
		//统计问题
		QuestionStatisticsModel qsm = ss.statistics(qid);
		//构造饼图数据集
		DefaultPieDataset ds = new DefaultPieDataset();
		for(OptionStatisticsModel osm: qsm.getOsms()){
			ds.setValue(osm.getOptionLabel(),osm.getCount());
		}
		JFreeChart chart = ChartFactory.createPieChart(qsm.getQuestion().getTitle(),
				ds, true, false,false);
		//设置编码
		chart.getTitle().setFont(new Font("宋体",Font.BOLD,25));
		chart.getLegend().setItemFont(new Font("宋体",Font.PLAIN,20));
		PiePlot pie = (PiePlot)chart.getPlot();
		pie.setLabelFont(new Font("宋体",Font.PLAIN,15));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ChartUtilities.writeChartAsJPEG(bos, chart, 400, 300);
			return new ByteArrayInputStream(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public StatisticsService getSs() {
		return ss;
	}
	@Resource(name="statisticsService")
	public void setSs(StatisticsService ss) {
		this.ss = ss;
	}
	public Integer getQid() {
		return qid;
	}
	public void setQid(Integer qid) {
		this.qid = qid;
	}
	
	/*
	 		<!--ChartAction -->
		<action name="ChartOutputAction" class="chartOutputAction">
			<result name="success" type="stream">
				<param name="contentType">image/jpeg</param>
				<param name="inputName">is</param>
				<param name="bufferSize">1024</param>
			</result>
		</action>
	 */
}
