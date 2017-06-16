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
 *��ͼ
 */
public class PieChart {
	public static void main(String[] args) throws Exception {
		//���ݼ�
		DefaultPieDataset dpd = new DefaultPieDataset();
		dpd.setValue("IBM",3000);
		dpd.setValue("Oracle", 5000);
		dpd.setValue("JBoss", 4000);
		dpd.setValue("����", 5500);
		//ͨ����������jfreechart(��ͼ)
		JFreeChart chart = ChartFactory.createPieChart("As����ͼ",dpd,true,false, false);
		//���ñ�������
		chart.getTitle().setFont(new Font("����",Font.BOLD,25));
		//������ʾ������
		chart.getLegend().setItemFont(new Font("����",Font.PLAIN,20));
		//�õ���ͼ��
		PiePlot pie = (PiePlot)chart.getPlot();
		pie.setLabelFont(new Font("����",Font.PLAIN,15));
		//���ñ���
		pie.setBackgroundImage(ImageIO.read(new File("d://b.jpg")));
		chart.setBackgroundImage(ImageIO.read(new File("d://c.jpg")));
		//���÷����
		pie.setExplodePercent("IBM", 0.25);
		//���Ʊ�ǩ
		pie.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1}/{3})"));
		//����ͼƬ
		ChartUtilities.saveChartAsJPEG(new File("d://a.jpeg"), chart,800, 600);
	}
}
