package com.lmdestiny.serveypack.jfreechart;

import java.awt.Font;
import java.io.File;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *饼图
 */
public class PieChart {
	public static void main(String[] args) throws Exception {
		//数据集
		DefaultPieDataset dpd = new DefaultPieDataset();
		dpd.setValue("IBM",3000);
		dpd.setValue("Oracle", 5000);
		dpd.setValue("JBoss", 4000);
		dpd.setValue("用友", 5500);
		//通过工厂生成jfreechart(饼图)
		JFreeChart chart = ChartFactory.createPieChart("As销量图",dpd,true,false, false);
		//设置标题字体
		chart.getTitle().setFont(new Font("宋体",Font.BOLD,25));
		//设置提示条字体
		chart.getLegend().setItemFont(new Font("宋体",Font.PLAIN,20));
		//得到绘图区
		PiePlot pie = (PiePlot)chart.getPlot();
		pie.setLabelFont(new Font("宋体",Font.PLAIN,15));
		//设置背景
		pie.setBackgroundImage(ImageIO.read(new File("d://b.jpg")));
		chart.setBackgroundImage(ImageIO.read(new File("d://c.jpg")));
		//设置分离度
		pie.setExplodePercent("IBM", 0.25);
		//定制标签
		pie.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1}/{3})"));
		//保存图片
		ChartUtilities.saveChartAsJPEG(new File("d://a.jpeg"), chart,800, 600);
	}
}
