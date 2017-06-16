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
		ds.setValue(3400, "����","��һ����");
		ds.setValue(400, "IBM", "��һ����");
		ds.setValue(5000, "Oracle", "��һ����");
		ds.setValue(3400, "����","�ڶ�����");
		ds.setValue(400, "IBM", "�ڶ�����");
		ds.setValue(5000, "Oracle", "�ڶ�����");
		ds.setValue(3400, "����","��������");
		ds.setValue(400, "IBM", "��������");
		ds.setValue(5000, "Oracle", "��������");
		JFreeChart chart = ChartFactory.createBarChart("As����������", "����", "����(��λ:��̨)",ds,PlotOrientation.VERTICAL, true, false, false);
		//��������
		chart.getTitle().setFont(new Font("����",Font.BOLD,20));
		chart.getLegend().setItemFont(new Font("����",Font.BOLD,15));
		//�õ���ͼ��
		CategoryPlot plot = chart.getCategoryPlot();
		//���ú�������
		plot.getDomainAxis().setLabelFont(new Font("����",Font.BOLD,15));
		//������������
		plot.getRangeAxis().setLabelFont(new Font("����",Font.BOLD,15));
		//����С��ǩ����
		plot.getDomainAxis().setTickLabelFont(new Font("����",Font.BOLD,15));
		ChartUtilities.saveChartAsJPEG(new File("d://d.jpeg"), chart, 800, 600);
		
	}

}
