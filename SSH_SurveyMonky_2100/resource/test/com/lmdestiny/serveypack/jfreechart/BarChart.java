package com.lmdestiny.serveypack.jfreechart;

import java.awt.Font;
import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class BarChart {

	public static void main(String[] args) throws Exception {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		ds.setValue(3400, "用友","第一季度");
		ds.setValue(400, "IBM", "第一季度");
		ds.setValue(5000, "Oracle", "第一季度");
		ds.setValue(3400, "用友","第二季度");
		ds.setValue(400, "IBM", "第二季度");
		ds.setValue(5000, "Oracle", "第二季度");
		ds.setValue(3400, "用友","第三季度");
		ds.setValue(400, "IBM", "第三季度");
		ds.setValue(5000, "Oracle", "第三季度");
		JFreeChart chart = ChartFactory.createBarChart("As服务器销量", "季度", "销量(单位:万台)",ds,PlotOrientation.VERTICAL, true, false, false);
		//设置字体
		chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));
		chart.getLegend().setItemFont(new Font("宋体",Font.BOLD,15));
		//得到绘图区
		CategoryPlot plot = chart.getCategoryPlot();
		//设置横轴字体
		plot.getDomainAxis().setLabelFont(new Font("宋体",Font.BOLD,15));
		//设置纵轴字体
		plot.getRangeAxis().setLabelFont(new Font("宋体",Font.BOLD,15));
		//设置小标签字体
		plot.getDomainAxis().setTickLabelFont(new Font("宋体",Font.BOLD,15));
		ChartUtilities.saveChartAsJPEG(new File("d://d.jpeg"), chart, 800, 600);
		
	}

}
